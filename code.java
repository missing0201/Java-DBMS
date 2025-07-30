import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class code {
    //Alvin Yan's code | ID: 388111103
    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("This is Alvin Yan's (ID:38811103) SCC201 Database Coursework.");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("READING FILES\n");

        //provided code by Professor Uraz on workshop 7

        String Stat = "38811103.csv";

        // JDBC connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/";
        Connection connection = null;

        // Database and table name to be created
        String databaseName = "AlvinSCC201";
        String tableman = "Manufacturer";
        String tablecpu = "CPU";
        String tablephone = "Phone";

        try (BufferedReader br = new BufferedReader(new FileReader(Stat)))
        {
            String line;
            List<String[]> allRows = new ArrayList<>();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line into an array of values using a comma as the delimiter
                String[] row = line.split(",");
                allRows.add(row);
            }

            // Assuming the first row contains column headers
            String[] headers = allRows.get(0);

            // Create arrays for each column dynamically
            int numColumns = headers.length;
            String[][] dataArrays = new String[numColumns][];

            // Initialize arrays
            for (int i = 0; i < numColumns; i++) {
                dataArrays[i] = new String[allRows.size()];
            }

            // Populate arrays with data
            for (int i = 0; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                for (int j = 0; j < numColumns; j++) {
                    dataArrays[j][i] = row[j];
                }
            }

            System.out.println("Reading "+Stat+" is completed... There are "+numColumns+" attributes in the file.");
            
            System.out.println("\nStudent 38811103's File: "+Stat+" Has Been Read And Loaded Into the Array Successfully!");

            System.out.println("--------------------------------------------------------------------------------------");
            try {
                
                //avery small part of the code below is also provided by Professor Uraz during lecture 6
                connection = DriverManager.getConnection(jdbcUrl);
               

                Statement statement = connection.createStatement();
     
                System.out.println("CREATING DATABASE AND TABLES\n");

                String DBCreateQuery = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(DBCreateQuery);
                System.out.println("Database '" + databaseName + "' created successfully.");
    
                String DBUseQuery = "USE " + databaseName;
                statement.executeUpdate(DBUseQuery);
                System.out.println("Now using Data Base :" + databaseName);
    
                String CreateManTableQuery = "CREATE TABLE " + tableman+ "( manufacturer_name varchar(20) PRIMARY KEY, worth int(50), country varchar(20));";
                statement.executeUpdate(CreateManTableQuery);
                System.out.println("Created table :" + tableman);
    
                String CreateCPUTableQuery = "CREATE TABLE " + tablecpu+ "( cpu_name varchar(20) PRIMARY KEY, brand varchar(20), speed double(3,2));";
                statement.executeUpdate(CreateCPUTableQuery);
                System.out.println("Created table :" + tablecpu);
    
                String CreatePhoneTableQuery = "CREATE TABLE "+ tablephone +" ( model_name varchar(20) PRIMARY KEY, OS varchar(20), weight int(50), manufacturer varchar(20), cpu varchar(20), FOREIGN KEY(manufacturer) REFERENCES Manufacturer(manufacturer_name), FOREIGN KEY(cpu) REFERENCES CPU(cpu_name));";
                statement.executeUpdate(CreatePhoneTableQuery);
                System.out.println("Created table :" + tablephone);
    
                System.out.println("\nDATABASE AND ALL TABLES(Phone+CPU+Manufacturer) HAS BEEN CREATED SUCCESSFULLY!");
                System.out.println("--------------------------------------------------------------------------------------");
                
                System.out.println("POPULATING TABLES\n");

                for(int i=1;i<allRows.size();i++){
                    String manFiller = "INSERT IGNORE INTO " + tableman+" VALUES ( '"+ dataArrays[3][i]+"', '"+dataArrays[4][i]+"', '"+ dataArrays[5][i]+"')";
                    statement.executeUpdate(manFiller);
                }

                System.out.println("Manufacturer table has been populated successfully.");   
                
                for(int i=1;i<allRows.size();i++){
                    String cpuFiller = "INSERT IGNORE INTO " + tablecpu+" VALUES ( '"+ dataArrays[6][i]+"', '"+dataArrays[7][i]+"', '"+ dataArrays[8][i]+"')";
                    statement.executeUpdate(cpuFiller);
                }

                System.out.println("CPU table has been populated successfully.");

                for(int i=1;i<allRows.size();i++){
                    String phoneFiller = "INSERT IGNORE INTO " + tablephone+" VALUES ( '"+ dataArrays[0][i]+"', '"+dataArrays[1][i]+"', '"+ dataArrays[2][i]+"', (SELECT manufacturer_name FROM "+tableman+" WHERE manufacturer_name = '"+dataArrays[3][i]+"'), (SELECT cpu_name FROM "+tablecpu+" WHERE cpu_name = '"+dataArrays[6][i]+"')"+")";
                    statement.executeUpdate(phoneFiller);
                }

                System.out.println("Phone table has been populated successfully.");

                System.out.println("\nALL TABLES(Phone+CPU+Manufacturer) HAS BEEN POPULATED SUCCESSFULLY!");
                System.out.println("--------------------------------------------------------------------------------------");
                
                System.out.println("EXECUTING TWO DELETE AND TWO GROUP BY-HAVING QUERIES\n");

                try{
                    String queryA = "DELETE FROM "+tablecpu+" WHERE cpu_name ='A12'";
                    statement.executeUpdate(queryA);
                    System.out.println("hmm,interesting...");
                }catch(SQLException e){
                    System.out.println("Query 1 Failed [Foreign Constraint Breached]");
                }

                try{
                    String queryB = "DELETE FROM "+tableman+" WHERE manufacturer_name ='Apple'";
                    statement.executeUpdate(queryB);
                    System.out.println("hmm,interesting...");
                }catch(SQLException e){
                    System.out.println("Query 2 Failed [Foreign Constraint Breached]");
                }
                
                ResultSet resultset1=statement.executeQuery("SELECT * FROM Phone WHERE OS = 'iOS' AND weight IN (SELECT weight FROM Phone WHERE OS = 'iOS' GROUP BY weight HAVING COUNT(weight) = 2);");
                System.out.println("The pair of iOS phones that has the same weight are: ");
                while(resultset1.next()){
                    System.out.println("Phone: "+resultset1.getString("model_name")+", weight: "+resultset1.getString("weight"));
                }

                ResultSet resultset2=statement.executeQuery("SELECT * FROM Phone WHERE OS = 'Android' AND weight IN ( SELECT weight FROM Phone WHERE OS = 'Android' GROUP BY weight HAVING COUNT(*) = 6);");
                System.out.println("The 6 Android phones that has the same weight are: ");
                while(resultset2.next()){
                    System.out.println("Phone: "+resultset2.getString("model_name")+", weight: "+resultset2.getString("weight"));
                }

                System.out.println("\nALL QUERIES HAS BEEN EXECUTED AND PRINTED OUT ACCORDINGLY!");
                System.out.println("--------------------------------------------------------------------------------------");

                String DBDeleteQuery = "DROP database " + databaseName+ " ;";
                statement.executeUpdate(DBDeleteQuery);
                System.out.println("Deleted Data Base:" + databaseName);
                
                System.out.println("\nALL FUNCTIONS IN ALVIN YAN'S (ID:38811103) PROGRAM HAS BEEN EXECUTED,ABORTING...");

                System.out.println("--------------------------------------------------------------------------------------");
    
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
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    //End of Alvin's code
}
