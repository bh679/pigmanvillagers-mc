package games.brennan.pigmanvillagers.mixin;

import games.brennan.pigmanvillagers.api.PigmanVillagersApi;
import games.brennan.pigmanvillagers.sound.PigmanSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * One seam for the pigman's voice. Every villager voice line — ambient, trade, yes/no,
 * celebrate, hurt, death — funnels through {@code Entity.playSound(SoundEvent, float, float)}
 * on both sides, so swapping the event here covers them all.
 */
@Mixin(Entity.class)
public abstract class EntityPlaySoundMixin {

    @ModifyVariable(method = "playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", at = @At("HEAD"), argsOnly = true)
    private SoundEvent pigmanvillagers$pigVoice(SoundEvent sound) {
        return PigmanVillagersApi.isPigman((Entity) (Object) this) ? PigmanSounds.remap(sound) : sound;
    }
}
