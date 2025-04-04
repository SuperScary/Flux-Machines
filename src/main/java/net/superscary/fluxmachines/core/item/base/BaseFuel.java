package net.superscary.fluxmachines.core.item.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class BaseFuel extends BaseItem {

	private BurnTime burnTime;

	public BaseFuel () {
		this(new Properties());
	}

	public BaseFuel (Item.Properties properties) {
		super(properties);
		this.burnTime = BurnTime.COKE;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return this.burnTime.getBurnTime();
	}

	enum BurnTime {
		COKE(800);

		final int burnTime;
		BurnTime (int burnTime) {
			this.burnTime = burnTime;
		}

		public int getBurnTime () {
			return burnTime;
		}
	}

}
