package games.brennan.pigmanvillagers;

/**
 * Pure config parsing and the roll itself (no Minecraft types, unit-tested).
 */
public final class PigmanConfig {

    public static final String CHANCE_KEY = "chance";
    public static final double DEFAULT_CHANCE = 0.01;

    /** Parsed chance plus an optional human-readable warning when the input was not usable as-is. */
    public record Result(double chance, String warning) {}

    private PigmanConfig() {}

    /**
     * Parses the {@code chance} value. Missing or malformed → default; out of range → clamped
     * to 0..1. Either case carries a warning for the log.
     */
    public static Result parseChance(String raw) {
        if (raw == null || raw.isBlank()) {
            return new Result(DEFAULT_CHANCE, "missing '" + CHANCE_KEY + "'");
        }
        double value;
        try {
            value = Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            return new Result(DEFAULT_CHANCE, "'" + CHANCE_KEY + "=" + raw.trim() + "' is not a number");
        }
        if (Double.isNaN(value)) {
            return new Result(DEFAULT_CHANCE, "'" + CHANCE_KEY + "' is NaN");
        }
        if (value < 0.0 || value > 1.0) {
            double clamped = Math.max(0.0, Math.min(1.0, value));
            return new Result(clamped, "'" + CHANCE_KEY + "=" + value + "' is outside 0..1");
        }
        return new Result(value, null);
    }

    /** @param random01 a uniform sample in [0, 1) */
    public static boolean roll(double random01, double chance) {
        return random01 < chance;
    }
}
