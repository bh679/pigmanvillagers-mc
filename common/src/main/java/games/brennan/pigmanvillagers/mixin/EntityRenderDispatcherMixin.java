package games.brennan.pigmanvillagers.mixin;

import games.brennan.pigmanvillagers.api.PigmanVillagersApi;
import games.brennan.pigmanvillagers.client.PigmanRenderer;
import games.brennan.pigmanvillagers.client.PigmanRendererHolder;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hands out the pigman renderer for flagged villagers. Only swaps when the resolved
 * renderer is vanilla's {@code VillagerRenderer} (our holder), so a mod that registers its
 * own villager renderer keeps working, just without pigmen.
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "getRenderer", at = @At("RETURN"), cancellable = true)
    @SuppressWarnings("unchecked")
    private <T extends Entity> void pigmanvillagers$pigmanRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
        if (cir.getReturnValue() instanceof PigmanRendererHolder holder && PigmanVillagersApi.isPigman(entity)) {
            PigmanRenderer pigman = holder.pigmanvillagers$pigmanRenderer();
            if (pigman != null) {
                cir.setReturnValue((EntityRenderer<? super T>) (EntityRenderer<?>) pigman);
            }
        }
    }
}
