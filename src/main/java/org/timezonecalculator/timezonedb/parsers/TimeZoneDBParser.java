package org.timezonecalculator.timezonedb.parsers;

import ratpack.jackson.JsonRender;

public interface TimeZoneDBParser {

    JsonRender parse(String responseText);

}
