package com.example.kirill.lab10;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by KIrill on 12.12.2016.
 */
public class DbConnector implements Runnable {
    private String type;
    private String queryBody;
    private static ArrayList<String> result;

    static {
        result = new ArrayList<>();
    }

    public DbConnector(String type, String queryBody) {
        this.type = type;
        this.queryBody = queryBody;
    }

    @Override
    public void run() {
        try {
            Connection connection;
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://195.50.2.82;" +
                            "databasename=JDBCTEST;" +
                            "user=jdbc;" +
                            "password=jdbctest"
            );
            Log.d("Lab10_DBConnector_Class", "Connection OK");
            Statement statement = connection.createStatement();
            switch (type) {
                case "select": select(statement); break;
                default: operation(statement); break;
            }
            connection.close();
            Log.d("Lab10_DBConnector_Class", "Connection Closed");
        } catch (ClassNotFoundException e) {
            Log.d("Lab10_DBConnector_Class", e.getMessage());
        } catch (Exception e) {
            Log.d("Lab10_DBConnector_Class", e.getMessage());
        }
    }

    private void operation(Statement statement) {
        try {
            statement.execute(queryBody);
        } catch (SQLException e) {
            Log.d("Lab10_DBConnector_Class", e.getMessage());
        }
    }

    private void select(Statement statement) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT КодОбласти, Область " +
                            "FROM dbo.Area"
            );
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            resultSet.close();
        } catch (SQLException e) {
            Log.d("Lab10_DBConnector_Class", e.getMessage());
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueryBody() {
        return queryBody;
    }

    public void setQueryBody(String queryBody) {
        this.queryBody = queryBody;
    }

    public static ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }
}
