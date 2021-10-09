package com.zxf.springboot.tomcat.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class JndiConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setAuth("Container");
                resource.setName("/jdbc/myJndiDataSource");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.h2.Driver");
                resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
                resource.setProperty("jdbcUrl", "jdbc:h2:mem:testdb");
                resource.setProperty("username", "sa");
                resource.setProperty("password", "password");
                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Lazy
    @Bean(destroyMethod = "")
    public DataSource myJndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/myJndiDataSource");
        bean.setLookupOnStartup(false);
        bean.setProxyInterface(DataSource.class);
        bean.setResourceRef(true);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}
