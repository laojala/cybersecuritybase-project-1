/*
 * Course project for https://cybersecuritybase.github.io/
 * Implementation contains security flaws
 */

package sec.project.controller;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.h2.tools.RunScript;
import sec.project.domain.Signup;


public class CustomDbHelpers {
    
    Connection connection = null;
    private String databaseAddress = "jdbc:h2:file:./database";
    
    public void addSignupToDb(String attendee, String message) {
        try {
            Connection connection = createConnectionAndDb();
            connection.createStatement().executeUpdate("INSERT INTO Signup (attendee, message) VALUES ('" +attendee+ "', '" +message+ "');");
            connection.close();
        } catch (Throwable t) {
            System.err.println("ERROR1: " + t.getMessage());
        }   
    }
    
    public ArrayList<Signup> readSignupsFromDb() {
        try {
            ArrayList<Signup> fromDB = new ArrayList<>();;
            connection = createConnectionAndDb();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Signup");
            
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("attendee");
                String message = resultSet.getString("message");
                Signup current = new Signup(name, message, id);
                fromDB.add(current); 
            }
            connection.close();
            return fromDB;
        } catch (Throwable t) {
            System.err.println("ERROR2: " + t.getMessage());
            return null;
        }       
    } 
    
    public Connection createConnectionAndDb () {
        Connection connection = null;
        try {
            ResultSet resultSet;
            connection = DriverManager.getConnection(databaseAddress, "sa", "");
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Signup");
            if (!resultSet.first()) {
                RunScript.execute(connection, new FileReader("src/main/resources/import.sql"));
            }
            
        } catch (Throwable t) {
            System.err.println("ERROR3: " + t.getMessage());
        }
        return connection;
    }
}
