package ucsd;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtraOne extends PApplet {
    private UnfoldingMap map;
    private Map<String, Float> life_exp;
    private List<Marker> countryMarkers;
    private List<Feature> countries;

    private Map<String, Float> loadLifeExpectancyFromCSV(String fileName){
        Map <String, Float> exp = new HashMap<String, Float>();
        String [] rows = loadStrings(fileName);

        for (String row: rows){
            String [] cols = row.split(",");
            if (cols.length == 6 && !cols[5].equals("..")){
                float value = Float.parseFloat(cols[5]);
                exp.put(cols[4], value);
            }
        }
        return exp;
    }

    private void shadeCountries(){
        for (Marker marker : this.countryMarkers){
            String countryId = marker.getId();

            if (this.life_exp.containsKey(countryId)){
                float le = this.life_exp.get(countryId);
                int colorLevel = (int) map(le, 40, 90, 10, 255);
                marker.setColor(color(255-colorLevel, 100, colorLevel));
            }
            else {
                marker.setColor(color(150, 150, 150));
            }
        }
    }

    public void setup(){
        size(1366, 600, OPENGL);
        map = new UnfoldingMap(this, 0, 0, 1366, 600, new Microsoft.HybridProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        this.life_exp = loadLifeExpectancyFromCSV("data/LifeExpectancyWorldBankModule.csv");

        this.countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
        this.countryMarkers = MapUtils.createSimpleMarkers(countries);

        map.addMarkers(countryMarkers);
        shadeCountries();
        //this.life_exp.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void draw(){
        background(239, 248, 251);
        map.draw();
    }
}
