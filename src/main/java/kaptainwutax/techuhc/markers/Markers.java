package kaptainwutax.techuhc.markers;

import kaptainwutax.techuhc.MessageUtils;
import kaptainwutax.techuhc.event.ServerStartEvent;
import kaptainwutax.techuhc.event.ServerStopEvent;
import kaptainwutax.techuhc.event.ServerTickEvent;
import kaptainwutax.techuhc.init.Commands;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.ThreadFactory;

public class Markers implements ModInitializer {
    private static final ThreadFactory TIMESTAMP_RUNNER_FACTORY = runnable -> new Thread(runnable, "Episode-Timestamps");

    private MinecraftServer server;

    private Thread timestampThread;
    private boolean stop;
    private int interval = 20 * 60;
    private boolean running = false;
    private long lastTimestamp = -1;
    private long timestampCount = 0;

    /**
     * Entry point
     */
    @Override
    public void onInitialize() {

        // Grab a MinecraftServer instance
        ServerStartEvent.EVENT.register(server -> this.server = server);

        // Always start the timestamp runner if it doesn't exist or is dead (if the server is ticking)
        ServerTickEvent.EVENT.register(server -> {
            if (this.timestampThread == null || !this.timestampThread.isAlive()) {
                this.timestampThread = TIMESTAMP_RUNNER_FACTORY.newThread(this::runTimestamp);
                this.timestampThread.start();
            }
        });

        // Stop the timestamp runner and dump state restore parameters
        ServerStopEvent.EVENT.register(server -> {
            // Stop the timestamp runner to prevent the server hanging after shutting down
            this.stop = true;
            this.timestampThread.interrupt();

            // Log the parameters that should be used to restore the current state after restart
            if (this.running && this.lastTimestamp >= 0) {
                long timeOffset = System.currentTimeMillis() - this.lastTimestamp;
                this.log("Timestamp parameters to use to restore current state: interval=" + this.interval +
                         ", timestampCount=" + this.timestampCount + ", timeOffset=" + timeOffset);
            }
        });

        // Register the marker command
        Commands.registerCommand(new MarkerCommand(this).getCommand());
    }

    /**
     * Starts the episode timestamps
     *
     * @param timestampCount Timestamp count to restore or default {@code 0}
     * @param timeOffset     Time offset to restore or default {@code -1}
     * @return {@code true} if passed values were valid and thus started successfully, {@code false} otherwise
     */
    public boolean start(int timestampCount, long timeOffset) {
        if (timeOffset >= 0 && timestampCount >= 0) {
            this.timestampCount = timestampCount;
            this.lastTimestamp = System.currentTimeMillis() - timeOffset;
            this.running = true;

            this.broadcast(this.getFormattedTimeSinceStart() + " | Episode " + (this.timestampCount + 1) + " continues.");
            return true;
        } else if (timeOffset == -1 && timestampCount == 0) {
            this.running = true;
            return true;
        }
        return false;
    }

    /**
     * Sets the time interval between two timestamp markers
     *
     * @param interval The interval in seconds
     * @return {@code true} if the passed interval was valid and thus set, {@code false} otherwise
     */
    public boolean setInterval(int interval) {
        if (interval > 0) {
            this.interval = interval;
            return true;
        }
        return false;
    }

    /**
     * Formats a message and logs it to the standard output
     *
     * @param message The message to format and log
     */
    private void log(String message) {
        System.out.println("[Episode Timestamps] " + message);
    }

    /**
     * Formats a message and broadcasts it to every player's chat
     *
     * @param message The message to format and broadcast
     */
    private void broadcast(String message) {
        server.getPlayerManager().broadcastChatMessage(MessageUtils.formatMessage(message), false);
    }

    /**
     * The timestamp runner
     * This is a runnable that should always run in the background
     */
    private void runTimestamp() {
        this.log("Starting episode timestamp runner");
        do {
            if (running) {
                long currentTime = System.currentTimeMillis();

                if (lastTimestamp <= 0) {
                    this.lastTimestamp = currentTime;
                    this.broadcast("Episode 1 starts now.");
                } else if (currentTime > lastTimestamp + interval * 1000) {
                    this.lastTimestamp = currentTime;
                    this.timestampCount++;
                    this.broadcast(this.getFormattedTimeSinceStart() + " | Episode " + this.timestampCount + " ends now. It's time for episode " + (this.timestampCount + 1) + "!");
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                break;
            }
        } while (!stop && !Thread.interrupted());
        this.log("Episode timestamp runner stopped");
    }

    /**
     * @return The time in second that elapsed since the timestamps were started
     */
    private int getTimeSinceStart() {
        return (int) (this.interval * this.timestampCount + (System.currentTimeMillis() - this.lastTimestamp) / 1000);
    }

    /**
     * @return The time in second that elapsed since the timestamps were started formatted in a human readable string
     */
    private String getFormattedTimeSinceStart() {
        int timeSinceStart = this.getTimeSinceStart();
        int seconds = timeSinceStart % 60;
        timeSinceStart = (timeSinceStart - seconds) / 60;

        int minutes = timeSinceStart % 60;
        timeSinceStart = (timeSinceStart - minutes) / 60;

        int hours = timeSinceStart;

        String str = "";
        if (seconds > 0 || minutes == 0 && hours == 0) {
            str = seconds + "s" + str;
        }
        if (minutes > 0) {
            str = minutes + "m" + str;
        }
        if (hours > 0) {
            str = hours + "h" + str;
        }

        return str;
    }
}
