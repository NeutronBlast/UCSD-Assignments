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

        /* Age */
        if (this.getAge().equals("Past Week")){
            pg.line(x, y+13, x+13, y);
            pg.line(x, y, x+13, y+13);
            pg.strokeWeight(6);
        }
    }
}
