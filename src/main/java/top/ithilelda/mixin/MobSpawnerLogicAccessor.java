package top.ithilelda.mixin;

import net.minecraft.util.collection.DataPool;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobSpawnerLogic.class)
public interface MobSpawnerLogicAccessor {
    @Accessor
    int getMinSpawnDelay();

    @Accessor
    void setMinSpawnDelay(int minSpawnDelay);

    @Accessor
    int getMaxSpawnDelay();

    @Accessor
    void setMaxSpawnDelay(int maxSpawnDelay);

    @Accessor
    int getSpawnCount();

    @Accessor
    void setSpawnCount(int spawnCount);

    @Accessor
    int getMaxNearbyEntities();

    @Accessor
    void setMaxNearbyEntities(int maxNearbyEntities);

    @Accessor
    int getRequiredPlayerRange();

    @Accessor
    void setRequiredPlayerRange(int requiredPlayerRange);

    @Accessor
    int getSpawnRange();

    @Accessor
    void setSpawnRange(int spawnRange);

    @Accessor
    MobSpawnerEntry getSpawnEntry();

    @Accessor
    void setSpawnEntry(MobSpawnerEntry mobSpawnerEntry);

    @Accessor
    DataPool<MobSpawnerEntry> getSpawnPotentials();

    @Accessor
    void setSpawnPotentials(DataPool<MobSpawnerEntry> spawnPotentials);
}
