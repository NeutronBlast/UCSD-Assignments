package ucsd.weekfour;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class OceanQuakeMarker extends EarthquakeMarker {
    public OceanQuakeMarker(PointFeature feature) {
        super(feature);

        isOnLand = false;
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.rect(x, y, 13,13);
    }
}
