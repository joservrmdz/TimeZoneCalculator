import groovy.json.JsonSlurper
import org.timezonecalculator.Server
import org.timezonecalculator.timezonedb.transport.Results
import ratpack.http.client.ReceivedResponse
import ratpack.test.MainClassApplicationUnderTest
import spock.lang.Shared
import spock.lang.Specification

class TimeZoneCalculatorSpec extends Specification {

    def mainClassApplicationUnderTest = new MainClassApplicationUnderTest(Server.class)
    @Shared
    def json = new JsonSlurper()

    def "returns CET timezone for Barcelona, Spain"() {
        when:
        def response = getTimeZone "time?city=Barcelona&country=ES"

        then:
        def result = json.parseText(response.body.text) as Results
        result.zones[0].abbreviation == "CET"
    }

    def "Returns message: 'Bad Request: Please type in a city.' when the city parameter is missing."() {
        when:
        def response = getTimeZone "time?city=&country=US"

        then:
        json.parseText(response.body.text).errorMessage == "Bad Request: Please type in a city."

    }

    def "Returns message: 'Sorry, We couldn't find a time and timezone for that city. Please try a different one.' when the city doesn't exist."() {
        when:
        def response = getTimeZone"time?city=Barcelona&country=HN"

        then:
        def errorResponse = json.parseText(response.body.text)
        errorResponse.errorMessage == "Sorry, We couldn't find a time and timezone for that city. Please try a different one."

    }

    def "Returns 'WET' as Timezone for Las Palmas, Spain."() {
        when:
        def response = getTimeZone "time?city=Las+Palmas&country=ES"

        then:
        def results = json.parseText(response.body.text) as Results
        results.zones[0].abbreviation == "WET"

    }

    def "Returns 'HN' as Timezone for Tegucigalpa, Honduras."() {
        when:
        def response = getTimeZone "time?city=Tegucigalpa&country=HN"

        then:
        def results = json.parseText(response.body.text) as Results
        results.zones[0].abbreviation == "CST"

    }

    def "Returns 10 results for Springfield, US."() {
        when:
        def response = getTimeZone "time?city=Springfield&country=US"

        then:
        def results = json.parseText(response.body.text) as Results
        results.zones.size() == 10

    }

    private ReceivedResponse getTimeZone(String query) {
        mainClassApplicationUnderTest.httpClient.get(query)
    }


}
