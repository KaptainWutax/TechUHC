package kaptainwutax.techuhc.event;

import net.minecraft.server.MinecraftServer;

public interface ServerStartEvent {
    Event<ServerStartEvent> EVENT = new Event<ServerStartEvent>() {
        @Override
        public ServerStartEvent invoker() {
            return server -> {
                this.listeners.forEach(listener -> listener.onServerStart(server));
            };
        }
    };

    void onServerStart(MinecraftServer server);
}
