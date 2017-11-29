package ca.ece.ubc.cpen221.mp5.tests;

import ca.ece.ubc.cpen221.mp5.Business;
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

        assertEquals(27,set.size());

        String DatatypeTest1 = "data/DatatypeTest1.json";
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(DatatypeTest1));
        line = bufferedReader2.readLine();
        JsonReader jsonReader = Json.createReader(new StringReader(line));
        JsonObject business = jsonReader.readObject();
        Business coffeeLab = new Business(business);

        assertTrue(set.contains(coffeeLab));
        assertEquals("The Coffee Lab",coffeeLab.getName());
        assertTrue(coffeeLab.containsCategory("Coffee & Tea"));
        assertEquals(4.0, coffeeLab.getRating(),0);
        assertEquals("http://www.yelp.com/biz/the-coffee-lab-berkeley-2", coffeeLab.getUrl());
        assertTrue(coffeeLab.getLocation().getNeighbourhoods().contains("UC Campus Area"));

        coffeeLab.setOpen(false);
        assertTrue(!coffeeLab.isOpen());

    }

}

