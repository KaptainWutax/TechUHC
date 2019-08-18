package kaptainwutax.techuhc.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.TechUHC;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.LongArgumentType.getLong;
import static com.mojang.brigadier.arguments.LongArgumentType.longArg;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandMarkers extends Command {

    @Override
    public String getName() {
        return "markers";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder
                .then(literal("set")
                        .then(literal("interval")
                                .then(argument("interval", integer(1))
                                        .executes(context -> setInterval(context, getInteger(context, "interval")))
                                )
                        )
                )
                .then(literal("start")
                        .executes(context -> start(context, 0, -1))
                        .then(argument("markerCount", integer())
                                .then(argument("timeOffset", longArg(0))
                                        .executes(context -> start(context, getInteger(context, "markerCount"), getLong(context, "timeOffset")))
                                )
                        )
                );
    }

    @Override
    public boolean isDedicatedServerOnly() {
        return false;
    }

    private int setInterval(CommandContext<ServerCommandSource> context, int interval) {
        if (TechUHC.getMarkers().setInterval(interval)) {
            this.sendFeedback(context, "Interval set to " + interval + " second(s)", true);
            return 1;
        }
        return 0;
    }

    private int start(CommandContext<ServerCommandSource> context, int markerCount, long timeOffset) {
        if (TechUHC.getMarkers().start(markerCount, timeOffset)) {
            this.sendFeedback(context, "Markers started with count=" + markerCount + " and timeOffset=" + timeOffset, true);
            return 1;
        }
        return 0;
    }
}
