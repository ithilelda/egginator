package top.ithilelda.api;

import net.minecraft.nbt.NbtCompound;

/**
 * embeds an arbitrary nbt compound for use at runtime.
 */
public interface INbtEmbedded {
    NbtCompound getEmbeddedNbt();
    void setEmbeddedNbt(NbtCompound nbtCompound);
}
