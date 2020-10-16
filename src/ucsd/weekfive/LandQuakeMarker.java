package ucsd.weekfive;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class LandQuakeMarker extends EarthquakeMarker {
    public LandQuakeMarker(PointFeature feature) {
        super(feature);
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.ellipse(x, y, 12, 12);
    }

    // Get the country the earthquake is in
    public String getCountry() {
        return (String) getProperty("country");
    }
}
