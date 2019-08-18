package kaptainwutax.techuhc.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.MessageUtils;
import net.minecraft.server.command.ServerCommandSource;

public abstract class Command {
    /**
     * @return The name of the root argument
     */
    public abstract String getName();

    /**
     * @return The required permission level for the whole command to be accessible and visible to a command sender
     */
    public abstract int getRequiredPermissionLevel();

    /**
     * Builds the command
     */
    public abstract void build(LiteralArgumentBuilder<ServerCommandSource> builder);

    /**
     * @return {@code true} if it must only be available on a dedicated server, {@code false} otherwise
     */
    public abstract boolean isDedicatedServerOnly();

    /**
     * Sends a message in a standardized way
     */
    protected final void sendFeedback(CommandContext<ServerCommandSource> context, String message, boolean showOps) {
        context.getSource().sendFeedback(MessageUtils.formatMessage(message), showOps);
    }
}
