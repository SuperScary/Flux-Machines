package net.superscary.fluxmachines.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.NotNull;

public record CrucibleBlockHit(BlockPos pos, int area, ItemStack stack) implements CustomPacketPayload {

	public static final CustomPacketPayload.Type<CrucibleBlockHit> TYPE = new CustomPacketPayload.Type<>(FluxMachines.getResource("crucible_block_hit"));

	public static final StreamCodec<RegistryFriendlyByteBuf, CrucibleBlockHit> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, CrucibleBlockHit::pos,
			ByteBufCodecs.VAR_INT, CrucibleBlockHit::area,
			ItemStack.STREAM_CODEC, CrucibleBlockHit::stack,
			CrucibleBlockHit::new);

	@Override
	public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type () {
		return TYPE;
	}

	public static class ClientPayloadHandler {
		public static void handleDataOnMain (final CrucibleBlockHit data, final IPayloadContext context) {
			context.enqueueWork(() -> {
				if (data.stack() == ItemStack.EMPTY) return;
				Containers.dropContents(context.player().level(), data.pos(), NonNullList.of(data.stack()));
			}).exceptionally(e -> {
				context.disconnect(Component.translatable("Block hit failed: %s", e.getMessage()));
				return null;
			});
		}
	}

	public static class ServerPayloadHandler {
		public static void handleDataOnMain (final CrucibleBlockHit data, final IPayloadContext context) {
			context.enqueueWork(() -> {
				if (data.stack() == ItemStack.EMPTY) return;
				Containers.dropContents(context.player().level(), data.pos(), NonNullList.of(data.stack()));
			}).exceptionally(e -> {
				context.disconnect(Component.translatable("Block hit failed: %s", e.getMessage()));
				return null;
			});
		}
	}

}
