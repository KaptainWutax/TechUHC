package kaptainwutax.techuhc.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import kaptainwutax.techuhc.event.ServerStartEvent;
import kaptainwutax.techuhc.event.ServerStopEvent;
import kaptainwutax.techuhc.event.ServerTickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer extends MinecraftServer {
    public MixinMinecraftServer(File file_1, Proxy proxy_1, DataFixer dataFixer_1, CommandManager commandManager_1, YggdrasilAuthenticationService yggdrasilAuthenticationService_1, MinecraftSessionService minecraftSessionService_1, GameProfileRepository gameProfileRepository_1, UserCache userCache_1, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory_1, String string_1) {
        super(file_1, proxy_1, dataFixer_1, commandManager_1, yggdrasilAuthenticationService_1, minecraftSessionService_1, gameProfileRepository_1, userCache_1, worldGenerationProgressListenerFactory_1, string_1);
    }

    @Inject(at = @At("INVOKE"), method = "start")
    private void start(CallbackInfo info) {
        ServerStartEvent.EVENT.invoker().onServerStart(this);
    }

    @Inject(at = @At("INVOKE"), method = "tick")
    private void tick(CallbackInfo info) {
        ServerTickEvent.EVENT.invoker().onServerTick(this);
    }

    @Inject(at = @At("INVOKE"), method = "shutdown")
    private void stop(CallbackInfo info) {
        ServerStopEvent.EVENT.invoker().onServerStop(this);
    }

}
