package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class SoundHelper {

    public static void fire (Level level, Player player, BlockPos pos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch) {
        level.playSound(player, pos, soundEvent, soundSource, volume, pitch);
    }

    public static void fire (Level level, Player player, BlockPos pos, Sounds sounds) {
        fire(level, player, pos, sounds.getSoundEvent(), sounds.getSoundSource(), sounds.getVolume(), sounds.getPitch());
    }

    public static void fire (Level level, Player player, BlockPos pos, Sounds sounds, float volume, float pitch) {
        fire(level, player, pos, sounds.getSoundEvent(), sounds.getSoundSource(), volume, pitch);
    }

    public enum Sounds {

        DECONSTRUCT (SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.7f, 1.0f),
        ROTATE (SoundEvents.PLAYER_SMALL_FALL, SoundSource.BLOCKS, 0.25f, 1.0f),
        BREAK (SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 1f),
        COPY_DATA (SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1f, 1f),
        WRITE_DATA (SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1f, 1f),;

        private final SoundEvent soundEvent;
        private final SoundSource soundSource;
        private final float volume;
        private final float pitch;

        Sounds (SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch) {
            this.soundEvent = soundEvent;
            this.soundSource = soundSource;
            this.volume = volume;
            this.pitch = pitch;
        }

        public SoundEvent getSoundEvent () {
            return soundEvent;
        }

        public SoundSource getSoundSource () {
            return soundSource;
        }

        public float getVolume () {
            return volume;
        }

        public float getPitch () {
            return pitch;
        }
    }

}
