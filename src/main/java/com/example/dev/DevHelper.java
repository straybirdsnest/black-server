package com.example.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class DevHelper {
    public static final String INIT_SCRIPT = "/dev/init.sql";
    public static final String CONFIGURATION_FILE = "/application.properties";
    public static final String JDBC_USERNAME = "spring.datasource.username";
    public static final String JDBC_PASSWORD = "spring.datasource.password";
    public static final String JDBC_URL = "spring.datasource.url";
    public static final String JDBC_DRIVER = "spring.datasource.driverClassName";
    private static final Logger logger = LoggerFactory.getLogger(DevHelper.class);
    private static String username;
    private static String password;
    private static String url;
    private static String driver;

    private static void loadPropertiesFromConfigurationFile() throws IOException {
        InputStream is = DevHelper.class.getResourceAsStream(CONFIGURATION_FILE);
        Properties props = new Properties();
        props.load(is);

        // find from application.properties
        username = props.getProperty(JDBC_USERNAME);
        password = props.getProperty(JDBC_PASSWORD);
        url = props.getProperty(JDBC_URL);
        driver = props.getProperty(JDBC_DRIVER);
    }

    private static void loadPropertiesFromSystemEnvironment() {
        String usernameEnv = System.getenv(JDBC_USERNAME);
        String passwordEnv = System.getenv(JDBC_PASSWORD);
        String urlEnv = System.getenv(JDBC_URL);
        String driverEnv = System.getenv(JDBC_DRIVER);
        if (usernameEnv != null) {
            username = usernameEnv;
        }
        if (passwordEnv != null) {
            password = passwordEnv;
        }
        if (urlEnv != null) {
            url = urlEnv;
        }
        if (driverEnv != null) {
            driver = driverEnv;
        }
    }

    private static void loadPropertiesFromCommandLineArguments(String[] args) {
        if (args == null) return;
        Optional<String> _username = Arrays.stream(args)
                .filter(e -> e.contains(JDBC_USERNAME)).findFirst();
        if (_username.isPresent()) {
            username = _username.get().split("=")[1];
        }
        Optional<String> _password = Arrays.stream(args)
                .filter(e -> e.contains(JDBC_PASSWORD)).findFirst();
        if (_password.isPresent()) {
            password = _password.get().split("=")[1];
        }
        Optional<String> _url = Arrays.stream(args)
                .filter(e -> e.contains(JDBC_URL)).findFirst();
        if (_url.isPresent()) {
            url = _url.get().split("=")[1];
        }
        Optional<String> _driver = Arrays.stream(args)
                .filter(e -> e.contains(JDBC_DRIVER)).findFirst();
        if (_driver.isPresent()) {
            driver = _driver.get().split("=")[1];
        }
    }

    public static void initDb(String[] args) {
        try {
            loadPropertiesFromConfigurationFile();
            loadPropertiesFromSystemEnvironment();
            loadPropertiesFromCommandLineArguments(args);

            // connect to datebase
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);

            ScriptRunner runner = new ScriptRunner(conn, false, true);
            //runner.setLogWriter(new LogPrintWriter(logger));
            runner.setLogWriter(null); // 不显示建表语句
            runner.setErrorLogWriter(new ErrorLogPrintWriter(logger));
            runner.runScript(new BufferedReader(new InputStreamReader(DevHelper.class.getResourceAsStream(INIT_SCRIPT))));

            //检查系统是否windows，若是则去掉盘符前面的反斜杠
            boolean isWindows;
            isWindows = System.getProperty("os.name").startsWith("Windows");

            Statement stat = conn.createStatement();
            String sql = "INSERT INTO T_IMAGE(data) VALUES (LOAD_FILE('%s'))";
            String wnmAvatar = DevHelper.class.getResource("/dev/data/wnm_avatar.png").getPath();
            String wnmBg = DevHelper.class.getResource("/dev/data/wnm_bg.png").getPath();
            if (isWindows) {
                wnmAvatar = wnmAvatar.substring(1);
            }
            stat.execute(String.format(sql, wnmAvatar));

            if (isWindows) {
                wnmBg = wnmBg.substring(1);
            }
            stat.execute(String.format(sql, wnmBg));
            String sql2 = "UPDATE T_USER SET avatar_id=1 WHERE id=1";
            stat.execute(sql2);
            String sql3 = "UPDATE T_USER SET background_image_id=2 WHERE id=1";
            stat.execute(sql3);

            String[] gameImages = new String[]{
                    "/dev/game_cs.png",
                    "/dev/game_dota2.png",
                    "/dev/game_hearthstone.png",
                    "/dev/game_lol.png",
                    "/dev/game_minecraft.png",
                    "/dev/game_starcraft.png",
                    "/dev/game_warcraft.png",
            };
            for (String image : gameImages) {
                String path = DevHelper.class.getResource(image).getPath();
                if (isWindows)
                    path = path.substring(1);
                stat.execute(String.format(sql, path));
            }

            int imageid;
            for (int id = 1; id < 8; id++) {
                imageid = id + 2;
                String sql4 = "UPDATE T_GAME SET logo_id=" + imageid + " WHERE id=" + id;
                stat.execute(sql4);
            }

            for (int id = 1; id < 11; id++) {
                String sql4 = "UPDATE T_ACTIVITY SET cover_image_id=2, game_id=" + (id % 6 + 1) + " WHERE id=" + id;
                stat.execute(sql4);
            }

            stat.close();
            conn.close();

            logger.debug("测试数据库初始化完毕");

        } catch (Exception e) {
            logger.debug("数据库初始化出错，服务器被迫终止", e);
            System.exit(0);
        }
    }

}
