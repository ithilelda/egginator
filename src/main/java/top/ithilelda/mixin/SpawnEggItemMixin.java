package top.ithilelda.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.ithilelda.Egginator;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {
    @Inject(at = @At(value = "INVOKE",
            target = "net/minecraft/item/ItemStack.decrement (I)V"),
            method = "useOnBlock")
    private void setSpawnerPropertyWhenUsed(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local BlockEntity blockEntity, @Local ItemStack itemStack) {
        NbtCompound spawnerTags = itemStack.getSubNbt(Egginator.SPAWNER_TAG_KEY);
        if (spawnerTags != null) {
            MobSpawnerLogicAccessor logic = (MobSpawnerLogicAccessor)((MobSpawnerBlockEntity)blockEntity).getLogic();
            logic.setMinSpawnDelay(logic.getMinSpawnDelay() + spawnerTags.getInt("MinSpawnDelay"));
            logic.setMaxSpawnDelay(logic.getMaxSpawnDelay() + spawnerTags.getInt("MaxSpawnDelay"));
            logic.setMaxNearbyEntities(logic.getMaxNearbyEntities() + spawnerTags.getInt("MaxNearbyEntities"));
            logic.setSpawnCount(logic.getSpawnCount() + spawnerTags.getInt("SpawnCount"));
            logic.setSpawnRange(logic.getSpawnRange() + spawnerTags.getInt("SpawnRange"));
            logic.setRequiredPlayerRange(logic.getRequiredPlayerRange() + spawnerTags.getInt("RequiredPlayerRange"));
        }
    }
}
