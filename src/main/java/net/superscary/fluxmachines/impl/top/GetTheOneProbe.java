package net.superscary.fluxmachines.impl.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.superscary.fluxmachines.api.blockentity.Crafter;
import net.superscary.fluxmachines.api.inventory.MachineInventory;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.block.cable.CableBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.blockentity.cable.CableBlockEntity;
import net.superscary.fluxmachines.core.util.helper.MathHelper;

import java.util.function.Function;

public class GetTheOneProbe implements Function<ITheOneProbe, Void> {

    public static ITheOneProbe probe;

    @Override
    public Void apply (ITheOneProbe theOneProbe) {
        probe = theOneProbe;
        probe.registerProvider(new IProbeInfoProvider() {
            @Override
            public ResourceLocation getID () {
                return FluxMachines.getResource("top");
            }

            @Override
            public void addProbeInfo (ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData hitData) {
                if (blockState.getBlock() instanceof FMBaseEntityBlock<?> block && !(blockState.getBlock() instanceof CableBlock)) {
                    var type = block.getBlockEntityType();
                    if (type.getBlockEntity(level, hitData.getPos()) instanceof FMBaseBlockEntity entity) {
                        inventoryType(mode, info, player, level, blockState, hitData, entity);
                    }
                    if (type.getBlockEntity(level, hitData.getPos()) instanceof FMBasePoweredBlockEntity entity) {
                        poweredType(mode, info, player, level, blockState, hitData, entity);
                    }
                }
            }
        });
        return null;
    }

    public void inventoryType (ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData hitData, FMBaseBlockEntity entity) {
        ILayoutStyle defaultStyle = info.defaultLayoutStyle();
    }

    public void poweredType (ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData hitData, FMBasePoweredBlockEntity entity) {
        IProgressStyle style = info.defaultProgressStyle().copy().prefix(Component.translatable("gui.fluxmachines.progress")).suffix("%").filledColor(Color.rgb(0, 162, 0)).alternateFilledColor(Color.rgb(0, 162, 0))
                .alignment(ElementAlignment.ALIGN_CENTER).height(16);

        if (entity instanceof Crafter<?> crafter) {
            var inventory = (MachineInventory) entity;
            if (crafter.isCrafting()) {
                var result = crafter.getCurrentRecipe().get().value().getResultItem(null).getItem();
                info.horizontal().item(getOrEmpty(inventory.getInventory(), 0)).progress(MathHelper.percentage((int) crafter.getProgress(), crafter.getMaxProgress()), 100, style).item(recipeOrStack(inventory.getInventory(), 1, result));
            } else {
                info.text(Component.translatable("gui.fluxmachines.idle"));
            }
        }

    }

    private ItemStack recipeOrStack (ItemStackHandler handler, int slot, Item item) {
        if (handler.getStackInSlot(slot).isEmpty()) {
            return new ItemStack(item);
        } else return handler.getStackInSlot(slot);
    }

    private ItemStack getOrEmpty (ItemStackHandler handler, int slot) {
        return !handler.getStackInSlot(slot).isEmpty() ? handler.getStackInSlot(slot) : ItemStack.EMPTY;
    }

}
