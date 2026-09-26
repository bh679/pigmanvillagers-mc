package games.brennan.pigmanvillagers.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;

/**
 * Public API for other mods. A pigman is an ordinary {@link Villager} carrying a synced
 * flag — same AI, trades and entity type; only the model and voice differ.
 */
public final class PigmanVillagersApi {

    /** Saved NBT key: whether the villager is a pigman. */
    public static final String NBT_PIGMAN = "PigmanVillager";

    /** Saved NBT key: whether the villager has already rolled (so reloads never re-roll). */
    public static final String NBT_ROLLED = "PigmanRolled";

    private PigmanVillagersApi() {}

    /**
     * Makes a villager's saved NBT roll afresh when it is next spawned, by removing both pigman
     * keys. Call on entity NBT stamped from a <em>template</em> (a structure, a carriage, a shared
     * build): the template captured one villager's roll, and without this every copy would repeat
     * it. Do NOT call when restoring the same villager (chunk reload, snapshot restore) — that
     * would re-roll an existing villager. No-op on tags without the keys.
     */
    public static void clearRoll(CompoundTag entityNbt) {
        if (entityNbt == null) {
            return;
        }
        entityNbt.remove(NBT_PIGMAN);
        entityNbt.remove(NBT_ROLLED);
    }

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
