package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.Rules;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class MixinServerWorld {

    @Inject(at = @At("HEAD"), method = "tickChunk", cancellable = true)
    public void tickChunk(WorldChunk worldChunk, int randomTicks, CallbackInfo ci) {
        if(!Rules.BOOLEAN_RULES.get("random_ticks"))ci.cancel();
    }

}
