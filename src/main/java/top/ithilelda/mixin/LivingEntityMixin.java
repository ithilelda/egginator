package top.ithilelda.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ithilelda.Egginator;
import top.ithilelda.api.INbtEmbedded;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements INbtEmbedded {
    private NbtCompound nbtCompound;
    @Override
    public NbtCompound getEmbeddedNbt() {
        return nbtCompound;
    }

    @Override
    public void setEmbeddedNbt(NbtCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeEmbeddedNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbtCompound != null) {
            nbt.put(Egginator.EMBEDDED_KEY, nbtCompound);
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readEmbeddedNbt(@NotNull NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(Egginator.EMBEDDED_KEY, NbtElement.COMPOUND_TYPE)) {
            nbtCompound = nbt.getCompound(Egginator.EMBEDDED_KEY);
        }
    }

    @Inject(at = @At("TAIL"), method = "dropLoot")
    private void dropCustomLootFromNbt(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (nbtCompound != null) {
            // debug. will remove.
            if (damageSource != null && damageSource.getSource() instanceof PlayerEntity pe) {
                pe.sendMessage(Text.of(nbtCompound.toString()));
            }
            LivingEntity self = (LivingEntity)(Object)this;
            NbtList customLoots = nbtCompound.getList("CustomLoots", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < customLoots.size(); i++) {
                NbtCompound loot = customLoots.getCompound(i);
                if (self.getRandom().nextDouble() < loot.getDouble("DropChance")) {
                    String dropName = loot.getString("DropItem");
                    ItemStack stack = new ItemStack(Registries.ITEM.get(new Identifier(dropName)), loot.getInt("DropCount"));
                    self.dropStack(stack);
                }
            }
        }
    }
}
