package games;

import java.util.Arrays;

public enum MinigameMode {

    PARKOUR_RACE, WATER_DROP, SURVIVAL_GAMES, CROSS_THE_GAP, STEAL_THE_GOLD;

    public String getName() {
        String[] words = this.toString().split("_");
        String name = "";
        for (String word: words)
            name += (word.charAt(0)+"").toUpperCase() + word.substring(1) + " ";
        return name.trim();
    }

    public String getAcronym() {
        String[] words = this.toString().split("_");
        String acronym = "";
        for (String word: words)
            acronym += (word.charAt(0)+"").toUpperCase();
        return acronym.trim();
    }

}
