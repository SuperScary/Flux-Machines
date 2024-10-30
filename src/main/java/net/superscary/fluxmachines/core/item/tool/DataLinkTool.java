package net.superscary.fluxmachines.core.item.tool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.api.data.DataComponent;
import net.superscary.fluxmachines.core.item.base.BaseItem;
import net.superscary.fluxmachines.core.item.base.PoweredItem;

import java.util.List;

public class DataLinkTool extends PoweredItem {

    private List<DataComponent> linkedData;

    public DataLinkTool (Properties properties) {
        super(properties, 20_000);
    }

    @Override
    public boolean isEnchantable (ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable (ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public void inventoryTick (ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (hasLinkedData()) {
            // tick power
        }
    }

    @Override
    public void appendHoverText (ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (hasLinkedData()) {
            tooltipComponents.add(Component.translatable("tooltip.fluxmachines.data_link_tool.has_data"));
        }
    }

    public boolean hasLinkedData () {
        return this.linkedData != null && !this.linkedData.isEmpty();
    }

    public void writeLinkedData (List<DataComponent> data) {
        this.linkedData = data;
    }

    public List<DataComponent> getLinkedData () {
        return this.linkedData;
    }

    public void clearLinkedData () {
        this.linkedData = List.of();
    }

}
