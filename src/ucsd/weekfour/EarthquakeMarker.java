package ucsd.weekfour;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

import java.awt.*;

public abstract class EarthquakeMarker extends SimplePointMarker {
    protected boolean isOnLand;

    /** Greater than or equal to this threshold is a moderate earthquake */
    public static final float THRESHOLD_MODERATE = 5;
    /** Greater than or equal to this threshold is a light earthquake */
    public static final float THRESHOLD_LIGHT = 4;

    /** Greater than or equal to this threshold is an intermediate depth */
    public static final float THRESHOLD_INTERMEDIATE = 70;
    /** Greater than or equal to this threshold is a deep depth */
    public static final float THRESHOLD_DEEP = 300;


    // abstract method implemented in derived classes
    public abstract void drawEarthquake(PGraphics pg, float x, float y);

    // constructor
    public EarthquakeMarker (PointFeature feature)
    {
        super(feature.getLocation());
        // Add a radius property and then set the properties
        java.util.HashMap<String, Object> properties = feature.getProperties();
        float magnitude = Float.parseFloat(properties.get("magnitude").toString());
        properties.put("radius", 2*magnitude );
        setProperties(properties);
        this.radius = 1.75f*getMagnitude();
    }

    // calls abstract method drawEarthquake and then checks age and draws X if needed
    public void draw(PGraphics pg, float x, float y) {
        // save previous styling
        pg.pushStyle();

        // determine color of marker from depth
        colorDetermine(pg);

        // call abstract method implemented in child class to draw marker shape
        drawEarthquake(pg, x, y);

        // OPTIONAL TODO: draw X over marker if within past day

        // reset to previous styling
        pg.popStyle();

    }

    // determine color of marker from depth, and set pg's fill color
    // using the pg.fill method.
    // We suggest: Deep = red, intermediate = blue, shallow = yellow
    // But this is up to you, of course.
    // You might find the getters below helpful.
    private void colorDetermine(PGraphics pg) {
        if (this.getDepth() < 70){
            pg.fill(255, 250, 148);
        }
        else if (this.getDepth() >= 70 && this.getDepth() < 300){
            pg.fill(0, 131, 195);
        }
        else {
            pg.fill(206, 73, 73);
        }
    }


    /*
     * getters for earthquake properties
     */

    public float getMagnitude() {
        return Float.parseFloat(getProperty("magnitude").toString());
    }

    public float getDepth() {
        return Float.parseFloat(getProperty("depth").toString());
    }

    public String getAge(){
        return (String) getProperty("age");
    }

    public String getTitle() {
        return (String) getProperty("title");

    }

    public float getRadius() {
        return Float.parseFloat(getProperty("radius").toString());
    }

    public boolean isOnLand()
    {
        return isOnLand;
    }

}
