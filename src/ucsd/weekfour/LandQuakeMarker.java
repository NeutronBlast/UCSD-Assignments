package ucsd.weekfour;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class LandQuakeMarker extends EarthquakeMarker {
    public LandQuakeMarker(PointFeature feature) {
        super(feature);
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.ellipse(x, y, 12, 12);

        /* Age */
        if (this.getAge().equals("Past Day")){
            pg.line(x-6, y-6, x+6, y+6);
            pg.line(x+6, y-6, x-6, y+6);
            pg.strokeWeight(6);
        }
    }

    // Get the country the earthquake is in
    public String getCountry() {
        return (String) getProperty("country");
    }
}
