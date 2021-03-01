package br.com.gabrielle.conection;

import java.sql.*;

public class FactoryConnection {


    private static Connection connection = null;
    private static final String PASSWORD = "";
    private static final String DB_USER = "root";

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/aplicando_teste";

    public Connection createConnectionMySQL() {

        try {
          connection = DriverManager.getConnection(DB_URL, DB_USER, PASSWORD);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return connection;
    }

    public void closeConnection(){
        if ( connection != null){
            try {
                connection.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
   public static void closeStatement(Statement statement){
        if (statement !=null){
            try {
                statement.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
   }
   public static void closeResult(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
   }
   //Services
}


