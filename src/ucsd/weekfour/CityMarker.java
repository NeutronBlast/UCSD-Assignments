package ucsd.weekfour;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

public class CityMarker extends SimplePointMarker {
    // The size of the triangle marker
    public static final int TRI_SIZE = 5;

    public CityMarker(Location location){
        super(location);
    }

    public CityMarker(Feature city){
        super(((PointFeature) city).getLocation(), city.getProperties());
    }

    public void draw(PGraphics pg, float x, float y){
        pg.pushStyle();
        pg.triangle(x, y, x+8, y+8, x+16, y);
        pg.fill(0, 248, 161);

        pg.popStyle();
    }
}
