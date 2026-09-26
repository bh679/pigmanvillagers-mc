package games.brennan.pigmanvillagers.sound;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PigmanSoundTableTest {

    @Test
    void voiceLinesBecomePigSounds() {
        assertEquals(PigmanSoundTable.PIG_AMBIENT, PigmanSoundTable.pigSoundFor("entity.villager.ambient"));
        assertEquals(PigmanSoundTable.PIG_AMBIENT, PigmanSoundTable.pigSoundFor("entity.villager.trade"));
        assertEquals(PigmanSoundTable.PIG_AMBIENT, PigmanSoundTable.pigSoundFor("entity.villager.yes"));
        assertEquals(PigmanSoundTable.PIG_AMBIENT, PigmanSoundTable.pigSoundFor("entity.villager.celebrate"));
        assertEquals(PigmanSoundTable.PIG_HURT, PigmanSoundTable.pigSoundFor("entity.villager.no"));
        assertEquals(PigmanSoundTable.PIG_HURT, PigmanSoundTable.pigSoundFor("entity.villager.hurt"));
        assertEquals(PigmanSoundTable.PIG_DEATH, PigmanSoundTable.pigSoundFor("entity.villager.death"));
    }

    @Test
    void workSoundsAndOtherSoundsAreUntouched() {
        assertNull(PigmanSoundTable.pigSoundFor("entity.villager.work_farmer"));
        assertNull(PigmanSoundTable.pigSoundFor("entity.villager.work_librarian"));
        assertNull(PigmanSoundTable.pigSoundFor("entity.generic.eat"));
        assertNull(PigmanSoundTable.pigSoundFor("entity.pig.ambient"));
        assertNull(PigmanSoundTable.pigSoundFor(null));
    }

    @Test
    void exactlySevenVoiceLinesAreRemapped() {
        Set<String> all = new HashSet<>();
        PigmanSoundTable.remappedVillagerSounds().forEach(all::add);
        assertEquals(7, all.size());
    }
}
