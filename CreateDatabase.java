import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

    public static void main(String[] args) {
        // JDBC connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/";
        Connection connection = null;

        // Database name to be created
        String databaseName = "SCC201";

        try {
            // Establishing a connection to the MySQL server
            connection = DriverManager.getConnection(jdbcUrl);
           
            // Creating a Statement object for executing SQL commands
            Statement statement = connection.createStatement();
 
            // Creating the database
            String DBCreateQuery = "CREATE DATABASE " + databaseName;
            statement.executeUpdate(DBCreateQuery);

            System.out.println("Database '" + databaseName + "' created successfully.");

            String DBUseQuery = "USE " + databaseName;
            statement.executeUpdate(DBUseQuery);

            System.out.println("Now using Data Base :" + databaseName);

            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            try{
            if(connection !=null){
                connection.close();
            }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
