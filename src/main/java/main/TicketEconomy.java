package main;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TicketEconomy extends JavaPlugin {

    final String database = "mc";
    final String username="admin"; //Enter in your db username
    final String password="mwsBRjS7N66BF4YERLzKuKZG6ObCLVa1BGu4ax"; //Enter your password for the db

    //Connection vars
    static Connection connection; //This is the variable we will use to connect to database

    @Override
    public void onEnable() {
        DatabaseManager.connect("localhost", database, username, password);
        DatabaseManager.executeUpdate("create_table.sql");
        DatabaseManager.executeUpdate("insert_balance.sql", "eergerg", 3.0, 4.0, 5.0);
    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

}
