package games.brennan.pigmanvillagers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Common init. The feature is mixin-driven (see {@code games.brennan.pigmanvillagers.mixin}):
 * every villager rolls once for being a pigman, which swaps its renderer to a player model
 * wearing the pigman skin and its voice to pig sounds. This class owns the id, the logger
 * and the one config value — the per-villager chance.
 */
public final class PigmanVillagers {

    public static final String MOD_ID = "pigmanvillagers";
    public static final Logger LOGGER = LoggerFactory.getLogger("PigmanVillagers");

    static final String CONFIG_FILE = "pigmanvillagers.properties";

    private static volatile double chance = PigmanConfig.DEFAULT_CHANCE;

    private PigmanVillagers() {}

    /** @param configDir the loader's config directory ({@code config/}). */
    public static void init(Path configDir) {
        chance = loadChance(configDir.resolve(CONFIG_FILE));
        LOGGER.info("[PigmanVillagers] initialised — pigman chance {} per villager", chance);
    }

    /** Probability (0..1) that a villager rolls pigman on its first server tick. */
    public static double chance() {
        return chance;
    }

    private static double loadChance(Path file) {
        if (!Files.exists(file)) {
            writeDefaults(file);
            return PigmanConfig.DEFAULT_CHANCE;
        }
        Properties props = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            LOGGER.warn("[PigmanVillagers] could not read {} — using default chance {}", file, PigmanConfig.DEFAULT_CHANCE, e);
            return PigmanConfig.DEFAULT_CHANCE;
        }
        PigmanConfig.Result result = PigmanConfig.parseChance(props.getProperty(PigmanConfig.CHANCE_KEY));
        if (result.warning() != null) {
            LOGGER.warn("[PigmanVillagers] {} in {} — using {}", result.warning(), file, result.chance());
        }
        return result.chance();
    }

    private static void writeDefaults(Path file) {
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                writer.write("# Pigman Villagers\n");
                writer.write("# Chance (0.0 - 1.0) that a villager is a pigman. Rolled once per villager.\n");
                writer.write(PigmanConfig.CHANCE_KEY + "=" + PigmanConfig.DEFAULT_CHANCE + "\n");
            }
        } catch (IOException e) {
            LOGGER.warn("[PigmanVillagers] could not write default config {}", file, e);
        }
    }
}
