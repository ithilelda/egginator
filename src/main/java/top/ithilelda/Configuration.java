package top.ithilelda;


import java.util.Map;
import java.util.List;

public class Configuration {
    private boolean blacklist = true;
    private List<String> entities = List.of("minecraft:wither", "minecraft:ender_dragon");
    private Map<String, SpawnerUpgrade> updateItems = Map.of(
            "minecraft:nether_star", new SpawnerUpgrade(0,0,0,0,-100,0, 30),
            "minecraft:netherite_scrap", new SpawnerUpgrade(0,0,4,4,0,0,20),
            "minecraft:golden_carrot", new SpawnerUpgrade(-20,-80,0,0,0,0,10)
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

    public Map<String, SpawnerUpgrade> getUpdateItems() {
        return updateItems;
    }

    public void setUpdateItems(Map<String, SpawnerUpgrade> updateItems) {
        this.updateItems = updateItems;
    }
}
