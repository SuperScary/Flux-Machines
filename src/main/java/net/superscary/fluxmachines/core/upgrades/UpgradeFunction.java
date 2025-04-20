package net.superscary.fluxmachines.core.upgrades;

import net.superscary.fluxmachines.attributes.Attribute;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;

public interface UpgradeFunction<T extends UpgradeBase, I> {

	void create (T base, I valueType);

	void work (I attribute);

}
