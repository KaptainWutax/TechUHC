package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.Rules;
import net.minecraft.entity.EntityCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnHelper.class)
public abstract class MixinSpawnHelper {

    @Inject(at = @At("HEAD"), method = "spawnEntitiesInChunk", cancellable = true)
    private static void spawnEntitiesInChunk(EntityCategory entityCategory, World world, WorldChunk worldChunk, BlockPos blockPos, CallbackInfo ci) {
        if(!Rules.BOOLEAN_RULES.get(entityCategory.getName() + "_spawn"))ci.cancel();
    }

}
