package main;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import commands.PaypalRegisterCommand;
import listeners.PlayerJoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import util.PlayerProfiles;

import java.sql.Connection;

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

    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

}
