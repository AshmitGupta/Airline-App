# My Personal Project

## Airline App

- **Purpose of the App**  
  This is a flight ticket booking app which allows the user to book a ticket
from/to their desired destination.
- **Users of the App**  
This app can be used by any person in any profession to travel anywhere around in Canada for
personal and/or professional reasons.
- **My Interest in the project**  
I have always wanted to create a management software for a big organisation like an airline and 
by the means of this project, I am moving one step closer to my goal.

### User Stories
- As a user, I want to be able to view all the flight routes offered by the airlines
- As a user, I want to be able to view all the fares of the offered flights
- As a user, I want to be able to book a ticket from and/or to my desired location
- As a user, I want to be able to collect and redeem my airline reward points
- As a user, I want to be able to change my booking according to my needs.
- As a user, I want to be able to delete any booking 
- As a user, I want to be able to save my bookings
- As a user, I want to be able to load my bookings and add/remove bookings from my existing booking list

### Phase 4: Task 2

Mon Mar 28 14:08:40 PDT 2022   
Added new booking: From: Vancouver To: Toronto

Mon Mar 28 14:08:42 PDT 2022   
Added new booking: From: Toronto To: Waterloo

Mon Mar 28 14:08:45 PDT 2022   
Added new booking: From: Vancouver To: Victoria

Mon Mar 28 14:08:54 PDT 2022   
Removed booking number 1

Mon Mar 28 14:09:03 PDT 2022   
Removed booking number 2

### Phase 4: Task 3

- I would make a new method in InnerRemoveListener class which takes in 
 a JTextField and a colour as parameters and then sets the text of the text field
to "Enter Valid Booking Number" and colour to whatever is passed in as the parameter.
This would help reduce duplication inside the removeBooking() method.
- I would make a new method which takes in a JButton and a String as a parameter and then
sets the text of the button to the String passed as a parameter and set the foreground color 
of the button to black. I would use this function to reduce duplication inside the revertButtons()
method.
- I would make the field imageLabel, a local variable inside the method addImage() since that 
field is not really used anywhere else in any other method or class in the whole program.
