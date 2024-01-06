package top.ithilelda.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.ithilelda.Egginator;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isPlayerInRange(DDDD)Z"), method = "isPlayerInRange")
    private boolean interceptPlayerCheck(boolean original) {
        if (((MobSpawnerLogicAccessor)this).getRequiredPlayerRange() < 0) return true;
        else return original;
    }
/*
    @WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.spawnNewEntityAndPassengers(Lnet/minecraft/entity/Entity;)Z"), method = "serverTick")
    private boolean debug(ServerWorld instance, Entity entity, Operation<Boolean> original) {
        boolean result = original.call(instance, entity);
        if (result) {
            Egginator.LOGGER.info("successfully spawned mob " + entity.getType());
        }
        return result;
    }
 */
}
