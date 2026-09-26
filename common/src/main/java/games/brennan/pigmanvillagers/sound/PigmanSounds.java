package games.brennan.pigmanvillagers.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.Map;

/**
 * Resolves {@link PigmanSoundTable} paths to real {@link SoundEvent}s. Only vanilla
 * ({@code minecraft:}) sounds are remapped.
 */
public final class PigmanSounds {

    private static final Map<String, SoundEvent> PIG_EVENTS = Map.of(
            PigmanSoundTable.PIG_AMBIENT, SoundEvents.PIG_AMBIENT,
            PigmanSoundTable.PIG_HURT, SoundEvents.PIG_HURT,
            PigmanSoundTable.PIG_DEATH, SoundEvents.PIG_DEATH
    );

    private PigmanSounds() {}

    /** @return the pig replacement for a villager voice sound, or {@code sound} unchanged. */
    public static SoundEvent remap(SoundEvent sound) {
        if (sound == null) {
            return null;
        }
        ResourceLocation id = sound.getLocation();
        if (!ResourceLocation.DEFAULT_NAMESPACE.equals(id.getNamespace())) {
            return sound;
        }
        String pig = PigmanSoundTable.pigSoundFor(id.getPath());
        return pig == null ? sound : PIG_EVENTS.getOrDefault(pig, sound);
    }
}
