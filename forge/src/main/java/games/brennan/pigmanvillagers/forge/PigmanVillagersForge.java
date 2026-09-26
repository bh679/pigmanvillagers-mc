package games.brennan.pigmanvillagers.forge;

import games.brennan.pigmanvillagers.PigmanVillagers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * Forge entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
@Mod("pigmanvillagers")
public final class PigmanVillagersForge {

    public PigmanVillagersForge(IEventBus modBus) {
        PigmanVillagers.init(FMLPaths.CONFIGDIR.get());
    }
}
