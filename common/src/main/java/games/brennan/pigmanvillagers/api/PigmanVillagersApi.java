package games.brennan.pigmanvillagers.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;

/**
 * Public API for other mods. A pigman is an ordinary {@link Villager} carrying a synced
 * flag — same AI, trades and entity type; only the model and voice differ.
 */
public final class PigmanVillagersApi {

    private PigmanVillagersApi() {}

    /** @return true when {@code entity} is a villager flagged as a pigman. */
    public static boolean isPigman(Entity entity) {
        return entity instanceof Villager && entity instanceof PigmanVillagerAccess access
                && access.pigmanvillagers$isPigman();
    }

    /**
     * Forces the pigman flag (server side). Also marks the villager as rolled, so its
     * first-tick roll never overrides the value set here.
     */
    public static void setPigman(Villager villager, boolean pigman) {
        ((PigmanVillagerAccess) villager).pigmanvillagers$setPigman(pigman);
    }
}
