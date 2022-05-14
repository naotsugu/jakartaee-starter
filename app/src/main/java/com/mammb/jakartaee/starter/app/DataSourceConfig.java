package com.mammb.jakartaee.starter.app;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * To override the settings in the deployment descriptor(web.xml, ejb-jar.xml, application.xml, etc..),
 * define the following :
 * <pre>{@code
 * <data-source>
 *   <name>java:module/MainDs</name>
 *   <class-name>com.example.MyDataSource</class-name>
 *   <server-name>myserver.com</server-name>
 *   <port-number>6689</port-number>
 *   <database-name>myDatabase</database-name>
 *   <user>lance</user>
 *   <password>secret</password>
 * </data-source>
 * }</pre>
 */
@DataSourceDefinition(
    name = DataSourceConfig.DS_NAME,
    className = "org.h2.jdbcx.JdbcDataSource",
    url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
    //url = "jdbc:h2:./data/db",
    //portNumber=6689,
    //serverName="myserver.com",
    //user="lance",
    //password="secret",
    initialPoolSize = 3,
    minPoolSize = 3,
    maxPoolSize = 100,
    properties = {
        "is-connection-validation-required=true",
        "connection-validation-method=custom-validation",
        "validation-classname=org.glassfish.api.jdbc.validation.H2ConnectionValidation",
        "connection-leak-timeout-in-seconds=5",
        "statement-leak-timeout-in-seconds=5"
    })
@jakarta.ejb.Startup
@jakarta.ejb.Singleton
public class DataSourceConfig {

    public static final String DS_NAME = "java:app/App/MainDs";

    private static final Logger log = Logger.getLogger(DataSourceConfig.class.getName());

    @Resource(lookup = DS_NAME)
    private DataSource dataSource;

    /**
     * When using @Startup in an ApplicationScoped CDI bean, define it as follows :
     * <pre>{@code
     * public void onStart(
     *     @Observes @Initialized(ApplicationScoped.class) Object pointless) {
     * }
     * }</pre>
     */
    @PostConstruct
    public void postConstruct() {
        log.info("#### Data source connection check...");
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.setQueryTimeout(10);
            var isValid = stmt.execute("SELECT '1'");
            if (!isValid) {
                throw new RuntimeException("#### Connection validation failed.");
            }
            log.info("#### Connection is valid");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
