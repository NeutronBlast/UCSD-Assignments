package ucsd.weekfour;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class LandQuakeMarker extends EarthquakeMarker {
    public LandQuakeMarker(PointFeature feature) {
        super(feature);
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.ellipse(x, y, x+15, y+15);
    }

    // Get the country the earthquake is in
    public String getCountry() {
        return (String) getProperty("country");
    }
}
