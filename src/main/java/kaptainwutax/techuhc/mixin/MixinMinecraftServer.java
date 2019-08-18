package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.event.ServerStartEvent;
import kaptainwutax.techuhc.event.ServerStopEvent;
import kaptainwutax.techuhc.event.ServerTickEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(at = @At("RETURN"), method = "start")
    private void start(CallbackInfo info) {
        ServerStartEvent.EVENT.invoker().onServerStart((MinecraftServer) (Object) this);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ServerTickEvent.EVENT.invoker().onServerTick((MinecraftServer) (Object) this);
    }

    @Inject(at = @At("HEAD"), method = "shutdown")
    private void stop(CallbackInfo info) {
        ServerStopEvent.EVENT.invoker().onServerStop((MinecraftServer) (Object) this);
    }

}
