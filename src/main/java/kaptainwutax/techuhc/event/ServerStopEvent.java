package kaptainwutax.techuhc.event;

import net.minecraft.server.MinecraftServer;

public interface ServerStopEvent {
    Event<ServerStopEvent> EVENT = new Event<ServerStopEvent>() {
        @Override
        public ServerStopEvent invoker() {
            return server -> {
                this.listeners.forEach(listener -> listener.onServerStop(server));
            };
        }
    };

    void onServerStop(MinecraftServer server);
}
