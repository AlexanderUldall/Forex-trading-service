# Preamble
Service that can return trading cut-off times for currency pairs on a given date. The cut-off time in FX trading is the deadline for when a trade can be made.

Example: <br>
CZK cannot be traded later than 11.00 today, and<br>
EUR cannot be traded later than 16.00 today, therefore,<br>
the cut-off time for today for the currency pair EUR/CZK is 11.00.<br>

# Technical implementation considerations
The application has been made to run as easily as possible. Simply run ForexTradingServiceApplication with default configurations. All configuration is in the application.properties file.<br>
The service will always return a timestamp provided a valid request is given. A valid request is as follows:<br>
- Two currency codes from the provided enum list
- A date in the format 'yyyy-MM-DD' and not in the past. 

The timestamp can range from not possible '00:00:00' to always possible '23:59:59.999999999'

# Links
- SWAGGER LINK: http://localhost:8080/swagger-ui/index.html#/
- H2 console: http://localhost:8080/console (user/password)

# Postman collection
A postman collection have been provided within 'resource/postman/FOREX SERVICE.postman_collection.json'. Variables for the collection can be found in the collection under pre-request script.

# Caching
Since the data is assumed to be static, a simple cache has been added for DB calls to reduce the load.

# Logging
Logging has been kept to a minimum in order to not clutter up the code. Logging is only done for the database service in order to that demonstrate the caching works on subsequent calls with the same currency. 
