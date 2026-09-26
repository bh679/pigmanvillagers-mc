package games.brennan.pigmanvillagers.api;

/**
 * Duck interface mixed into {@code Villager} by {@code VillagerMixin}. Prefer
 * {@link PigmanVillagersApi} from outside this mod.
 */
public interface PigmanVillagerAccess {

    boolean pigmanvillagers$isPigman();

    void pigmanvillagers$setPigman(boolean pigman);
}
