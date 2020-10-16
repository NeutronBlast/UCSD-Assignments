package ucsd.weekfive;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PGraphics;

// TODO: Change SimplePointMarker to CommonMarker as the very first thing you do
// in module 5 (i.e. CityMarker extends CommonMarker).  It will cause an error.
// That's what's expected.
public class CityMarker extends CommonMarker  {
    public static int TRI_SIZE = 5;

    public CityMarker(Location location) {
        super(location);
    }

    public CityMarker(Feature city) {
        super(((PointFeature)city).getLocation(), city.getProperties());
        // Cities have properties: "name" (city name), "country" (country name)
        // and "population" (population, in millions)
    }

    /**
     * Implementation of method to draw marker on the map.
     */
    public void draw(PGraphics pg, float x, float y) {
        // Save previous drawing style
        pg.pushStyle();

        pg.fill(0, 248, 161);
        pg.triangle(x, y, x+8, y+8, x+16, y);

        // Restore previous drawing style
        pg.popStyle();
    }

    public String getCity(){
        return getStringProperty("name");
    }

    public String getCountry(){
        return getStringProperty("country");
    }

    public float getPopulation(){
        return Float.parseFloat(getStringProperty("population"));
    }

    @Override
    public void drawMarker(PGraphics pg, float x, float y) {

    }

    @Override
    public void showTitle(PGraphics pg, float x, float y) {

    }
}
