package kaptainwutax.techuhc.init;

import com.mojang.brigadier.CommandDispatcher;
import kaptainwutax.techuhc.command.CommandRule;
import net.minecraft.server.command.ServerCommandSource;

public class Commands {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        CommandRule.register(dispatcher);
    }

}
