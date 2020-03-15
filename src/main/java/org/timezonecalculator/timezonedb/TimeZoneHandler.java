package org.timezonecalculator.timezonedb;

import org.junit.platform.commons.util.StringUtils;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.timezonecalculator.timezonedb.parsers.TimeZoneDBParser;
import org.timezonecalculator.ErrorResponse;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MediaType;
import ratpack.http.client.HttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import static ratpack.jackson.Jackson.json;

@Singleton
public class TimeZoneHandler implements Handler {
    private static final String CITY_QUERY_PARAM = "city";
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String PLEASE_TYPE_IN_A_CITY_MESSAGE = "Please type in a city.";
    private static final String COULDN_T_ENCODE_CITY_NAME_MESSAGE = "Couldn't encode city name";
    private static final String UTF_8 = "UTF-8";
    private final TimeZoneDBParser timeZoneDBParser;

    @Inject
    public TimeZoneHandler(TimeZoneDBParser timeZoneDBParser) {
        this.timeZoneDBParser = timeZoneDBParser;
    }

    @Override
    public void handle(Context ctx) {

        try {
            String city = URLEncoder.encode(ctx.getRequest().getQueryParams().get(CITY_QUERY_PARAM), UTF_8);

            String country = ctx.getRequest().getQueryParams().get(COUNTRY_QUERY_PARAM);
            HttpClient httpClient = ctx.get(HttpClient.class);
            if (StringUtils.isNotBlank(city)) {
                URI timeZoneDBApiURI = URI.create(
                        String.format(
                                ctx.get(TimeZoneDBConfig.class).getUrl(),
                                ctx.get(TimeZoneDBConfig.class).getApiKey(),
                                city,
                                country));
                httpClient.get(timeZoneDBApiURI, requestSpec ->
                        requestSpec.getHeaders().set(ACCEPT_HEADER, MediaType.APPLICATION_JSON))
                        .then(response -> ctx.render(timeZoneDBParser.parse(response.getBody().getText())));
            } else {
                ctx.render(json(new ErrorResponse(PLEASE_TYPE_IN_A_CITY_MESSAGE)));
            }
        } catch (UnsupportedEncodingException e) {
            ctx.render(json(new ErrorResponse(COULDN_T_ENCODE_CITY_NAME_MESSAGE)));
        }
    }

}