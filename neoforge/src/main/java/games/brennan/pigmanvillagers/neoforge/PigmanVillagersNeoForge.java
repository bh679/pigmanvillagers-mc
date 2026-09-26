package games.brennan.pigmanvillagers.neoforge;

import games.brennan.pigmanvillagers.PigmanVillagers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

/**
 * NeoForge entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
@Mod(PigmanVillagersNeoForge.MOD_ID)
public final class PigmanVillagersNeoForge {

    public static final String MOD_ID = "pigmanvillagers";

    public PigmanVillagersNeoForge(IEventBus modBus) {
        PigmanVillagers.init(FMLPaths.CONFIGDIR.get());
    }
}
