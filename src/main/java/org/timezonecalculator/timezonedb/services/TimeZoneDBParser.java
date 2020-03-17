package org.timezonecalculator.timezonedb.services;

import com.google.gson.Gson;
import ratpack.jackson.JsonRender;

public interface TimeZoneDBParser {
    String TIME_ZONE_DB_OK_RESPONSE_STATUS = "OK";
    String CITY_NOT_FOUND_RESPONSE = "Sorry, We couldn't find a time and timezone for that city. Please try a different one.";
    Gson gson = new Gson();

    JsonRender parse(String responseText);

}
