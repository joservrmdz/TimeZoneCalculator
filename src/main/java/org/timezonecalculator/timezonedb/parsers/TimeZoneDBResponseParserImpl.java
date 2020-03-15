package org.timezonecalculator.timezonedb.parsers;

import com.google.gson.Gson;
import org.timezonecalculator.ErrorResponse;
import org.timezonecalculator.timezonedb.transport.Results;
import ratpack.jackson.JsonRender;

import static ratpack.jackson.Jackson.json;

public final class TimeZoneDBResponseParserImpl implements TimeZoneDBParser {
    private static final String TIME_ZONE_DB_OK_RESPONSE_STATUS = "OK";
    private final Gson gson = new Gson();

    @Override
    public final JsonRender parse(String responseText) {
        Results results = gson.fromJson(responseText, Results.class);
        if (results.getStatus().equals(TIME_ZONE_DB_OK_RESPONSE_STATUS)) {
            return json(results);
        } else {
            return json(new ErrorResponse(results.getMessage()));
        }
    }


}
