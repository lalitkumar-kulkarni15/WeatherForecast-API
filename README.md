# WeatherForecast-API

This project houses an API to fetch the weather forecast parameters of all the cities in the world. It is implemented using Spring boot and restfull API. Please refer to the Swagger specs at http://localhost:9067/swagger-ui.html. ( Yes, the app is currently configured to run on the port 9067, however you are free to change it anytime you need by updating it in the application.properties file. 

The application connects to https://openweathermap.org for obtaining the weather forecast for 3 days by invoking their API which is exposed. It calculates the average of daily ( 06:00 - 18:00 ) and nightly (18:00 - 06:00) of the temperature in degree celcius and pressure in hpa. 

To begin with, the app is implemented using Spring boot - restfull API and uses primarily JDK 1.8.

The package structure is simple to understand - The flow goes like RestController -> Business -> Services -> (Invokes 3rd part weather) API. and back again. 

Also as mentioned on the swagger specs, there currently exposed is a GET API with 2 parameters a) cityName b) country code.( Please refer the https://openweathermap.org for more details about the country codes and city names. 

There are currently sufficient test cases ( Integration test cases + unit test cases ) which has code coverage of around 86.0 % . I will keep on adding the Junits to make it more better. 

Please feel free to use this API and n case you have any suggestions to improove the quality of the code please do let me know.

Thanks and Regards,

Lalit

Email : lalitkulkarniofficial@gmail.com
