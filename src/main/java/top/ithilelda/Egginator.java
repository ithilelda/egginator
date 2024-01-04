package top.ithilelda;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.nio.file.Files;
import java.nio.file.Path;

public class Egginator implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("egginator");
    public static final String SPAWNER_TAG_KEY = "SpawnerTag";
    public static Configuration CONFIGURATION = new Configuration();
    ;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Egginator initialized.");

        Yaml yaml = new Yaml();

        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("egginator.yaml");
        try {
            if (Files.exists(configPath)) {
                CONFIGURATION = yaml.loadAs(Files.readString(configPath), Configuration.class);
            } else {
                Files.createFile(configPath);
                Files.writeString(configPath, yaml.dumpAs(CONFIGURATION, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static boolean canCreateSpawnEgg(@NotNull LivingEntity livingEntity) {
        String entityId = Registries.ENTITY_TYPE.getId(livingEntity.getType()).toString();
        return CONFIGURATION.isBlacklist() ^ CONFIGURATION.getEntities().contains(entityId);
    }

    public static NbtCompound updateSpawnerTag(NbtCompound oldSpawnerTag, String id) {
        SpawnerUpgrade upgrade = CONFIGURATION.getUpdates().get(id);
        if (upgrade != null) {
            oldSpawnerTag.putInt("MinSpawnDelay", oldSpawnerTag.getInt("MinSpawnDelay") + upgrade.getMinSpawnDelay());
            oldSpawnerTag.putInt("MaxSpawnDelay", oldSpawnerTag.getInt("MaxSpawnDelay") + upgrade.getMaxSpawnDelay());
            oldSpawnerTag.putInt("MaxNearbyEntities", oldSpawnerTag.getInt("MaxNearbyEntities") + upgrade.getMaxNearbyEntities());
            oldSpawnerTag.putInt("SpawnCount", oldSpawnerTag.getInt("SpawnCount") + upgrade.getSpawnCount());
            oldSpawnerTag.putInt("SpawnRange", oldSpawnerTag.getInt("SpawnRange") + upgrade.getSpawnRange());
            oldSpawnerTag.putInt("RequiredPlayerRange", oldSpawnerTag.getInt("RequiredPlayerRange") + upgrade.getRequiredPlayerRange());
        }
        return oldSpawnerTag;
    }

    public static int getUpgradeExpCost(String id) {
        int result = 0;
        SpawnerUpgrade upgrade = CONFIGURATION.getUpdates().get(id);
        if (upgrade != null) {
            result = upgrade.getExpCost();
        }
        return result;
    }
}