package com.zxf.springboot.tomcat.jndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Enumeration;
import java.util.Hashtable;

public class MyBeanFactory implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        MyBean myBean = new MyBean();

        Enumeration<RefAddr> addrs = ((Reference) obj).getAll();
        while (addrs.hasMoreElements()) {
            RefAddr addr = addrs.nextElement();

            String value = (String) addr.getContent();
            if (addr.getType().equals("foo")) {
                myBean.setFoo(value);
            } else if (addr.getType().equals("bar")) {
                myBean.setBar(value);
            }
        }

        return myBean;
    }
}
