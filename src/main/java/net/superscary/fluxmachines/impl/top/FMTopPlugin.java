package net.superscary.fluxmachines.impl.top;

import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModList;

public class FMTopPlugin {

    public static void register () {
        if (!ModList.get().isLoaded("theoneprobe")) {
            return;
        }
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

}
