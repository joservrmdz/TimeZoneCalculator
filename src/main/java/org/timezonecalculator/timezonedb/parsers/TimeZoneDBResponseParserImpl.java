package org.timezonecalculator.timezonedb.parsers;

import com.google.gson.Gson;
import org.timezonecalculator.timezonedb.transport.ErrorResponse;
import org.timezonecalculator.timezonedb.transport.Results;
import ratpack.jackson.JsonRender;

import static ratpack.jackson.Jackson.json;

public class TimeZoneDBResponseParserImpl implements TimeZoneDBParser {
    private static final String TIMEZONEDB_OK_RESPONSE_STATUS = "OK";
    private Gson gson = new Gson();

    @Override
    public JsonRender parse(String responseText) {
        Results results = gson.fromJson(responseText, Results.class);
        if (results.getStatus().equals(TIMEZONEDB_OK_RESPONSE_STATUS)) {
            return json(results);
        } else {
            return json(new ErrorResponse(results.getMessage()));
        }
    }


}
