package ucsd.weekfour;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import ucsd.support.ParseFeed;

import java.util.ArrayList;
import java.util.List;

public class WeekFour extends PApplet {
    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "data/quiz1.atom";

    // The files containing city names and info and country names and info
    private String cityFile = "data/city-data.json";
    private String countryFile = "data/countries.geo.json";

    // The map
    private UnfoldingMap map;

    // Markers for each city
    private List<Marker> cityMarkers;

    // Markers for each earthquake
    private List<Marker> quakeMarkers;

    // A List of country markers
    private List<Marker> countryMarkers;

    // helper method to test whether a given earthquake is in a given country
    // This will also add the country property to the properties of the earthquake
    // feature if it's in one of the countries.
    // You should not have to modify this code
    private boolean isInCountry(PointFeature earthquake, Marker country) {
        // getting location of feature
        Location checkLoc = earthquake.getLocation();

        // some countries represented it as MultiMarker
        // looping over SimplePolygonMarkers which make them up to use isInsideByLoc
        if(country.getClass() == MultiMarker.class) {

            // looping over markers making up MultiMarker
            for(Marker marker : ((MultiMarker)country).getMarkers()) {

                // checking if inside
                if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
                    earthquake.addProperty("country", country.getProperty("name"));

                    // return if is inside one
                    return true;
                }
            }
        }

        // check if inside country represented by SimplePolygonMarker
        else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
            earthquake.addProperty("country", country.getProperty("name"));

            return true;
        }
        return false;
    }

    /* prints countries with number of earthquakes as
     * Country1: numQuakes1
     * Country2: numQuakes2
     * ...
     * OCEAN QUAKES: numOceanQuakes
     * */
    private void printQuakes()
    {
        int countries = 1;
        int oceanquakes = 0;
        boolean counted = false;
        List <Object> c = new ArrayList<Object>();

        for (Marker cm: countryMarkers){
            int quakes = 0;

            /* If country isn't already in the list add */
            if (!c.contains(cm.getProperty("name"))){
                c.add(cm.getProperty("name"));

                for (Marker qm: quakeMarkers){
                    if ((cm.getProperty("name") != null && qm.getProperty("country") != null)
                            && (cm.getProperty("name") == qm.getProperty("country"))){
                        quakes++;
                    }

                    /* Iterate through all the null country earthquakes once using a flag */

                    if (!counted){
                        if (qm.getProperty("country") == null){
                            oceanquakes++;
                        }
                    }
                }

                counted = true;
                if (cm.getProperty("name") != null && quakes > 0){
                    System.out.println("Country "+ countries + " (" + cm.getProperty("name") + "): " + quakes + "\n");
                    countries++;
                }
            }

            /* If country already exists don't bother */
            else {
                continue;
            }
        }
        System.out.println("OCEAN QUAKES: " + oceanquakes);
    }

    // Checks whether this quake occurred on land.  If it did, it sets the
    // "country" property of its PointFeature to the country where it occurred
    // and returns true.
    private boolean isLand(PointFeature earthquake) {
        // Loop over all the country markers.
        // For each, check if the earthquake PointFeature is in the
        // country in m.  Notice that isInCountry takes a PointFeature
        // and a Marker as input.
        // If isInCountry ever returns true, isLand should return true.
        for (Marker m : countryMarkers) {
            if (isInCountry(earthquake, m))
                return true;
        }

        // not inside any country
        return false;
    }

    private void addKey() {
        // Remember you can use Processing's graphics methods here
        fill(255, 250, 240);
        rect(25, 50, 165, 450);

        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Earthquake Key", 65, 75);

        fill(color(0, 248, 161));
        triangle(50, 125, 50+8, 125+8, 50+16, 125);
        fill(color(255, 255, 255));
        ellipse(56, 167, 10, 10);
        fill(color(255, 255, 255));
        rect(50, 203, 10, 10);

        fill(color(255, 250, 148));
        ellipse(50, 295, 10, 10);
        fill(color(0, 131, 195));
        ellipse(50, 335, 10, 10);
        fill(color(206, 73, 73));
        ellipse(50, 375, 10, 10);

        fill(color(255, 255, 255));
        rect(50, 450, 10, 10);
        line(50, 450+10, 50+10, 450);
        line(50, 450, 50+10, 450+10);

        fill(0, 0, 0);
        text("City Marker", 75, 125);
        text("Land Quake", 75, 165);
        text("Ocean Quake", 75, 205);
        text("Size ~ Magnitude", 50, 255);
        text("Shallow", 75, 295);
        text("Intermediate", 75, 335);
        text("Deep", 75, 375);
        text("Age ~ Date", 50, 415);
        text("Past Day", 75, 455);
    }

    public void setup(){
        size(1366, 660, OPENGL);
        map = new UnfoldingMap(this, 0, 50, 1200, 660, new Microsoft.HybridProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        // STEP 1: load country features and markers
        List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
        countryMarkers = MapUtils.createSimpleMarkers(countries);

        /* -------------------------------------------------------------------------
            Properties:
            1. Country (name)

           ------------------------------------------------------------------------
        */

        // STEP 2: read in city data
        List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
        cityMarkers = new ArrayList<Marker>();
        for(Feature city : cities) {
            cityMarkers.add(new CityMarker(city));
            /* -------------------------------------------------------------------------
                Properties:
                1. Country (name)
                2. City
                3. Coastal (true/false)
                4. Population
                ------------------------------------------------------------------------
             */
        }

        // STEP 3: read in earthquake RSS feed
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        quakeMarkers = new ArrayList<Marker>();

        /* -------------------------------------------------------------------------
            Properties:
            1. Depth
            2. Magnitude
            3. Title
            4. Age
             ------------------------------------------------------------------------
         */

        for(PointFeature feature : earthquakes) {
            //check if LandQuake
            if(isLand(feature)) {
                quakeMarkers.add(new LandQuakeMarker(feature));
            }
            // OceanQuakes
            else {
                quakeMarkers.add(new OceanQuakeMarker(feature));
            }
        }

        /* could be used for debugging
        printQuakes();*/

        // Add markers to map
        map.addMarkers(quakeMarkers);
        map.addMarkers(cityMarkers);

    }

    public void draw(){
        background(0);
        map.draw();
        addKey();
    }
}
