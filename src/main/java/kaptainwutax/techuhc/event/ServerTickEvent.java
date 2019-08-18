package kaptainwutax.techuhc.event;

import net.minecraft.server.MinecraftServer;

public interface ServerTickEvent {
    Event<ServerTickEvent> EVENT = new Event<ServerTickEvent>() {
        @Override
        public ServerTickEvent invoker() {
            return server -> {
                this.listeners.forEach(listener -> listener.onServerTick(server));
            };
        }
    };

    void onServerTick(MinecraftServer server);
}
