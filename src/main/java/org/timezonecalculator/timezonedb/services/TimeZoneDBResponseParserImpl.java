package org.timezonecalculator.timezonedb.services;

import com.google.gson.Gson;
import org.timezonecalculator.timezonedb.transport.ErrorResponse;
import org.timezonecalculator.timezonedb.transport.Results;
import ratpack.jackson.JsonRender;

import static ratpack.jackson.Jackson.json;

public final class TimeZoneDBResponseParserImpl implements TimeZoneDBParser {
    private String TIME_ZONE_DB_OK_RESPONSE_STATUS = "OK";
    private String CITY_NOT_FOUND_RESPONSE = "Sorry, We couldn't find a time and timezone for that city. Please try a different one.";
    private Gson gson = new Gson();

    @Override
    public final JsonRender parse(String responseText) {
        Results results = gson.fromJson(responseText, Results.class);
        if (results.getStatus().equals(TIME_ZONE_DB_OK_RESPONSE_STATUS)) {
            return json(results);
        } else {
            return json(new ErrorResponse(CITY_NOT_FOUND_RESPONSE));
        }
    }


}
