package kaptainwutax.techuhc.markers;

import kaptainwutax.techuhc.MessageUtils;
import kaptainwutax.techuhc.event.ServerStartEvent;
import kaptainwutax.techuhc.event.ServerStopEvent;
import kaptainwutax.techuhc.event.ServerTickEvent;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.ThreadFactory;

public class Markers {

    private static final ThreadFactory MARKER_RUNNER_FACTORY = runnable -> new Thread(runnable, "Markers");

    private MinecraftServer server;

    private Thread markerThread;
    private boolean stop;
    private int interval = 20 * 60;
    private boolean running = false;
    private long lastMarker = -1;
    private long markerCount = 0;

    /**
     * Entry point
     */
    public void onInitialize() {

        // Grab a MinecraftServer instance
        ServerStartEvent.EVENT.register(server -> this.server = server);

        // Always start the marker runner if it doesn't exist or is dead (if the server is ticking)
        ServerTickEvent.EVENT.register(server -> {
            if (this.markerThread == null || !this.markerThread.isAlive()) {
                this.markerThread = MARKER_RUNNER_FACTORY.newThread(this::runMarker);
                this.markerThread.start();
            }
        });

        // Stop the marker runner and dump state restore parameters
        ServerStopEvent.EVENT.register(server -> {
            // Stop the marker runner to prevent the server hanging after shutting down
            this.stop = true;
            this.markerThread.interrupt();

            // Log the parameters that should be used to restore the current state after restart
            if (this.running && this.lastMarker >= 0) {
                long timeOffset = System.currentTimeMillis() - this.lastMarker;
                this.log("Marker parameters to use to restore current state: interval=" + this.interval +
                         ", markerCount=" + this.markerCount + ", timeOffset=" + timeOffset);
            }
        });
    }

    /**
     * Starts the markers
     *
     * @param markerCount Marker count to restore or default {@code 0}
     * @param timeOffset     Time offset to restore or default {@code -1}
     * @return {@code true} if passed values were valid and thus started successfully, {@code false} otherwise
     */
    public boolean start(int markerCount, long timeOffset) {
        if (timeOffset >= 0 && markerCount >= 0) {
            this.markerCount = markerCount;
            this.lastMarker = System.currentTimeMillis() - timeOffset;
            this.running = true;

            this.broadcast(this.getFormattedTimeSinceStart() + " | Episode " + (this.markerCount + 1) + " continues.");
            return true;
        } else if (timeOffset == -1 && markerCount == 0) {
            this.running = true;
            return true;
        }
        return false;
    }

    /**
     * Sets the time interval between two marker markers
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
        System.out.println("[Markers] " + message);
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
     * The marker runner
     * This is a runnable that should always run in the background
     */
    private void runMarker() {
        this.log("Starting marker runner");
        do {
            if (running) {
                long currentTime = System.currentTimeMillis();

                if (lastMarker <= 0) {
                    this.lastMarker = currentTime;
                    this.broadcast("Episode 1 starts now.");
                } else if (currentTime > lastMarker + interval * 1000) {
                    this.lastMarker = currentTime;
                    this.markerCount++;
                    this.broadcast(this.getFormattedTimeSinceStart() + " | Episode " + this.markerCount + " ends now. It's time for episode " + (this.markerCount + 1) + "!");
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                break;
            }
        } while (!stop && !Thread.interrupted());
        this.log("Marker runner stopped");
    }

    /**
     * @return The time in second that elapsed since the markers were started
     */
    private int getTimeSinceStart() {
        return (int) (this.interval * this.markerCount + (System.currentTimeMillis() - this.lastMarker) / 1000);
    }

    /**
     * @return The time in second that elapsed since the markers were started formatted in a human readable string
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
