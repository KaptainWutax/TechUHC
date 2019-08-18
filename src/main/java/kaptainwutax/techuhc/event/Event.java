package kaptainwutax.techuhc.event;

import java.util.ArrayList;
import java.util.List;

public abstract class Event<L> {
    protected final List<L> listeners;

    protected Event() {
        this.listeners = new ArrayList<>();
    }

    public abstract L invoker();

    public final void register(L listener) {
        this.listeners.add(listener);
    }
}
