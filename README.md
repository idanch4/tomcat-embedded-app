# Tomcat Embedded App
  ## Overview
A very simple web application which simulates a restaurant. 
You can login, search the menu, order a meal and see the status of your meal
(if the your order is ready you would see it immediately, but the option to change the status from UI hasn't been implemented yet)
    
## Running the app
1. git clone https://github.com/idanch4/tomcat-embedded-app.git
2. run mvn install (to download the dependencies)
3. run the main method - it uses an embedded Tomcat server to run the web application on it 

### Endpoints:
  If unchanged, the app runs on port 8082 (change this in main method)
  1. / - homepage 
  2. /listOrders: displays all the orders in the restauran (admin only)
