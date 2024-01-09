package top.ithilelda.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE",
                target = "net/minecraft/world/MobSpawnerLogic.isPlayerInRange (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"),
            method = "serverTick")
    private boolean interceptPlayerCheck(boolean original) {
        if (((MobSpawnerLogicAccessor)this).getRequiredPlayerRange() < 0) return true;
        else return original;
    }

    @Inject(at = @At("HEAD"), method = "setEntityId")
    private void ClearMobSpawnerEntryBeforeSet(EntityType<?> type, @Nullable World world, Random random, BlockPos pos, CallbackInfo ci) {
        ((MobSpawnerLogicAccessor)this).setSpawnEntry(null);
    }
}
