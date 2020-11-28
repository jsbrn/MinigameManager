package main;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class TicketEconomy extends JavaPlugin {

    //Connection vars
    static Connection connection; //This is the variable we will use to connect to database

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();

        DatabaseManager.connect(
                "localhost",
                config.getString("mysql.database"),
                config.getString("mysql.username"),
                config.getString("mysql.password"));

        DatabaseManager.executeUpdate("create_table.sql");
        DatabaseManager.executeUpdate("insert_balance.sql", "eergerg", 3.0, 4.0, 5.0);
        DatabaseManager.executeQuery("retrieve_player_balance.sql", "uuidOrName");
    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

}
