package util;

import main.PlayerProfile;
import main.TicketEconomy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerProfiles {

    private static HashMap<String, PlayerProfile> PLAYER_PROFILES = new HashMap<String, PlayerProfile>();

    public static PlayerProfile getPlayerProfile(UUID uuid) {
        return PLAYER_PROFILES.get(uuid.toString());
    }

    public static boolean profileExists(Player p) {
        return PLAYER_PROFILES.containsKey(p.getUniqueId().toString());
    }

    public static void create(Player p) {
        PlayerProfile newProfile = new PlayerProfile(p.getUniqueId(), false, 0, 0, 0, "");
        PLAYER_PROFILES.put(p.getUniqueId().toString(), newProfile);
        DatabaseManager.executeUpdate("insert_profile.sql", p.getUniqueId().toString());
        newProfile.save();
    }

    public static boolean loadPlayerProfiles() {
        ResultSet results = DatabaseManager.executeQuery("select_all_profiles.sql");
        try {
            while (results.next()) {
                UUID playerUUID = UUID.fromString(results.getString("player_uuid"));
                PlayerProfile profile = new PlayerProfile(
                        playerUUID,
                        results.getBoolean("premium"),
                        results.getInt("tickets"),
                        results.getInt("credits"),
                        results.getInt("coins"),
                        results.getString("paypal_email")
                );
                PLAYER_PROFILES.put(playerUUID.toString(), profile);
            }
            JavaPlugin.getPlugin(TicketEconomy.class).getLogger().info("Successfully loaded "+results.getFetchSize()+" profiles.");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
