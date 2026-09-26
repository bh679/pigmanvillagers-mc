package games.brennan.pigmanvillagers.mixin;

import games.brennan.pigmanvillagers.client.PigmanRenderer;
import games.brennan.pigmanvillagers.client.PigmanRendererHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Builds a {@link PigmanRenderer} from the same context every time vanilla builds the
 * villager renderer, so it is rebuilt on every resource reload exactly like vanilla's.
 */
@Mixin(VillagerRenderer.class)
public abstract class VillagerRendererMixin implements PigmanRendererHolder {

    @Unique
    private PigmanRenderer pigmanvillagers$pigman;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void pigmanvillagers$buildPigman(EntityRendererProvider.Context context, CallbackInfo ci) {
        pigmanvillagers$pigman = new PigmanRenderer(context);
    }

    @Override
    public PigmanRenderer pigmanvillagers$pigmanRenderer() {
        return pigmanvillagers$pigman;
    }
}
