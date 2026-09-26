package games.brennan.pigmanvillagers.mixin;

import games.brennan.pigmanvillagers.api.PigmanVillagersApi;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * A villager stamped from a structure template (structure blocks, jigsaw pieces, any mod that
 * places templates with entities) rolls afresh instead of copying the roll the template captured.
 * The tag handed to {@code createEntityIgnoreException} is vanilla's per-entity copy, so clearing
 * it never touches the template itself.
 *
 * <p>The entity pass is {@code placeEntities} in vanilla/Fabric but {@code addEntitiesToWorld} on
 * NeoForge and Forge (patched to run entity processors). Both are targeted; exactly one exists on
 * each loader, so {@code require = 1} makes a silent miss impossible.</p>
 */
@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    @ModifyArg(
            method = {"placeEntities", "addEntitiesToWorld"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;createEntityIgnoreException(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/nbt/CompoundTag;)Ljava/util/Optional;"),
            index = 1,
            require = 1)
    private CompoundTag pigmanvillagers$freshRoll(CompoundTag entityNbt) {
        PigmanVillagersApi.clearRoll(entityNbt);
        return entityNbt;
    }
}
