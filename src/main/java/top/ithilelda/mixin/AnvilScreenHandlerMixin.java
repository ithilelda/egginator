package top.ithilelda.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ithilelda.Egginator;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Inject(at = @At(value = "INVOKE",
            target = "net/minecraft/inventory/CraftingResultInventory.setStack(ILnet/minecraft/item/ItemStack;)V",
            ordinal = 2),
            method = "updateResult",
            cancellable = true)
    private void UpdateSpawnEgg(CallbackInfo ci) {
        ForgingScreenHandlerAccessor fsh = (ForgingScreenHandlerAccessor) this;
        AnvilScreenHandlerAccessor ash = (AnvilScreenHandlerAccessor) this;
        ItemStack input1 = fsh.getInput().getStack(0);
        ItemStack input2 = fsh.getInput().getStack(1);
        String id = Registries.ITEM.getId(input2.getItem()).toString();
        if (input1.getItem() instanceof SpawnEggItem && Egginator.CONFIGURATION.getUpdates().containsKey(id)) {
            ItemStack output = input1.copy();
            NbtCompound oldSpawnerTag = output.getOrCreateSubNbt(Egginator.SPAWNER_TAG_KEY);
            output.setSubNbt(Egginator.SPAWNER_TAG_KEY, Egginator.updateSpawnerTag(oldSpawnerTag, id));
            fsh.getOutput().setStack(0, output);
            ash.getLevelCost().set(Egginator.getUpgradeExpCost(id));
            ci.cancel();
        }
    }
}