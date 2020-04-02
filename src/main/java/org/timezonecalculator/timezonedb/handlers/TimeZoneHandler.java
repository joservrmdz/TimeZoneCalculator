package org.timezonecalculator.timezonedb.handlers;

import org.apache.commons.lang3.StringUtils;
import org.timezonecalculator.timezonedb.services.TimeZoneService;
import org.timezonecalculator.timezonedb.transport.ErrorResponse;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

import javax.inject.Inject;
import javax.inject.Singleton;

import static ratpack.jackson.Jackson.json;

@Singleton
public final class TimeZoneHandler implements Handler {
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String CITY_QUERY_PARAM = "city";
    public static final String PLEASE_TYPE_IN_A_CITY_RESPONSE = "Bad Request: Please type in a city.";
    private String CITY_NOT_FOUND_RESPONSE = "Sorry, We couldn't find a time and timezone for that city. Please try a different one.";
    private String TIME_ZONE_DB_OK_RESPONSE_STATUS = "OK";
    private final TimeZoneService timeZoneService;

    @Inject
    public TimeZoneHandler(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;

    }

    @Override
    public void handle(Context ctx) {
        Promise.value(ctx.getRequest().getQueryParams().get(CITY_QUERY_PARAM))
                .route(
                        StringUtils::isBlank,
                        city -> ctx.render(json(new ErrorResponse(PLEASE_TYPE_IN_A_CITY_RESPONSE))))
                .flatMap(city -> timeZoneService.getTimeZone(city, ctx.getRequest().getQueryParams().get(COUNTRY_QUERY_PARAM)))
                .mapIf(results -> results.getStatus().equals(TIME_ZONE_DB_OK_RESPONSE_STATUS),
                        Jackson::json,
                        results -> json(getErrorResponse()))
                .then(ctx::render);
    }

    private ErrorResponse getErrorResponse() {
        return new ErrorResponse(CITY_NOT_FOUND_RESPONSE);
    }


}