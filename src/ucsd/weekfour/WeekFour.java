package ucsd.weekfour;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import ucsd.support.ParseFeed;

import java.util.ArrayList;
import java.util.List;

public class WeekFour extends PApplet {
    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

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
            isInCountry(earthquake, m);
        }

        // not inside any country
        return false;
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
        //background(0);
        //map.draw();
    }
}
