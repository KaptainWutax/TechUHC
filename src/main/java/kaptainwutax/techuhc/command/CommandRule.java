package kaptainwutax.techuhc.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.Rules;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandRule extends Command {
    @Override
    public String getName() {
        return "rule";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        for (Map.Entry<String, Boolean> entry : Rules.BOOLEAN_RULES.entrySet()) {
            builder.then(literal(entry.getKey())
                    .executes(context -> getRule(context, entry.getKey()))
                    .then(literal("false").executes(context -> setRule(context, entry.getKey(), false)))
                    .then(literal("true").executes(context -> setRule(context, entry.getKey(), true)))
            );
        }
    }

    @Override
    public boolean isDedicatedServerOnly() {
        return false;
    }

    private int getRule(CommandContext<ServerCommandSource> context, String key) {
        this.sendFeedback(context, "Rule [" + key + "] has a value of " + Rules.BOOLEAN_RULES.get(key) + ".", false);
        return 0;
    }

    private int setRule(CommandContext<ServerCommandSource> context, String key, boolean value) {
        if (!Rules.BOOLEAN_RULES.containsKey(key)) {
            this.sendFeedback(context, "Unknown rule [" + key + "].", false);
            return 1;
        }

        boolean originalValue = Rules.BOOLEAN_RULES.put(key, value);
        this.sendFeedback(context, "Rule [" + key + "] has been updated from " + originalValue + " to " + value + ".", true);

        return 0;
    }
}
