package kaptainwutax.techuhc;

import kaptainwutax.techuhc.markers.Markers;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class TechUHC implements ModInitializer {

    private static Markers MARKERS = new Markers();

    @Override
    public void onInitialize() {
        MARKERS.onInitialize();
    }

    public static Markers getMarkers() {
        return MARKERS;
    }

}
