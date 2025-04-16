package net.superscary.fluxmachines.core.item.upgrade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class UpgradeEmpty extends UpgradeBase {

	public UpgradeEmpty (Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText (ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
	}
}
