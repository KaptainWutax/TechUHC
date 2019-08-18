package kaptainwutax.techuhc.markers;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.techuhc.MessageUtils;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.LongArgumentType.getLong;
import static com.mojang.brigadier.arguments.LongArgumentType.longArg;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MarkerCommand {
    private final Markers markers;

    public MarkerCommand(Markers markers) {
        this.markers = markers;
    }

    public LiteralArgumentBuilder<ServerCommandSource> getCommand() {
        return literal("marker")
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

    private int setInterval(CommandContext<ServerCommandSource> context, int interval) {
        if (this.markers.setInterval(interval)) {
            context.getSource().sendFeedback(MessageUtils.formatMessage("Interval set to " + interval + " second(s)"), true);
            return 1;
        }
        return 0;
    }

    private int start(CommandContext<ServerCommandSource> context, int markerCount, long timeOffset) {
        if (this.markers.start(markerCount, timeOffset)) {
            context.getSource().sendFeedback(MessageUtils.formatMessage("Markers started with count=" + markerCount + " and timeOffset=" + timeOffset), true);
            return 1;
        }
        return 0;
    }
}
