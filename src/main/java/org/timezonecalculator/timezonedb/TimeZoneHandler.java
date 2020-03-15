package org.timezonecalculator.timezonedb;

import org.junit.platform.commons.util.StringUtils;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.timezonecalculator.timezonedb.parsers.TimeZoneDBParser;
import org.timezonecalculator.timezonedb.transport.ErrorMessage;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MediaType;
import ratpack.http.client.HttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URLEncoder;

import static ratpack.jackson.Jackson.json;

@Singleton
public class TimeZoneHandler implements Handler {
    private static final String CITY_QUERY_PARAM = "city";
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String PLEASE_TYPE_IN_A_CITY_MESSAGE = "Please type in a city.";
    private final TimeZoneDBParser timeZoneDBParser;

    @Inject
    public TimeZoneHandler(TimeZoneDBParser timeZoneDBParser) {
        this.timeZoneDBParser = timeZoneDBParser;
    }

    @Override
    public void handle(Context ctx) {
        String city = URLEncoder.encode(ctx.getRequest().getQueryParams().get(CITY_QUERY_PARAM));
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
        }
        else
        {
            ctx.render(json(new ErrorMessage(PLEASE_TYPE_IN_A_CITY_MESSAGE)));
        }
    }

}