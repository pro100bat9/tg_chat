package com.example.scrapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class IntegrationEnvironmentTest extends IntegrationEnvironment{

    @Test
    @SneakyThrows
    void checkSchema(){
        Connection connection = DriverManager.getConnection(POSTGRES_CONTAINER.getJdbcUrl(),
                POSTGRES_CONTAINER.getUsername(), POSTGRES_CONTAINER.getPassword());

        List<String> tablesName = getTablesName(connection);
        Set<String> correctNames = new HashSet<>(Arrays.asList("link", "tg_chat", "databasechangeloglock", "databasechangelog", "subscription"));
        for(String name : tablesName){
            assertThat(correctNames).contains(name);
        }
    }


    @SneakyThrows
    private List<String> getTablesName(Connection connection){
        List<String> tablesName = new ArrayList<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try(ResultSet resultSet = databaseMetaData.getTables(null, "public", "%", new String[]{"TABLE"})){
            while (resultSet.next()){
                String tableName = resultSet.getString("TABLE_NAME");
                tablesName.add(tableName);
            }
        }
        return tablesName;

    }
}
