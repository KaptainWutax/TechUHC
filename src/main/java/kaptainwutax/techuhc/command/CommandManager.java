package kaptainwutax.techuhc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public static final CommandManager INSTANCE = new CommandManager();

    private final List<Command> commands = new ArrayList<>();

    /**
     * Registers a command
     *
     * @param command The command to be registered
     */
    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    /**
     * Must be called on server setup to register the commands to the game
     */
    public void registerBrigadierCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicatedServer) {
        for (Command command : this.commands) {
            if (!command.isDedicatedServerOnly() || dedicatedServer) {
                LiteralArgumentBuilder<ServerCommandSource> builder = LiteralArgumentBuilder.literal(command.getName());

                builder.requires((sender) -> sender.hasPermissionLevel(command.getRequiredPermissionLevel()));

                command.build(builder);
                dispatcher.register(builder);
            }
        }
    }
}
