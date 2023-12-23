package top.ithilelda.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ithilelda.Egginator;

import java.util.Optional;

@Mixin(EggEntity.class)
public abstract class EggEntityMixin {

	@Inject(at = @At("HEAD"), method = "onEntityHit")
	private void getHit(EntityHitResult entityHitResult, CallbackInfo ci) {
		EggEntity entity = (EggEntity)(Object)this;
		if (entityHitResult.getEntity() instanceof LivingEntity le) {
			if (!spawnEntityEgg(le)) {
				Egginator.LOGGER.warn("The egg for entity " + le.getName() + " has failed to drop!");
			}
		}
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