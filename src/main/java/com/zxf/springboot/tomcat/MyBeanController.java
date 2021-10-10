package com.zxf.springboot.tomcat;

import com.zxf.springboot.tomcat.jndi.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@RestController
@RequestMapping("/mybean")
public class MyBeanController {
//    Not supported, need the MyBean is an interface.
//    @Autowired
//    private MyBean myJndiBean;
//
//    @GetMapping("/byBean")
//    public MyBean myJndiBeanByBean() throws NamingException {
//        return myJndiBean;
//    }

    @GetMapping("/byTemplate")
    public MyBean myJndiBeanByTemplate() throws NamingException {
        MyBean myBean = new JndiTemplate().lookup("java:comp/env/bean/myJndiBean", MyBean.class);
        return myBean;
    }

    @GetMapping("/byOriginal")
    public MyBean myJndiBeanByOriginal() throws NamingException {
        Context context = new InitialContext();
        MyBean myBean = (MyBean) context.lookup("java:comp/env/bean/myJndiBean");
        return myBean;
    }
}
