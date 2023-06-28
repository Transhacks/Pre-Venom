package me.stevemmmmm.thepitremake.game.killstreaks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Killstreak {
    private static Killstreak instance;
    private final Map<UUID, Integer> killStreakMap = new HashMap<>();

    // static initialization block
    static {
        instance = new Killstreak();
    }

    // private constructor to prevent external instantiation
    private Killstreak() {}

    /**
     * Returns the instance of this class
     * @return the singleton instance of Killstreak
     */
    public static Killstreak getInstance() {
        return instance;
    }
    /**
     * Get kills from hashmap with key 'UUID'
     * @param uuid
     * @return
     */
 
    public int getKills(final UUID uuid){
        if(!killStreakMap.containsKey(uuid)) return 0;
        return killStreakMap.get(uuid);
    }
 
    /**
     * Add kill into hashmap with key 'uuid'
     * @param uuid
     */
 
    public void addKill(final UUID uuid) {
        if (killStreakMap.containsKey(uuid)) {
            killStreakMap.put(uuid, getKills(uuid) + 1);
        } else {
            killStreakMap.put(uuid, 1);
        }
    }
 
    /**
     * Reset kills under key 'UUID'
     * @param uuid
     */
 
    public void resetKills(final UUID uuid){
        if(!killStreakMap.containsKey(uuid)) return;
        killStreakMap.remove(uuid);
    }
 
 
}
