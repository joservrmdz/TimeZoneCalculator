package org.timezonecalculator.timezonedb.handlers;

import org.apache.commons.lang3.StringUtils;
import org.timezonecalculator.timezonedb.services.TimeZoneURIBuilder;
import org.timezonecalculator.timezonedb.transport.ErrorResponse;
import org.timezonecalculator.timezonedb.services.TimeZoneDBParser;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MediaType;
import ratpack.http.client.HttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;

import static ratpack.jackson.Jackson.json;

@Singleton
public final class TimeZoneHandler implements Handler {
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String ACCEPT_HEADER = "Accept";
    private final TimeZoneDBParser timeZoneDBParser;
    private final HttpClient httpClient;
    private static final String CITY_QUERY_PARAM = "city";
    public static final String PLEASE_TYPE_IN_A_CITY_RESPONSE = "Bad Request: Please type in a city.";
    private final TimeZoneURIBuilder timeZoneURIBuilder;

    @Inject
    public TimeZoneHandler(TimeZoneDBParser timeZoneDBParser,
                           HttpClient httpClient,
                           TimeZoneURIBuilder timeZoneURIBuilder) {
        this.timeZoneDBParser = timeZoneDBParser;
        this.httpClient = httpClient;
        this.timeZoneURIBuilder = timeZoneURIBuilder;
    }

    @Override
    public void handle(Context ctx) {
        Promise.value(ctx.getRequest().getQueryParams().get(CITY_QUERY_PARAM))
                .route(
                        StringUtils::isBlank,
                        city -> ctx.render(json(new ErrorResponse(PLEASE_TYPE_IN_A_CITY_RESPONSE))))
                .then(city -> getTimeZone(city, ctx));
    }

    private void getTimeZone(String city, Context ctx) {
        String country = ctx.getRequest().getQueryParams().get(COUNTRY_QUERY_PARAM);
        URI timeZoneDBApiURI = URI.create(
                timeZoneURIBuilder.getTimeZoneURI(city, ctx, country));
        httpClient.get(timeZoneDBApiURI, requestSpec ->
                requestSpec.getHeaders().set(ACCEPT_HEADER, MediaType.APPLICATION_JSON))
                .map(receivedResponse -> timeZoneDBParser.parse(receivedResponse.getBody().getText()))
                .then(ctx::render);
    }



}