Datatype Design for MP5

*****
Database (superclass): implements MP5DB (interface)
  -
YelpDB: extends Database
****


MP5 datatypes
*****
Business: datatype to represent business
Restaurant: extends Business
  - Set<Review>
*****
Person: datatype to represent person
YelpUser: extends Person; creates specific fields according to given User.JSON file
*****
Review: datatype to represent a review
YelpReview: extends Review
*****


Other datatypes:
******
Location: datatype to represent a location
AddressLocation: extends Location
GeographicalLocation: extends Location
******
ID: datatype to represent an Identification
BusinessID: extends ID
UserID: extends ID
ReviewID: extends ID
******
Reaction (enum) : datatype to represent a reaction within a YelpReview
  -- Cool
  -- Useful
  -- Funny
******
