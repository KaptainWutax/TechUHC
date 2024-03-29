package kaptainwutax.techuhc;

import kaptainwutax.techuhc.command.CommandIntegerRule;
import kaptainwutax.techuhc.command.CommandManager;
import kaptainwutax.techuhc.command.CommandMarkers;
import kaptainwutax.techuhc.command.CommandBooleanRule;
import kaptainwutax.techuhc.markers.Markers;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class TechUHC implements ModInitializer {

    private static Markers MARKERS = new Markers();

    @Override
    public void onInitialize() {
        MARKERS.onInitialize();
        CommandManager.INSTANCE.registerCommand(new CommandBooleanRule());
        CommandManager.INSTANCE.registerCommand(new CommandIntegerRule());
        CommandManager.INSTANCE.registerCommand(new CommandMarkers());
    }

    public static Markers getMarkers() {
        return MARKERS;
    }

}
