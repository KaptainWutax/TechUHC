package kaptainwutax.techuhc.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private static final List<LiteralArgumentBuilder<ServerCommandSource>> commands = new ArrayList<>();

    public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        commands.add(command);
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        commands.forEach(dispatcher::register);
    }

}
