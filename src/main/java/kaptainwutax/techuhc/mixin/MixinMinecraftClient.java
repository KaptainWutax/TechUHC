package kaptainwutax.techuhc.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow protected int attackCooldown;

    @Shadow public Screen currentScreen;

    @Shadow public GameOptions options;

    @Shadow public Mouse mouse;

    @Inject(at = @At("TAIL"), method = "tick()V")
    public void tick(CallbackInfo ci) {
        /*
        StringBuilder sb = new StringBuilder();

        sb.append("Screen:" + this.currentScreen + ", ");
        sb.append("Cooldown:" + this.attackCooldown + ", ");
        sb.append("Attack:" + this.options.keyAttack.isPressed() + ", ");
        sb.append("Locked:" + this.mouse.isCursorLocked());


        System.out.println(sb.toString());
        */

        KeyBinding.setKeyPressed(this.options.keyAttack.getDefaultKeyCode(), false);
        this.attackCooldown = 0;
    }

}
