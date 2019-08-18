package kaptainwutax.techuhc.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import kaptainwutax.techuhc.command.CommandMarkers;
import kaptainwutax.techuhc.command.CommandRule;
import net.minecraft.server.command.ServerCommandSource;

public class Commands {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicatedServer) {
        registerCommand(dispatcher, CommandRule.getCommand());
        Commands.registerCommand(dispatcher, new CommandMarkers().getCommand());
        if(dedicatedServer) {
            //Register dedicated server commands here.
        }
    }

    private static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, LiteralArgumentBuilder<ServerCommandSource> command) {
        dispatcher.register(command);
    }

}
