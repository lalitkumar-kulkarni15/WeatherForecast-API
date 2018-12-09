# WeatherForecast-API

This project houses a restfull API to fetch the weather forecast parameters of all the cities in the world. It is implemented using Spring boot and restfull API. Please refer to the Swagger specs at http://localhost:9067/swagger-ui.html. At this URL you will know the structure of the GET request and the JSON response as well. You will also be able to hit the request from the swagger UI and play with it. The app is currently configured to run on the port 9067, however you are free to change it anytime you need by updating it in the application.properties file. 

The application connects to https://openweathermap.org for obtaining the weather forecast for 3 days by invoking their API . It calculates the average of daily ( 06:00 - 18:00 ) and nightly (18:00 - 06:00) of the temperature in degree celcius and pressure in hpa. Thus in the response we can see the day average of city temperature ( from 6:00-18:00 ) and nightly ( 18:00-06:00 ) temperature average per day. for the next 3 consecutive days.  

The app is implemented using Spring boot framework, restfull API and uses JDK 1.8.

The package structure is kept simple. - The flow goes like Http get request -> RestController -> Business -> Services -> (Invokes 3rd part weather) API. and back again. 

Also as mentioned on the swagger specs, there currently exposed is a GET API with 2 parameters a) cityName b) country code.( Please refer the https://openweathermap.org for more details about the exact country codes and city names. In case you specify incorrect country names and city name then the required data will not be found and appropriate error code will be thrown. 

Below is the sample URL of the API exposed in order to get the weather forecast parameters. As mentioned below 2 parameters are passed in the URL i.e. city= and country=. Below URL can find the average weather forecast for 3 days of Pune,India. Please feel free to update these parameters and get the weather data of cities.

URL : http://localhost:9067/weather-forecast/v1/data?city=Pune&countryCd=in 

Apart from the URL you will have to add appropriate header ex : Accept : application/json. in the request.

There are currently sufficient test cases ( Integration test cases + unit test cases ) which has code coverage of around 86.0 % . I will keep on adding the Junits to make it more better.

Please feel free to use this API / extend it and n case you have any suggestions to improove the quality of the code please do let me know.

Thanks and Regards,

Lalit

Email : lalitkulkarniofficial@gmail.com
