package net.superscary.fluxmachines.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.superscary.fluxmachines.core.util.item.MimickingRecord;

public class Codecs {

    public static final StreamCodec<RegistryFriendlyByteBuf, Integer> INTEGER_STREAM_CODEC = new StreamCodec<>() {
        @Override
        public Integer decode (RegistryFriendlyByteBuf pBuffer) {
            return pBuffer.readVarInt();
        }

        @Override
        public void encode (RegistryFriendlyByteBuf pBuffer, Integer pValue) {
            pBuffer.writeVarInt(pValue);
        }
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, Float> FLOAT_STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, Float>() {
        @Override
        public Float decode (RegistryFriendlyByteBuf pBuffer) {
            return pBuffer.readFloat();
        }

        @Override
        public void encode (RegistryFriendlyByteBuf pBuffer, Float pValue) {
            pBuffer.writeFloat(pValue);
        }
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, String> STRING_STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, String>() {
        @Override
        public String decode (RegistryFriendlyByteBuf pBuffer) {
            return pBuffer.readUtf();
        }

        @Override
        public void encode (RegistryFriendlyByteBuf pBuffer, String pValue) {
            pBuffer.writeUtf(pValue);
        }
    };

    public static final Codec<MimickingRecord> MIMICKING_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.BOOL.fieldOf("isMimicking").forGetter(MimickingRecord::isMimicking),
                Block.CODEC.forGetter(MimickingRecord::block)
        ).apply(instance, MimickingRecord::new)
    );

}
