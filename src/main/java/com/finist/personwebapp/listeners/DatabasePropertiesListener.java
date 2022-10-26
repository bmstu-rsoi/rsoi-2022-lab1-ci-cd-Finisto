package com.finist.personwebapp.listeners;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.Console;
import java.util.Properties;

public class DatabasePropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
//    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        System.out.println("test log!!!!");
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties props = new Properties();
        String db_url = environment.getProperty("DATABASE_URL");
        if (db_url == null)
            return;
        String[] dlist = db_url.split(":");
        String db_user = dlist[1].replace("//",""),
                db_pass = dlist[2].split("@")[0],
                db_host = dlist[2].split("@")[1],
                db_name = dlist[3].split("/")[1];
        int db_port = Integer.parseInt(dlist[3].split("/")[0]);
        props.put("spring.datasource.url", "jdbc:postgresql://${DATABASE_HOST:%s}:${DATABASE_PORT:%d}/${DATABASE_NAME:%s}?sslmode=require&amp;sslfactory=org.postgresql.ssl.NonValidatingFactory".formatted(
                db_host, db_port, db_name
        ));
        props.put("spring.datasource.username", "${DATABASE_USER:%s}".formatted(db_user));
        props.put("spring.datasource.password", "${DATABASE_PASSWORD:%s}".formatted(db_pass));
        environment.getPropertySources().addFirst(new PropertiesPropertySource("my-props", props));
    }
}