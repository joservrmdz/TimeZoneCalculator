import groovy.json.JsonSlurper
import org.timezonecalculator.Server
import ratpack.test.MainClassApplicationUnderTest
import spock.lang.Shared
import spock.lang.Specification

class TimeZoneCalculatorSpec extends Specification {

    @Shared
    def mainClassApplicationUnderTest = new MainClassApplicationUnderTest(Server.class)

    def "starts the web application"() {

        when:
        def statusCode = mainClassApplicationUnderTest.httpClient.get().statusCode

        then:
        statusCode == 200

    }

    def "returns CET timezone for Barcelona, Spain"() {
        given:
        def json = new JsonSlurper()

        when:
        def response = mainClassApplicationUnderTest.httpClient.get("time?city=Barcelona&country=ES")

        then:
        String TZ = json.parseText(response.body.text).getAt("zones").getAt("abbreviation")
        TZ == "[CET]"
    }

    def "returns error messaage for request with misssing City"(){
        given:
        def json = new JsonSlurper()

        when:
        def response = mainClassApplicationUnderTest.httpClient.get("time?city=&country=US")

        then:
        def results = json.parseText(response.body.text)
        results.errorMessage ==  "Please type in a city."

    }

    def "returns error message with the City"(){
        given:
        def json = new JsonSlurper()

        when:
        def response = mainClassApplicationUnderTest.httpClient.get("time?city=Barcelona&country=HN")

        then:
        def results = json.parseText(response.body.text)
        results.errorMessage ==  "Record not found."

    }


}
