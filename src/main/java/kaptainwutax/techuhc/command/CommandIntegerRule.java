package kaptainwutax.techuhc.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.Rules;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandIntegerRule extends Command {
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
        for(Map.Entry<String, Integer> entry : Rules.INTEGER_RULES.entrySet()) {
            builder.then(literal(entry.getKey())
                    .executes(context -> getRule(context, entry.getKey()))
                    .then(CommandManager.argument("count", IntegerArgumentType.integer()).executes(context -> setRule(context, entry.getKey(), IntegerArgumentType.getInteger(context, "count"))))
            );
        }
    }

    @Override
    public boolean isDedicatedServerOnly() {
        return false;
    }

    private int getRule(CommandContext<ServerCommandSource> context, String key) {
        this.sendFeedback(context, "Rule [" + key + "] has a value of " + Rules.INTEGER_RULES.get(key) + ".", false);
        return 1;
    }

    private int setRule(CommandContext<ServerCommandSource> context, String key, int value) {
        if (!Rules.INTEGER_RULES.containsKey(key)) {
            this.sendFeedback(context, "Unknown rule [" + key + "].", false);
            return 0;
        }

        int originalValue = Rules.INTEGER_RULES.put(key, value);
        this.sendFeedback(context, "Rule [" + key + "] has been updated from " + originalValue + " to " + value + ".", true);

        return 1;
    }
}
