package main;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import commands.PaypalRegisterCommand;
import listeners.PlayerJoinListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.multiverse.MultiverseConstants;
import util.PlayerProfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class TicketEconomy extends JavaPlugin {

    //Connection vars
    static Connection connection; //This is the variable we will use to connect to database
    static MVWorldManager worldManager;

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
        getCommand("register").setExecutor(new PaypalRegisterCommand());

        //register event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        worldManager = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();
        worldManager.cl
                

    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

}
