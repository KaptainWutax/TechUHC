package kaptainwutax.techuhc.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.Rules;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Map;

public class CommandRule {

    public static LiteralArgumentBuilder<ServerCommandSource> getCommand() {
        //Creates the builder with the main argument literal.
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("rule");

        //Sender needs a permission level of 2.
        builder.requires((sender) -> sender.hasPermissionLevel(2));

        for (Map.Entry<String, Boolean> entry : Rules.BOOLEAN_RULES.entrySet()) {
            builder.then(CommandManager.literal(entry.getKey())
                    .executes(context -> getRule(context, entry.getKey()))
                    .then(CommandManager.literal("false").executes(context -> setRule(context, entry.getKey(), false)))
                    .then(CommandManager.literal("true").executes(context -> setRule(context, entry.getKey(), true)))
            );
        }

        return builder;
    }

    private static int getRule(CommandContext<ServerCommandSource> context, String key) {
        ServerCommandSource source = context.getSource();
        sendMessage(source, "Rule [" + key + "] has a value of " + Rules.BOOLEAN_RULES.get(key) + ".", false);
        return 0;
    }

    private static int setRule(CommandContext<ServerCommandSource> context, String key, boolean value) {
        ServerCommandSource source = context.getSource();

        if (!Rules.BOOLEAN_RULES.containsKey(key)) {
            sendMessage(source, "Unknown rule [" + key + "].", false);
            return 1;
        }

        boolean originalValue = Rules.BOOLEAN_RULES.put(key, value);
        sendMessage(source, "Rule [" + key + "] has been updated from " + originalValue + " to " + value + ".", true);

        return 0;
    }

    private static void sendMessage(ServerCommandSource source, String message, boolean showOps) {
        source.sendFeedback(new LiteralText(message), showOps);
    }

}
