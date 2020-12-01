package main;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private static Connection connection;
    private static Logger logger;

    public static boolean connect(String host, String database, String username, String password) {
        
        logger = JavaPlugin.getPlugin(TicketEconomy.class).getLogger();
        
        logger.info("Connecting to database "+database+" at "+host+" with user "+username+" (port 3306)...");
        String url = "jdbc:mysql://"+host+":3306/"+database;
        try { //We use a try catch to avoid errors, hopefully we don't get any.
            Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return false;
        }
        try { //Another try catch to get any SQL errors (for example connections errors)
            connection = DriverManager.getConnection(url,username,password);
            //with the method getConnection() from DriverManager, we're trying to set
            //the connection's url, username, password to the variables we made earlier and
            //trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { //catching errors)
            e.printStackTrace(); //prints out SQLException errors to the console (if any)
            return false;
        }
        return true;
    }

    public static int executeUpdate(String file_name, Object... orderedParameters) {
        logger.info("Executing update "+file_name+"...");
        try {
            PreparedStatement stmt = prepareStatement(file_name, orderedParameters);
            return stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public static ResultSet executeQuery(String file_name, Object... orderedParameters) {
        logger.info("Executing query "+file_name+"...");
        try {
            PreparedStatement stmt = prepareStatement(file_name, orderedParameters);
            return stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static PreparedStatement prepareStatement(String file_name, Object... orderedParameters) {
        try {
            PreparedStatement stmt = connection.prepareStatement(read("sql/"+file_name, true));
            for (int i = 0; i < orderedParameters.length; i++) {
                System.out.println("[DEBUG] i = "+i +" AND "+orderedParameters[i]);
                int pIndex = i + 1;
                Object p = orderedParameters[i];
                if (p instanceof Integer)
                    stmt.setInt(pIndex, (Integer)p);
                else if (p instanceof String)
                    stmt.setString(pIndex, (String)p);
                else if (p instanceof Double)
                    stmt.setDouble(pIndex, (Double)p);
                else if (p instanceof Boolean)
                    stmt.setBoolean(pIndex, (Boolean)p);
                else
                    throw new IllegalArgumentException("Ordered parameter "+p+" (pIndex = "+pIndex+") is of an unsupported type.");
            }
            return stmt;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean disconnect() {
        logger.info("Disconnecting from database...");
        // invoke on disable.
        try { //using a try catch to catch connection errors (like wrong sql password...)
            if (connection!=null && !connection.isClosed()){ //checking if connection isn't null to
                //avoid receiving a nullpointer
                connection.close(); //closing the connection field variable.
                logger.info("Disconnected!");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String read(String url, boolean internal) {
        BufferedReader bf = null;
        String contents = "";
        try {
            bf = internal
                    ? new BufferedReader(new InputStreamReader(DatabaseManager.class.getResourceAsStream("/"+url)))
                    : new BufferedReader(new InputStreamReader(new FileInputStream(url)));
            while (true) {
                String line = bf.readLine();
                if (line == null) break;
                contents += line.trim()+"\n";
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

}
