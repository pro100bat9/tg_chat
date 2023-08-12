package com.example.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {
    static final String IMAGE_VERSION = "postgres:14";
    static final PostgreSQLContainer<?> POSTGRES_CONTAINER;
    static final String MASTER_PATH = "master.xml";

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION)
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("postgres");

        POSTGRES_CONTAINER.start();
        executeMigrations();
    }

    private static void executeMigrations(){
        try(Connection connection = DriverManager.getConnection(POSTGRES_CONTAINER.getJdbcUrl(),
                POSTGRES_CONTAINER.getUsername(),
                POSTGRES_CONTAINER.getPassword())){
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Path path = new File("").toPath().toAbsolutePath().getParent().resolve("migrations");
            Liquibase liquibase = new Liquibase(MASTER_PATH, new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());

        }
        catch (LiquibaseException | SQLException e) {
            throw new RuntimeException("Failed to execute migrations", e);
        }  catch (FileNotFoundException e) {
            throw new RuntimeException("Changelog file not found", e);
        }
    }
    public static void stopContainer(){
        POSTGRES_CONTAINER.stop();
    }



}
