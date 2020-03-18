package org.timezonecalculator.timezonedb;

import com.google.inject.AbstractModule;
import org.timezonecalculator.timezonedb.handlers.TimeZoneHandler;

public final class TimeZoneDbModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TimeZoneHandler.class);
    }
}
