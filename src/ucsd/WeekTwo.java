package ucsd;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class WeekTwo extends PApplet {
    UnfoldingMap map_1;
    UnfoldingMap map_2;
    int width;
    int height;

    public WeekTwo(int w, int h){
        this.width = w;
        this.height = h;
    }

    public void setup(){
        size(this.width, this.height, OPENGL);
        AbstractMapProvider provider = new Microsoft.HybridProvider();
        AbstractMapProvider provider_2 = new Microsoft.HybridProvider();
        map_1 = new UnfoldingMap(this, 0, 0, 660, 600, provider);
        map_2 = new UnfoldingMap(this,660 + 46 , 0, 660, 600, provider_2);
        map_1.zoomAndPanTo(10, new Location(32.9, -117.2));
        map_2.zoomAndPanTo(10, new Location(10.34, -67.04));

        MapUtils.createDefaultEventDispatcher(this, map_1);
        MapUtils.createDefaultEventDispatcher(this, map_2);
    }

    public void draw(){
        background(244, 237, 230);
        map_1.draw();
        map_2.draw();
    }
}
