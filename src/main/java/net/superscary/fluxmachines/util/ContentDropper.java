package net.superscary.fluxmachines.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ContentDropper {

    public static void spawnDrops (Level level, BlockPos pos, List<ItemStack> drops) {
        if (!level.isClientSide()) {
            NonNullList<ItemStack> list = NonNullList.create();
            list.addAll(drops);
            Containers.dropContents(level, pos, list);
        }
    }

    public static void spawnDrops (Level level, BlockPos pos, Container container) {
        if (!level.isClientSide()) {
            List<ItemStack> drops = new ArrayList<>();
            for (int i = 0; i < container.getContainerSize(); i++) {
                drops.add(container.getItem(i));
            }
            spawnDrops(level, pos, drops);
        }
    }

}
