package com.zxf.springboot.tomcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private DataSource myJndiDataSource;

    @GetMapping("/byBean")
    public List<Map<String, String>> myJndiDataSourceByBean() throws NamingException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(myJndiDataSource);
        return queryData(jdbcTemplate);
    }

    @GetMapping("/byTemplate")
    public List<Map<String, String>> myJndiDataSourceByTemplate() throws NamingException {
        DataSource dataSource = new JndiTemplate().lookup("java:comp/env/jdbc/myJndiDataSource", DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return queryData(jdbcTemplate);
    }

    @GetMapping("/byOriginal")
    public List<Map<String, String>> myJndiDataSourceByOriginal() throws NamingException {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/myJndiDataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return queryData(jdbcTemplate);
    }

    private List<Map<String, String>> queryData(JdbcTemplate jdbcTemplate) {
        List<Map<String, String>> employees = jdbcTemplate.query("select * from employees", new RowMapper<Map<String, String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, String> result = new HashMap<>();
                result.put("id", rs.getString("id"));
                result.put("name", rs.getString("name"));
                result.put("surname", rs.getString("surname"));
                return result;
            }
        });
        return employees;
    }
}
