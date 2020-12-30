package games;

public enum MinigameMap {

    HELL_TOWER, WATER_DROP;

    public GameInstance getGameInstance() {
        if (this == HELL_TOWER) return new HelltowerGameInstance();
        if (this == WATER_DROP) return new WaterDropGameInstance();
        return null;
    }

    public String getWorldName() {
        return this.toString().toLowerCase();
    }
}
