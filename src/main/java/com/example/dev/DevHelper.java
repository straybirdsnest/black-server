package com.example.dev;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class DevHelper {
    public static void initDb(String[] args) {
        initDbWithScript(args, "/dev/init.sql");
    }

    public static void initDbWithoutTables(String[] args) {
        initDbWithScript(args, "/dev/recreate_database.sql");
    }

    private static void initDbWithScript(String[] args, String script) {
        try {
            String username, password, url, driver;
            InputStream is = DevHelper.class.getResourceAsStream("/application.properties");
            Properties props = new Properties();
            props.load(is);
            // find from application.properties
            username = props.getProperty("spring.datasource.username");
            password = props.getProperty("spring.datasource.password");
            url = props.getProperty("spring.datasource.url");
            driver = props.getProperty("spring.datasource.driverClassName");

            // find from command line arguments
            Optional<String> _username = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.username")).findFirst();
            if (_username.isPresent()) {
                username = _username.get().split("=")[1];
            }
            Optional<String> _password = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.password")).findFirst();
            if (_password.isPresent()) {
                password = _password.get().split("=")[1];
            }
            Optional<String> _url = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.url")).findFirst();
            if (_url.isPresent()) {
                url = _url.get().split("=")[1];
            }
            Optional<String> _driver = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.driverClassName")).findFirst();
            if (_driver.isPresent()) {
                driver = _driver.get().split("=")[1];
            }

            // connect to datebase
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stat = conn.createStatement();

            // run init.sql
            ScriptRunner runner = new ScriptRunner(conn, false, true);
            runner.runScript(new BufferedReader(new InputStreamReader(
                    DevHelper.class.getResourceAsStream(script)
            )));
            System.out.println("=== 测试数据库初始化完毕 ===\n");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
