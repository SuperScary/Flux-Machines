package net.superscary.fluxmachines.core.upgrades;

import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;

public interface UpgradeFunction<T extends UpgradeBase> {

	void work ();

}
