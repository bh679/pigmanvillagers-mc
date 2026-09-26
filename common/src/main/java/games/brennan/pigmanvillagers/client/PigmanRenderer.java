package games.brennan.pigmanvillagers.client;

import games.brennan.pigmanvillagers.PigmanVillagers;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

/**
 * Draws a pigman villager as a player model wearing the pigman skin. The skin is a
 * classic (wide-arm) skin, so the model is built with {@code slim = false}. Babies need no
 * special handling: {@code HumanoidModel} already draws a young model with a big head and
 * half-size body, like a baby zombie.
 */
public final class PigmanRenderer extends MobRenderer<Villager, PlayerModel<Villager>> {

    public static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PigmanVillagers.MOD_ID, "textures/entity/pigman.png");

    private static final float SHADOW_RADIUS = 0.5F;

    public PigmanRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), SHADOW_RADIUS);
        addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Villager villager) {
        return TEXTURE;
    }
}
