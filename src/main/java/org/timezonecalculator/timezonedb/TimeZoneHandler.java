package org.timezonecalculator.timezonedb;

import org.timezonecalculator.ErrorResponse;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.timezonecalculator.timezonedb.parsers.TimeZoneDBParser;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MediaType;
import ratpack.http.client.HttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import static ratpack.jackson.Jackson.json;

@Singleton
public final class TimeZoneHandler implements Handler {
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String COULD_NOT_ENCODE_CITY_NAME_MESSAGE = "Couldn't encode city name";
    private final TimeZoneDBParser timeZoneDBParser;
    private final HttpClient httpClient;
    private final ParametersExtractor parametersExtractor;

    @Inject
    public TimeZoneHandler(TimeZoneDBParser timeZoneDBParser,
                           HttpClient httpClient,
                           ParametersExtractor parametersExtractor) {
        this.timeZoneDBParser = timeZoneDBParser;
        this.httpClient = httpClient;
        this.parametersExtractor = parametersExtractor;
    }

    @Override
    public void handle(Context ctx) {

        try {
            parametersExtractor.getCity(ctx)
                    .then(city -> getTimeZone(city, ctx));

        } catch (UnsupportedEncodingException e) {
            ctx.render(json(new ErrorResponse(COULD_NOT_ENCODE_CITY_NAME_MESSAGE)));
        }
    }

    private void getTimeZone(String city, Context ctx) {
        String country = ctx.getRequest().getQueryParams().get(COUNTRY_QUERY_PARAM);
        URI timeZoneDBApiURI = URI.create(
                String.format(
                        ctx.get(TimeZoneDBConfig.class).getUrl(),
                        ctx.get(TimeZoneDBConfig.class).getApiKey(),
                        city,
                        country));
        httpClient.get(timeZoneDBApiURI, requestSpec ->
                requestSpec.getHeaders().set(ACCEPT_HEADER, MediaType.APPLICATION_JSON))
                .map(receivedResponse -> timeZoneDBParser.parse(receivedResponse.getBody().getText()))
                .then(ctx::render);
    }

}