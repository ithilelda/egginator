package top.ithilelda;


import java.util.List;
import java.util.Map;

public class Configuration {
    private boolean blacklist = true;
    private List<String> entities = List.of("minecraft:wither", "minecraft:ender_dragon");
    private Map<String, SpawnerUpgrade> updates = Map.of(
            "minecraft:nether_star", new SpawnerUpgrade(0, 0, 0, 0, -100, 0, 30),
            "minecraft:netherite_scrap", new SpawnerUpgrade(0, 0, 4, 4, 0, 0, 20),
            "minecraft:golden_carrot", new SpawnerUpgrade(-20, -80, 0, 0, 0, 0, 10)
    );

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    public List<String> getEntities() {
        return entities;
    }

    public void setEntities(List<String> entities) {
        this.entities = entities;
    }

    public Map<String, SpawnerUpgrade> getUpdates() {
        return updates;
    }

    public void setUpdates(Map<String, SpawnerUpgrade> updates) {
        this.updates = updates;
    }
}
