package ca.ece.ubc.cpen221.mp5.tests;

import ca.ece.ubc.cpen221.mp5.Business;
import ca.ece.ubc.cpen221.mp5.Location;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Cynzy on 2017-11-27.
 */
public class DatatypeTests {

    //testing Businesses
    @Test
    public void test0() throws IOException {
        String restaurantJson = "data/restaurantsTest.json";
        Set<Business> set = new HashSet<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(restaurantJson));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            JsonReader jsonReader = Json.createReader(new StringReader(line));
            JsonObject business = jsonReader.readObject();
            Business business1 = new Business(business);
            set.add(business1);
        }
        bufferedReader.close();

        //checking size
        assertEquals(27,set.size());

        //parsing DatatypeTest1
        String DatatypeTest1 = "data/DatatypeTest1.json";
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(DatatypeTest1));
        line = bufferedReader2.readLine();
        JsonReader jsonReader = Json.createReader(new StringReader(line));
        JsonObject business = jsonReader.readObject();
        Business coffeeLab = new Business(business);

        //restaurantTests should contain the restaurant Coffee Lab
        assertTrue(set.contains(coffeeLab));

        //checking validity of parsing DatatypeTest1
        assertEquals("The Coffee Lab",coffeeLab.getName());
        assertTrue(coffeeLab.containsCategory("Coffee & Tea"));
        assertEquals(4.0, coffeeLab.getRating(),0);
        assertEquals("http://www.yelp.com/biz/the-coffee-lab-berkeley-2", coffeeLab.getUrl());

        //checking validity of Location class
        assertTrue(coffeeLab.getLocation().getNeighbourhoods().contains("UC Campus Area"));
        assertTrue(coffeeLab.getLocation().getAddress().contains("94720"));
        assertEquals("Berkeley", coffeeLab.getLocation().getCity());
        assertEquals("CA", coffeeLab.getLocation().getState());
        assertEquals(37.8727784971583,coffeeLab.getLocation().getCoordinates().getlatitude(),0);
        assertEquals(-122.255591154098,coffeeLab.getLocation().getCoordinates().getlongitude(),0);
        assertTrue(coffeeLab.getLocation().getSchool().contains("University of California at Berkeley"));
        assertEquals(coffeeLab.getLocation().hashCode(), coffeeLab.getLocation().getNeighbourhoods().hashCode());
        assertTrue(coffeeLab.getLocation().toString().contains(coffeeLab.getLocation().getCity()));

        Location location = new Location();
        assertTrue(!coffeeLab.getLocation().equals(location));
        assertTrue(!coffeeLab.getLocation().equals("m"));

        //removing neighborhoods and checking
        coffeeLab.getLocation().removeNeighbourhood("UC Campus Area");
        assertTrue(coffeeLab.getLocation().getNeighbourhoods().isEmpty());
        coffeeLab.getLocation().removeSchool("University of California at Berkeley");
        assertTrue(coffeeLab.getLocation().getSchool().isEmpty());

        //checking validity of other instance fields
        coffeeLab.setOpen(false);
        assertTrue(!coffeeLab.isOpen());

    }

}

