**Datatype Design for MP5**

Database: implements MP5DB (interface)
  - Map<Business, Set<Review>>

YelpDB: extends Database  
  - Map<Restaurant, Set<Review>>
  - Map<User, Set<Review>>

**MP5 datatypes**
Business: datatype to represent a business 
  - Boolean open
  - String url
  - Location location
  - String BudinessID
  - String name
  - String photoUrl
  - Set<String> categories
  
Restaurant: extends Business  
  - int stars
  - int reviewCount
  - int price
  
Person: datatype to represent person 
  - String name;

YelpUser: extends Person; creates specific fields according to given User.JSON file  
  - String url
  - Map<Reaction, Integer> reactions
  - int reviewCount
  - String userID
  - double averageStars

Review: datatype to represent a review    
  - String businessID
  - Map<Reaction, Integer> reactions
  - String reviewID
  - String userID
  - String text
  - String date
  - int stars

**Other datatypes**  
Location: datatype to represent a location  
- point coordinates
- String state
- String neighbour
- String school
- String address

Reaction (enum) : datatype to represent a reaction within a YelpReview  
  -- Cool  
  -- Useful  
  -- Funny  

