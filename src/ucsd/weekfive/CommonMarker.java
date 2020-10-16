package ucsd.weekfive;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

import java.util.HashMap;

public abstract class CommonMarker extends SimplePointMarker {
    protected boolean clicked = false;

    public CommonMarker (Location location){
        super(location);
    }

    public CommonMarker(Location location, HashMap<String, Object> properties){
        super(location, properties);
    }

    public boolean getClicked(){
        return clicked;
    }

    public void setClicked(boolean state){
        clicked = state;
    }

    // Common piece of drawing method for markers;
    // Note that you should implement this by making calls
    // drawMarker and showTitle, which are abstract methods
    // implemented in subclasses
    public void draw(PGraphics pg, float x, float y){
        if (!hidden){
            drawMarker(pg, x, y);
            if (selected){
                showTitle(pg, x, y);
            }
        }
    }

    public abstract void drawMarker(PGraphics pg, float x, float y);
    public abstract void showTitle(PGraphics pg, float x, float y);
}
