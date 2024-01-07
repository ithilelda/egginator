package top.ithilelda.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE",
                target = "net/minecraft/world/MobSpawnerLogic.isPlayerInRange (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"),
            method = "serverTick")
    private boolean interceptPlayerCheck(boolean original) {
        if (((MobSpawnerLogicAccessor)this).getRequiredPlayerRange() < 0) return true;
        else return original;
    }
}
