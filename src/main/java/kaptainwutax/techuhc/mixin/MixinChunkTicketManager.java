package kaptainwutax.techuhc.mixin;

import net.minecraft.server.world.ChunkTicketManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public abstract class MixinChunkTicketManager {

    private long tick = 0;

    @Inject(at = @At("HEAD"), method = "purge()V", cancellable = true)
    private void purge(CallbackInfo ci) {
        if(this.tick++ % 900 != 0)ci.cancel();
        else System.out.println("Purging!");
    }

}
