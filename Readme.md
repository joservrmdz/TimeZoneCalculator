# Time and TimeZoneCalculator 

## Use Cases Implementation
A description of the implementation of the use cases described in the instructions is detailed below.
#### 1. Submitting an empty city name

When the user submits an empty city name, the server will validate the city parameter and will not make an unnecessary 
request to the TimeZoneDB API as I know it's already a bad request. 
It will answer the client with the message `Bad Request: Please type in a city.` I'm not validating the country parameter because 
this is a fixed value from the dropdown and was it not requested in the instructions. 

#### 2. Submitting a city name that is not known 
When the user submits a "city and country" pair that's not known by the timezoneDB, the response of the request will be 
parsed and the field "status" of the payload will be checked. Since it's value will be different than "OK", we know the
the city was not found. 
In that case the server will return: `Sorry, We couldn't find that city. Please try a different one.`

#### 3. Submitting a city name that is not unique
When the user submits a "city " whose name exists more than one in a country, all the results found for that query by 
timezoneDB will be returned to the client.

## Testing
The tests I wrote are written in Spock and they cover all the Use Cases described above.

## Input Data
One of the countries I picked for the dropdown is Honduras.
Here are some cities that can be used for testing Honduras so you don't have to look them up:
- Tegucigalpa
- Comayagua
- San Pedro Sula

## Validations
- I'm UriEncoding the city in the case the user submits a city with whitespaces. For example, "Los Angeles, USA". This will 
ensure we will have a valid url to make the request.
- As described in the first point of this section there's also a validation for when the user submits an "empty city".

## FrontEnd
I tried to use the GroovyMarkupTemplates available in Ratpack but I couldn't find much documentation on them and my IDE
doesn't offer support for it. So I decided use plain HTML file with a form for the input fields and a button that makes 
the HTTPRequest to the server and renders the response using Handlebars.

## Design
Most of the job is done on the TimeZoneHandler. I would've preferred to extract some of that logic to other classes to 
have a better separation of concerns. For example wrap the **httpClient** on a service and inject 
this service in the Handler as imo an HttpClient is more low level. I've seen several examples on github where the `HttpClient` is 
obtained from the `Context` but I'm not sure this is the best design approach. Unfortunately, I need to be a bit more 
proficient with Ratpack's API to achieve that. 





 

