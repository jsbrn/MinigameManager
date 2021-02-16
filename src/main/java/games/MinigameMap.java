package games;

import games.challenges.HelltowerGameController;
import games.challenges.WaterDropGameController;
import games.team.CastleHeistGameController;

import java.util.Random;

public enum MinigameMap {

    HELL_TOWER, WATER_DROP, CASTLE_HEIST;

    public GameController createGameController() {
        if (this == HELL_TOWER) return new HelltowerGameController();
        if (this == WATER_DROP) return new WaterDropGameController();
        if (this == CASTLE_HEIST) return new CastleHeistGameController();
        return null;
    }

    public String getWorldName() {
        return "map_"+this.toString().toLowerCase();
    }

    public String getFriendlyWorldName() {
        return this.toString().replace("_", " ");
    }

    public static MinigameMap random() {
        MinigameMap[] values = MinigameMap.values();
        return values[new Random().nextInt(values.length)];
    }
}
