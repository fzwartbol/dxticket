package org.dxticket.configuration;


import org.springframework.beans.factory.annotation.Value;


public class Credentials implements javax.jcr.Credentials {
    @Value("${credentials:username}")
    public static String USERNAME = "admin";
    @Value("${credentials:password}")
    public static String PASSWORD = "admin";
}
