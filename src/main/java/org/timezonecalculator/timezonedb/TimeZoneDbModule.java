package org.timezonecalculator.timezonedb;

import com.google.inject.AbstractModule;
import org.timezonecalculator.timezonedb.handlers.TimeZoneHandler;
import org.timezonecalculator.timezonedb.services.TimeZoneService;
import org.timezonecalculator.timezonedb.services.TimeZoneURIBuilder;

public final class TimeZoneDbModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TimeZoneHandler.class);
        bind(TimeZoneURIBuilder.class);
        bind(TimeZoneService.class);
    }
}
