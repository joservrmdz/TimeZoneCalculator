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

    def "returns CET for Barcelona, Spain"()
    {
        given:
        def json = new JsonSlurper()

        when:
        def response = mainClassApplicationUnderTest.httpClient.get("time?city=Barcelona&country=ES")

        then:
        String TZ = json.parseText(response.body.text).getAt("zones").getAt("abbreviation") == "[CET]"



    }




}
