package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.Rules;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class MixinServerWorld extends World {

    protected MixinServerWorld(LevelProperties levelProperties_1, DimensionType dimensionType_1, BiFunction<World, Dimension, ChunkManager> biFunction_1, Profiler profiler_1, boolean boolean_1) {
        super(levelProperties_1, dimensionType_1, biFunction_1, profiler_1, boolean_1);
    }

    @Inject(at = @At("HEAD"), method = "tickChunk", cancellable = true)
    public void tickChunk(WorldChunk worldChunk, int randomTicks, CallbackInfo ci) {
        if(!Rules.BOOLEAN_RULES.get("random_ticks"))ci.cancel();
    }

}
