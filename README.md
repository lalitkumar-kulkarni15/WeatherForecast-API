# WeatherForecast-API

<b>What is the intent of project ?</b> 

<p>This project houses an API to fetch the weather forecast parameters of all the cities in the world. The weather forecast is provided for 3 consecutive days including the current requested day with parameters - avg day temperture,avg night temperature in celcius and avg pressure in hpa.</p>

<b>How is it technically implemented? </b>

<p>It is implemented using JDK 8 , spring boot framework and restfull API. The medium of transport of the data is JSON ovr the http protocol . 
  
<b>Swagger specs :</b>

<p> Swagger usually helps in the smart way of having technical documentation of the API. Please refer to the Swagger specs at http://localhost:9067/swagger-ui.html. URL . At this URL you will know the structure of the request (which is te http GET request) and the JSON response structure as well. You will also be able to hit the request from the swagger and get the json response back on the UI and play with it. The app is currently configured to run on the port 9067 of the localhost ( In case you are running it on the local server ), however you are free to change it anytime you need ( In  case you wish to deploy it to a different server )  by updating it in the application.properties file.
  
<b>How does the API get the weather data?</b>  

<p>The application connects to https://openweathermap.org for obtaining the weather forecast for 5 days by invoking their API . It calculates the average of daily ( 06:00 - 18:00 ) and nightly (18:00 - 06:00) of the temperature in degree celcius and pressure in hpa.The data for 3 consecutive days is then filtered out of the 5 days data. Thus in the response we can see the day average of city temperature ( from 6:00-18:00 ) and nightly ( 18:00-06:00 ) temperature average per day. for the next 3 consecutive days for each day.
The https://openweathermap.org also exposes the rest API to fetch the weather data of 5 consecutive days which is as of now free to use.However it uses an api-key to authenticate the request. The api-key is obtained by registering on the same site.Currently for this poject I have configured my personal api-key. Please feel free to update your own api- key in the application.properties file. </p> 

<b>Code and package structure</b>

<p>The package structure is kept simple to understand well. Please refer the WIKI section for a detailed package structure walkthrough. - </p>

<b>How to access the API?</b>

<p>Also as mentioned on the swagger specs, there currently exposed is a GET API with 2 parameters a) cityName b) country code.( Please refer the https://openweathermap.org for more details about the exact country codes and city names. In case you specify incorrect country names and city name then the required data will not be found and appropriate error code will be thrown. </p>

<p>Below is the sample URL of the API exposed in order to get the weather forecast parameters. As mentioned below 2 parameters are passed in the URL i.e. city= and country=. Below URL can find the average weather forecast for 3 days of Pune,India. Please feel free to update these parameters and get the weather data of cities.</p>

<p>URL : http://localhost:9067/weather-forecast/v1/data?city=Pune&countryCd=in </p>

<p>Apart from the URL you will have to add appropriate header ex : Accept : application/json. in the request.

Please refer the Swagger ui for the response json structure.</p>

<b> Integration and unit test cases</b>

<p>There are currently sufficient test cases ( Integration test cases + unit test cases ) which has code coverage of around 89.4 % . I will keep on adding the Junits to make it more better.</p>

<b>Code coverage</b>

<p>Code coverage gives us the metrics of how many lines of instructions have we covered of the production code from our test cases. 
Currently the code coverage of the project is above 90%. I have also attached the snapshot of the code coverage report which is collected using the clover plugin in case you are using STS or eclipse. </p>

<b>Static code analysis</b>

<p> The source code is scanned by the sonarlint which is a static code analysis tool.

<p>Please feel free to use this API / extend it and n case you have any suggestions to improove the quality of the code please do let me know.In case of any issues/ bugs found please open an issue and will tr to fix it as soon as possible.</p>

Thanks and Regards,  

Lalit
Email : lalitkulkarniofficial@gmail.com
