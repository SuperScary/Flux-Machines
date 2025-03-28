package net.superscary.fluxmachines.core.item.tool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.api.data.DataLinkInteract;
import net.superscary.fluxmachines.api.data.PropertyComponent;
import net.superscary.fluxmachines.core.item.base.PoweredItem;
import net.superscary.fluxmachines.core.util.helper.PlayerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataLinkTool extends PoweredItem {

    private ArrayList<PropertyComponent<?>> linkedData;

    public DataLinkTool (Properties properties) {
        super(properties, 200_000);
    }

    @Override
    public boolean isEnchantable (@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable (@NotNull ItemStack stack, @NotNull ItemStack book) {
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use (@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        if (player.isShiftKeyDown() && !(level.getBlockEntity(PlayerHelper.clip(player, level)) instanceof DataLinkInteract)) {
            clearLinkedData();
            if (!level.isClientSide())
                player.sendSystemMessage(Component.translatable("message.fluxmachines.data_link_tool.cleared_data"));
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }
    }

    @Override
    public void inventoryTick (ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!hasLinkedData()) {
            getEnergyStorage().receiveEnergy(1, false);
        } else {
            getEnergyStorage().extractEnergy(1, false);
        }
    }

    @Override
    public void appendHoverText (ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (hasLinkedData()) {
            tooltipComponents.add(Component.translatable("tooltip.fluxmachines.data_link_tool.has_data"));
        } else {
            tooltipComponents.add(Component.translatable("message.fluxmachines.data_link_tool.empty_data"));
        }
    }

    public boolean hasLinkedData () {
        return this.linkedData != null && !this.linkedData.isEmpty();
    }

    public void writeLinkedData (ArrayList<PropertyComponent<?>> data) {
        this.linkedData = data;
    }

    public ArrayList<PropertyComponent<?>> getLinkedData () {
        return this.linkedData;
    }

    public void clearLinkedData () {
        this.linkedData = new ArrayList<>();
    }

}
