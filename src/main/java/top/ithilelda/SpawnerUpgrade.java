package top.ithilelda;

public class SpawnerUpgrade {
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int spawnCount;
    private int maxNearbyEntities;
    private int requiredPlayerRange;
    private int spawnRange;
    private int expCost;

    public SpawnerUpgrade() {
        minSpawnDelay = 0;
        maxSpawnDelay = 0;
        spawnCount = 0;
        maxNearbyEntities = 0;
        requiredPlayerRange = 0;
        spawnRange = 0;
        expCost = 3;
    }

    public SpawnerUpgrade(int minSpawnDelay, int maxSpawnDelay, int spawnCount, int maxNearbyEntities, int requiredPlayerRange, int spawnRange, int expCost) {
        this.minSpawnDelay = minSpawnDelay;
        this.maxSpawnDelay = maxSpawnDelay;
        this.spawnCount = spawnCount;
        this.maxNearbyEntities = maxNearbyEntities;
        this.requiredPlayerRange = requiredPlayerRange;
        this.spawnRange = spawnRange;
        this.expCost = expCost;
    }

    public int getMinSpawnDelay() {
        return minSpawnDelay;
    }

    public void setMinSpawnDelay(int minSpawnDelay) {
        this.minSpawnDelay = minSpawnDelay;
    }

    public int getMaxSpawnDelay() {
        return maxSpawnDelay;
    }

    public void setMaxSpawnDelay(int maxSpawnDelay) {
        this.maxSpawnDelay = maxSpawnDelay;
    }

    public int getSpawnCount() {
        return spawnCount;
    }

    public void setSpawnCount(int spawnCount) {
        this.spawnCount = spawnCount;
    }

    public int getMaxNearbyEntities() {
        return maxNearbyEntities;
    }

    public void setMaxNearbyEntities(int maxNearbyEntities) {
        this.maxNearbyEntities = maxNearbyEntities;
    }

    public int getRequiredPlayerRange() {
        return requiredPlayerRange;
    }

    public void setRequiredPlayerRange(int requiredPlayerRange) {
        this.requiredPlayerRange = requiredPlayerRange;
    }

    public int getSpawnRange() {
        return spawnRange;
    }

    public void setSpawnRange(int spawnRange) {
        this.spawnRange = spawnRange;
    }

    public int getExpCost() {
        return expCost;
    }

    public void setExpCost(int expCost) {
        this.expCost = expCost;
    }
}
