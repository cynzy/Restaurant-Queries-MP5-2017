package ca.ece.ubc.cpen221.mp5.tests;

import ca.ece.ubc.cpen221.mp5.*;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

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
        Restaurant coffeeLabRestaurant = new Restaurant(business);

        //two classes should be the same since one subtypes the other
        assertEquals(coffeeLab, coffeeLabRestaurant);
        //restaurantTests should contain the restaurant Coffee Lab
        assertTrue(set.contains(coffeeLab));


        //***************************
        //checking validity of parsing DatatypeTest1
        assertEquals(coffeeLab.toString(),coffeeLab.getName());
        assertEquals(4.0, coffeeLab.getRating(),0);
        assertEquals("http://www.yelp.com/biz/the-coffee-lab-berkeley-2", coffeeLab.getUrl());
        assertEquals(1,coffeeLab.getPrice());
        assertEquals("8leHSCn0DCa1ilxfbHuCQA",coffeeLab.getBusinessID());
        assertEquals( "http://s3-media2.ak.yelpcdn.com/bphoto/WtJT52wSvRU5CqtsL5ajUA/ms.jpg", coffeeLab.getPhotoUrl());
        assertEquals(12, coffeeLab.getReviewCount());
        assertEquals(coffeeLab.getBusinessID().hashCode(), coffeeLab.hashCode());
        assertTrue(!coffeeLab.equals("m"));


        //**********
        //checking validity of category
        coffeeLab.addCategory("Chocolate");
        assertTrue(coffeeLab.containsCategory("Chocolate"));
        coffeeLab.removeCategory("Chocolate");
        assertTrue(!coffeeLab.getCategories().contains("Chocolate"));
        //**********


        //**********
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

        //checking validity of Coordinates
        assertEquals(coffeeLab.getLocation().getCoordinates().hashCode(),
                (int) (coffeeLab.getLocation().getCoordinates().getlatitude() + coffeeLab.getLocation().getCoordinates().getlongitude()));
        assertTrue(coffeeLab.getLocation().getCoordinates().toString().contains("37.8727784971583"));
        assertTrue(!coffeeLab.getLocation().getCoordinates().equals(","));

        //changing latitude and longitude and checking
        coffeeLab.getLocation().getCoordinates().setlatitude(0);
        coffeeLab.getLocation().getCoordinates().setlongitude(0);
        //should not change internal coordinates since getLocation() only returns a copy of coordinates
        assertTrue(!(coffeeLab.getLocation().getCoordinates().getlatitude() == 0.0));
        assertTrue(!(coffeeLab.getLocation().getCoordinates().getlongitude() == 0.0));


        //removing neighborhoods and checking
        coffeeLab.getLocation().removeNeighbourhood("UC Campus Area");
        assertTrue(coffeeLab.getLocation().getNeighbourhoods().isEmpty());
        coffeeLab.getLocation().removeSchool("University of California at Berkeley");
        assertTrue(coffeeLab.getLocation().getSchool().isEmpty());
        //***********


        //***********
        //modifying instance fields
        //checking validity of other instance fields
        coffeeLab.setOpen(false);
        assertTrue(!coffeeLab.isOpen());
        coffeeLab.setLocation(location);
        assertTrue(coffeeLab.getLocation().getSchool().isEmpty());
        coffeeLab.setPhotoUrl("");
        assertEquals("",coffeeLab.getPhotoUrl());
        coffeeLab.setUrl("");
        assertEquals("",coffeeLab.getUrl());
        //***********

        //***************************

    }



    //testing Reviews
    @Test
    public void test1() throws IOException {

        //parsing DatatypeTest2
        String line;
        String DatatypeTest2 = "data/DatatypeTest2.json";
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(DatatypeTest2));
        line = bufferedReader2.readLine();
        JsonReader jsonReader = Json.createReader(new StringReader(line));
        JsonObject readObject = jsonReader.readObject();

        Review review = new Review(readObject);
        YelpReview yelpReview = new YelpReview(readObject);


        //***************************
        //checking validity of parsing DatatypeTest2
        assertEquals(review.toString(),review.getText());
        assertEquals(3, review.getStars());
        assertEquals(review.getBusinessID().hashCode(), review.hashCode());
        assertEquals(review, yelpReview);
        assertTrue(!review.equals("m"));
        assertTrue(review.getText().contains("pinball machine that works"));
        assertEquals("2008-11-24",review.getDate());
        assertEquals("HLu2iuxxmDYmVGWcitJXXw", review.getUserID());

        //checking validity of hashmap of reactions
        assertEquals(2,yelpReview.getNumReactions(PossibleReactions.COOL));
        assertTrue(yelpReview.getReactionMap().containsKey(PossibleReactions.FUNNY));

    }

    //testing Users
    @Test
    public void test2() throws IOException {

        //parsing DatatypeTest3
        String line;
        String DatatypeTest3 = "data/DatatypeTest3.json";
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(DatatypeTest3));
        line = bufferedReader2.readLine();
        JsonReader jsonReader = Json.createReader(new StringReader(line));
        JsonObject readObject = jsonReader.readObject();

        User user = new User(readObject);
        YelpUser yelpUser = new YelpUser(readObject);


        //***************************
        //checking validity of parsing DatatypeTest3
        assertEquals(user.toString(),user.getName());
        assertEquals(user.hashCode(), (int) user.getAverageRating());
        assertEquals("http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog", user.getUrl());
        assertEquals(user, yelpUser);
        assertTrue(!user.equals("m"));
        assertEquals(29, user.getReviewCount());

        //checking validity of hashmap of reactions
        assertEquals(14,yelpUser.getNumVotes(PossibleReactions.COOL));
        assertTrue(yelpUser.getVotes().containsKey(PossibleReactions.FUNNY));
        yelpUser.addVote(PossibleReactions.COOL);
        assertEquals(15,yelpUser.getNumVotes(PossibleReactions.COOL));

    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Test
    public void test3() {
    	Cluster cluster = new Cluster(0,0);
    	Location location = new Location();
    	assertTrue(cluster.isEmpty());
    	assertTrue(cluster.toString().equals("Centroid:Latitude:0.0, Longitude:0.0|| Set:[]"));
    	assertFalse( cluster.equals(location));
    	
    }

}

