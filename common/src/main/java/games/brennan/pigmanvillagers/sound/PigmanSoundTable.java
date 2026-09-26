package games.brennan.pigmanvillagers.sound;

import java.util.Map;

/**
 * Which pig sound replaces which villager sound, keyed by sound-event path (no Minecraft
 * types, unit-tested). Job-site work sounds ({@code entity.villager.work_*}) are block/tool
 * sounds, not the villager's voice, so they are deliberately absent and play unchanged.
 */
public final class PigmanSoundTable {

    public static final String PIG_AMBIENT = "entity.pig.ambient";
    public static final String PIG_HURT = "entity.pig.hurt";
    public static final String PIG_DEATH = "entity.pig.death";

    private static final Map<String, String> VILLAGER_TO_PIG = Map.of(
            "entity.villager.ambient", PIG_AMBIENT,
            "entity.villager.trade", PIG_AMBIENT,
            "entity.villager.yes", PIG_AMBIENT,
            "entity.villager.celebrate", PIG_AMBIENT,
            "entity.villager.no", PIG_HURT,
            "entity.villager.hurt", PIG_HURT,
            "entity.villager.death", PIG_DEATH
    );

    private PigmanSoundTable() {}

    /** @return the pig sound path for a villager voice sound, or {@code null} to leave it unchanged. */
    public static String pigSoundFor(String villagerSoundPath) {
        return villagerSoundPath == null ? null : VILLAGER_TO_PIG.get(villagerSoundPath);
    }

    /** Every villager sound path that is remapped. */
    public static Iterable<String> remappedVillagerSounds() {
        return VILLAGER_TO_PIG.keySet();
    }
}
