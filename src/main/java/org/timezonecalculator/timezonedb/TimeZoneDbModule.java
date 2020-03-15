package org.timezonecalculator.timezonedb;

import com.google.inject.AbstractModule;
import org.timezonecalculator.timezonedb.parsers.TimeZoneDBParser;
import org.timezonecalculator.timezonedb.parsers.TimeZoneDBResponseParserImpl;

public class TimeZoneDbModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TimeZoneHandler.class);
        bind(TimeZoneDBParser.class).to(TimeZoneDBResponseParserImpl.class);
    }
}
