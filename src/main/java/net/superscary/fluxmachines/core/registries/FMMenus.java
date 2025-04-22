package net.superscary.fluxmachines.core.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.menu.FluidTankMenu;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;

public class FMMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, FluxMachines.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FluxFurnaceMenu>> FLUX_FURNACE_MENU = register("flux_furnace_menu", FluxFurnaceMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<FluidTankMenu>> FLUID_TANK_MENU = register("fluid_tank_menu", FluidTankMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ReactorMenu>> REACTOR_MENU = register("reactor_menu", ReactorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register (String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, () ->IMenuTypeExtension.create(factory));
    }

}
