package main;

import commands.TestCommand;
import listeners.PlayerJoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import util.DatabaseManager;
import util.PlayerProfiles;

import java.sql.Connection;

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

        DatabaseManager.executeUpdate("create_profiles_table.sql");

        PlayerProfiles.loadPlayerProfiles();

        //register commands
        getCommand("test").setExecutor(new TestCommand());

        //register event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

}
