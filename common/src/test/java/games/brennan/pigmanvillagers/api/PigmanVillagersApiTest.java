package games.brennan.pigmanvillagers.api;

import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PigmanVillagersApiTest {

    @Test
    void clearRollRemovesBothKeysAndKeepsTheRest() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", "minecraft:villager");
        nbt.putBoolean(PigmanVillagersApi.NBT_PIGMAN, true);
        nbt.putBoolean(PigmanVillagersApi.NBT_ROLLED, true);

        PigmanVillagersApi.clearRoll(nbt);

        assertFalse(nbt.contains(PigmanVillagersApi.NBT_PIGMAN));
        assertFalse(nbt.contains(PigmanVillagersApi.NBT_ROLLED));
        assertEquals("minecraft:villager", nbt.getString("id"));
    }

    @Test
    void clearRollToleratesMissingKeysAndNull() {
        assertDoesNotThrow(() -> PigmanVillagersApi.clearRoll(new CompoundTag()));
        assertDoesNotThrow(() -> PigmanVillagersApi.clearRoll(null));
    }

    @Test
    void nbtKeysAreStable() {
        // Saved worlds and templates carry these names — renaming them orphans every saved pigman.
        assertEquals("PigmanVillager", PigmanVillagersApi.NBT_PIGMAN);
        assertEquals("PigmanRolled", PigmanVillagersApi.NBT_ROLLED);
    }
}
