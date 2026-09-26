package games.brennan.pigmanvillagers.fabric;

import games.brennan.pigmanvillagers.PigmanVillagers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Fabric entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
public final class PigmanVillagersFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PigmanVillagers.init(FabricLoader.getInstance().getConfigDir());
    }
}
