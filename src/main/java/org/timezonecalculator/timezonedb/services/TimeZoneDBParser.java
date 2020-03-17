package org.timezonecalculator.timezonedb.services;

import ratpack.jackson.JsonRender;

public interface TimeZoneDBParser {

    JsonRender parse(String responseText);

}
