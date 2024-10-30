package net.superscary.fluxmachines.core.item.base;

import com.google.common.base.Preconditions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.api.energy.Chargeable;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;

import java.util.List;

public class PoweredItem extends BaseItem implements Chargeable {

    private FMEnergyStorage energyStorage;

    public PoweredItem (Properties properties, int maxEnergy) {
        super(properties.durability(maxEnergy));
        this.energyStorage = new FMEnergyStorage(maxEnergy);
    }

    @Override
    public void setDamage (ItemStack stack, int damage) {
        super.setDamage(stack, getEnergyStorage().getMaxEnergyStored());
    }

    public FMEnergyStorage getEnergyStorage () {
        return energyStorage;
    }

    @Override
    public void inventoryTick (ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText (ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal(getEnergyStorage().getEnergyStored() + "/" + getEnergyStorage().getMaxEnergyStored()));
    }

    @Override
    public void charge (ItemStack stack, int amount) {
        Preconditions.checkArgument(stack.getItem() instanceof PoweredItem, "Item must be an instance of PoweredItem");
        ((PoweredItem) stack.getItem()).getEnergyStorage().receiveEnergy(amount, false);
    }

}
