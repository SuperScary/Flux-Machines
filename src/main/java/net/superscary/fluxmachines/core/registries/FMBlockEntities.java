package net.superscary.fluxmachines.core.registries;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.blockentity.cable.CableBlockEntity;
import net.superscary.fluxmachines.core.blockentity.machine.CoalGeneratorBlockEntity;
import net.superscary.fluxmachines.core.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.core.blockentity.misc.FluidTankBlockEntity;
import net.superscary.fluxmachines.core.util.DeferredBlockEntityType;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class FMBlockEntities {

    private static final List<DeferredBlockEntityType<?>> BLOCK_ENTITY_TYPES = new ArrayList<>();

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FluxMachines.MODID);

    public static final DeferredBlockEntityType<FluxFurnaceBlockEntity> FLUX_FURNACE = create("flux_furnace", FluxFurnaceBlockEntity.class, FluxFurnaceBlockEntity::new, FMBlocks.FLUX_FURNACE);
    public static final DeferredBlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR = create("coal_generator", CoalGeneratorBlockEntity.class, CoalGeneratorBlockEntity::new, FMBlocks.COAL_GENERATOR);

    public static final DeferredBlockEntityType<FluidTankBlockEntity> FLUID_TANK = create("fluid_tank", FluidTankBlockEntity.class, FluidTankBlockEntity::new, FMBlocks.FLUID_TANK);

    public static final DeferredBlockEntityType<CableBlockEntity> CABLE = create("cable", CableBlockEntity.class, CableBlockEntity::new, FMBlocks.CABLE);

    /**
     * Get all block entity types whose implementations extends the given base class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> List<BlockEntityType<? extends T>> getSubclassesOf (Class<T> baseClass) {
        var result = new ArrayList<BlockEntityType<? extends T>>();
        for (var type : BLOCK_ENTITY_TYPES) {
            if (baseClass.isAssignableFrom(type.getBlockEntityClass())) {
                result.add((BlockEntityType<? extends T>) type.get());
            }
        }
        return result;
    }

    /**
     * Get all block entity types whose implementations implement the given interface.
     */
    public static List<BlockEntityType<?>> getImplementorsOf (Class<?> iface) {
        var result = new ArrayList<BlockEntityType<?>>();
        for (var type : BLOCK_ENTITY_TYPES) {
            if (iface.isAssignableFrom(type.getBlockEntityClass())) {
                result.add(type.get());
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private static <T extends FMBaseBlockEntity> DeferredBlockEntityType<T> create (String id, Class<T> entityClass, BlockEntityFactory<T> factory, BlockDefinition<? extends FMBaseEntityBlock<?>>... blockDefinitions) {
        Preconditions.checkArgument(blockDefinitions.length > 0);
        var deferred = REGISTRY.register(id, () -> {
            AtomicReference<BlockEntityType<T>> typeHolder = new AtomicReference<>();
            BlockEntityType.BlockEntitySupplier<T> supplier = (blockPos, blockState) -> factory.create(typeHolder.get(), blockPos, blockState);

            var blocks = Arrays.stream(blockDefinitions).map(BlockDefinition::block).toArray(FMBaseEntityBlock[]::new);
            var type = BlockEntityType.Builder.of(supplier, blocks).build(null);
            typeHolder.setPlain(type);

            for (var block : blocks) {
                FMBaseEntityBlock<T> baseBlock = (FMBaseEntityBlock<T>) block;
                baseBlock.setBlockEntity(entityClass, type);
            }

            return type;
        });

        var result = new DeferredBlockEntityType<>(entityClass, deferred);
        BLOCK_ENTITY_TYPES.add(result);
        return result;
    }

    @FunctionalInterface
    interface BlockEntityFactory<T extends FMBaseBlockEntity> {
        T create (BlockEntityType<T> type, BlockPos pos, BlockState state);
    }

}
