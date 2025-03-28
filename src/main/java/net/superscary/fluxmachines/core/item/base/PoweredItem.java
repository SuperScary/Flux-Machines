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

    private final FMEnergyStorage energyStorage;

    public PoweredItem (Properties properties, int capacity) {
        this(properties, capacity, capacity, capacity, 0);
    }

    public PoweredItem (Properties properties, int capacity, int maxTransfer) {
        this(properties, capacity, maxTransfer, maxTransfer, 0);
    }

    public PoweredItem (Properties properties, int capacity, int maxReceive, int maxExtract) {
        this(properties, capacity, maxReceive, maxExtract, 0);
    }

    public PoweredItem (Properties properties, int capacity, int maxReceive, int maxExtract, int energy) {
        super(properties.stacksTo(1));
        this.energyStorage = new FMEnergyStorage(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int getMaxDamage (ItemStack stack) {
        return getEnergyStorage().getMaxEnergyStored();
    }

    @Override
    public int getDamage (ItemStack stack) {
        return getEnergyStorage().getEnergyStored();
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
        tooltipComponents.add(Component.literal(getEnergyStorage().getEnergyStored() + "/" + getEnergyStorage().getMaxEnergyStored() + " FE"));
    }

    @Override
    public void charge (ItemStack stack, int amount) {
        Preconditions.checkArgument(stack.getItem() instanceof PoweredItem, "Item must be an instance of PoweredItem");
        ((PoweredItem) stack.getItem()).getEnergyStorage().receiveEnergy(amount, false);
    }

}
