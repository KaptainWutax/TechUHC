package kaptainwutax.techuhc.mixin;

import com.mojang.datafixers.util.Either;
import kaptainwutax.techuhc.Rules;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mixin(ServerChunkManager.class)
public abstract class MixinServerChunkManager {

    @Shadow protected abstract boolean method_20585(long hash, Function<ChunkHolder, CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>>> futureFunction);

    @Inject(at = @At("HEAD"), method = "shouldTickBlock", cancellable = true)
    public void shouldTickBlock(BlockPos pos, CallbackInfoReturnable cli) {
        if(Rules.BOOLEAN_RULES.get("tick_unloaded_chunks")) {
            cli.setReturnValue(true);
            cli.cancel();
        }
    }

}
