package ucsd;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import ucsd.support.ParseFeed;

import java.util.ArrayList;
import java.util.List;

public class WeekThree extends PApplet {
    public static final float THRESHOLD_MODERATE = 5;
    public static final float THRESHOLD_LIGHT = 4;
    private UnfoldingMap map;
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    public void setup(){
        size(1366, 660, OPENGL);
        map = new UnfoldingMap(this, 150,0,1200,660, new Microsoft.HybridProvider());
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        List<Marker> markers = new ArrayList<Marker>();
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);

        for (PointFeature eq: earthquakes){
            SimplePointMarker marker = new SimplePointMarker(eq.getLocation(), eq.getProperties());

            float mg = Float.parseFloat(eq.getProperty("magnitude").toString());

            if (mg < THRESHOLD_LIGHT){
                marker.setColor(color(124, 185, 232));
                marker.setStrokeColor(color(124, 185, 232));
                marker.setStrokeWeight(1);
            }
            else if (mg >= THRESHOLD_LIGHT && mg < THRESHOLD_MODERATE) {
                marker.setColor(color(255, 235, 116));
                marker.setStrokeColor(color(255, 235, 116));
                marker.setStrokeWeight(3);
            }
            else {
                marker.setColor(color(251, 16, 3));
                marker.setStrokeColor(color(251, 16, 3));
                marker.setStrokeWeight(7);
            }

            markers.add(marker);
        }

        map.addMarkers(markers);
    }

    private void addKeys(){
        fill(255, 255, 255);
        rect(10, 50, 170, 200);
        fill(251, 16, 3);
        ellipse(35, 110, 10, 10);
        fill(0,0,0);
        text("Earthquake Key", 55, 70);
        text("5.0 + Magnitude", 55, 115);
        fill(255, 235, 116);
        ellipse(35, 160, 10, 10);
        fill(0,0,0);
        text("4.0 + Magnitude", 55, 165);
        fill(124, 185, 232);
        ellipse(35, 210, 10, 10);
        fill(0,0,0);
        text("Below 4.0", 55, 215);
    }

    public void draw(){
        background(204, 255, 204);
        map.draw();
        addKeys();
    }
}
