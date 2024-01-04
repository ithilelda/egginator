package top.ithilelda.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ithilelda.Egginator;

@Mixin(EggEntity.class)
public abstract class EggEntityMixin {

    @Inject(at = @At("HEAD"), method = "onEntityHit")
    private void getHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (entityHitResult.getEntity() instanceof LivingEntity le && Egginator.canCreateSpawnEgg(le)) {
            if (!spawnEntityEgg(le)) {
                Egginator.LOGGER.warn("The egg for entity " + le.getName() + " has failed to drop!");
            }
        }
    }

    @ModifyExpressionValue(at = @At(value = "FIELD", target = "net/minecraft/world/World.isClient:Z"), method = "onCollision")
    private boolean cancelChickenSpawnOnLivingEntityHit(boolean original, HitResult hitResult) {
        return original || (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() instanceof LivingEntity);
    }

    @Unique
    private boolean spawnEntityEgg(LivingEntity entity) {
        SpawnEggItem spawnEgg = SpawnEggItem.forEntity(entity.getType());
        if (spawnEgg != null) {
            Egginator.LOGGER.debug("Spawn Egg of Entity " + EntityType.getId(entity.getType()) + " found!");
            NbtCompound entityNbt = new NbtCompound();
            entity.writeNbt(entityNbt);
            Egginator.LOGGER.debug(entityNbt.toString());
            entityNbt.remove("Pos");
            entityNbt.remove("UUID");
            entityNbt.remove("Motion");
            entityNbt.remove("Rotation");
            NbtCompound itemNbt = new NbtCompound();
            itemNbt.put(EntityType.ENTITY_TAG_KEY, entityNbt);
            ItemStack spawnEggStack = new ItemStack(spawnEgg);
            spawnEggStack.setNbt(itemNbt);
            ItemEntity ie = entity.dropStack(spawnEggStack);
            if (ie != null) {
                entity.discard();
                return true;
            }
        }
        return false;
    }
}