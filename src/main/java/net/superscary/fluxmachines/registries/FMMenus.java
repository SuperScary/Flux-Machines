package net.superscary.fluxmachines.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;

public class FMMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, FluxMachines.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FluxFurnaceMenu>> FLUX_FURNACE_MENU = register("flux_furnace_menu", FluxFurnaceMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register (String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, () ->IMenuTypeExtension.create(factory));
    }

}
