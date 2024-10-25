package net.superscary.fluxmachines.core.mixins.chunkloading;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {

    @Shadow
    @Final
    ServerLevel level;

    @Inject(at = @At("RETURN"), method = "anyPlayerCloseEnoughForSpawning", cancellable = true)
    private void forceLoad (ChunkPos pos, CallbackInfoReturnable<Boolean> ci) {
        if (!ci.getReturnValue()) {
            ci.setReturnValue(true);
        }
    }

}
