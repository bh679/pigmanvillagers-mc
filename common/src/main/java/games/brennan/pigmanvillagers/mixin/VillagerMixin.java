package games.brennan.pigmanvillagers.mixin;

import games.brennan.pigmanvillagers.PigmanConfig;
import games.brennan.pigmanvillagers.PigmanVillagers;
import games.brennan.pigmanvillagers.api.PigmanVillagerAccess;
import games.brennan.pigmanvillagers.api.PigmanVillagersApi;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds the synced pigman flag to every villager and rolls it exactly once.
 *
 * <p>The roll happens on the villager's first server tick rather than in
 * {@code finalizeSpawn}, because many spawn paths skip {@code finalizeSpawn} entirely —
 * structure/NBT-template villagers, breeding, zombie cures, other mods' placers. A saved
 * {@code PigmanRolled} marker makes it sticky across reloads.</p>
 */
@Mixin(Villager.class)
public abstract class VillagerMixin implements PigmanVillagerAccess {

    @Unique
    private static final EntityDataAccessor<Boolean> PIGMANVILLAGERS$DATA_PIGMAN =
            SynchedEntityData.defineId(Villager.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private boolean pigmanvillagers$rolled;

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void pigmanvillagers$defineData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(PIGMANVILLAGERS$DATA_PIGMAN, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void pigmanvillagers$save(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean(PigmanVillagersApi.NBT_PIGMAN, pigmanvillagers$isPigman());
        tag.putBoolean(PigmanVillagersApi.NBT_ROLLED, pigmanvillagers$rolled);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void pigmanvillagers$load(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(PigmanVillagersApi.NBT_PIGMAN)) {
            // An explicit value (e.g. /summon ... {PigmanVillager:1b}) counts as rolled.
            pigmanvillagers$setPigman(tag.getBoolean(PigmanVillagersApi.NBT_PIGMAN));
        }
        if (tag.contains(PigmanVillagersApi.NBT_ROLLED)) {
            pigmanvillagers$rolled = tag.getBoolean(PigmanVillagersApi.NBT_ROLLED);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void pigmanvillagers$rollOnce(CallbackInfo ci) {
        Villager self = (Villager) (Object) this;
        if (pigmanvillagers$rolled || self.level().isClientSide()) {
            return;
        }
        pigmanvillagers$setPigman(PigmanConfig.roll(self.getRandom().nextDouble(), PigmanVillagers.chance()));
    }

    @Override
    public boolean pigmanvillagers$isPigman() {
        return ((Villager) (Object) this).getEntityData().get(PIGMANVILLAGERS$DATA_PIGMAN);
    }

    @Override
    public void pigmanvillagers$setPigman(boolean pigman) {
        pigmanvillagers$rolled = true;
        ((Villager) (Object) this).getEntityData().set(PIGMANVILLAGERS$DATA_PIGMAN, pigman);
    }
}
