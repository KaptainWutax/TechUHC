package kaptainwutax.techuhc.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public abstract class MixinCommandManager {

    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;

    /**
     * Enables registering command on the integrated server
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onRegister(boolean dedicatedServer, CallbackInfo ci) {
        if (!dedicatedServer) {
            kaptainwutax.techuhc.command.CommandManager.INSTANCE.registerBrigadierCommands(this.dispatcher, dedicatedServer);
        }
    }

}
