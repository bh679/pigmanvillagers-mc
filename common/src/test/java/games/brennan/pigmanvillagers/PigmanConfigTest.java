package games.brennan.pigmanvillagers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PigmanConfigTest {

    @Test
    void defaultChanceIsOnePercent() {
        assertEquals(0.01, PigmanConfig.DEFAULT_CHANCE);
    }

    @Test
    void validValueParsesWithoutWarning() {
        PigmanConfig.Result r = PigmanConfig.parseChance(" 0.25 ");
        assertEquals(0.25, r.chance());
        assertNull(r.warning());
    }

    @Test
    void boundsAreAccepted() {
        assertEquals(0.0, PigmanConfig.parseChance("0").chance());
        assertEquals(1.0, PigmanConfig.parseChance("1").chance());
        assertNull(PigmanConfig.parseChance("1").warning());
    }

    @Test
    void missingValueFallsBackToDefault() {
        for (String raw : new String[] {null, "", "   "}) {
            PigmanConfig.Result r = PigmanConfig.parseChance(raw);
            assertEquals(PigmanConfig.DEFAULT_CHANCE, r.chance());
            assertNotNull(r.warning());
        }
    }

    @Test
    void malformedValueFallsBackToDefault() {
        for (String raw : new String[] {"lots", "1%", "NaN"}) {
            PigmanConfig.Result r = PigmanConfig.parseChance(raw);
            assertEquals(PigmanConfig.DEFAULT_CHANCE, r.chance(), raw);
            assertNotNull(r.warning(), raw);
        }
    }

    @Test
    void outOfRangeValuesAreClamped() {
        assertEquals(1.0, PigmanConfig.parseChance("5").chance());
        assertEquals(0.0, PigmanConfig.parseChance("-0.5").chance());
        assertNotNull(PigmanConfig.parseChance("5").warning());
    }

    @Test
    void rollIsStrictlyBelowChance() {
        assertTrue(PigmanConfig.roll(0.0, 0.01));
        assertTrue(PigmanConfig.roll(0.0099, 0.01));
        assertFalse(PigmanConfig.roll(0.01, 0.01));
        assertFalse(PigmanConfig.roll(0.0, 0.0));
        assertTrue(PigmanConfig.roll(0.9999, 1.0));
    }
}
