package org.timezonecalculator.timezonedb.services;

import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TimeZoneURIBuilder {
    private TimeZoneDBConfig timeZoneDBConfig;

    @Inject
    public TimeZoneURIBuilder(TimeZoneDBConfig timeZoneDBConfig) {
        this.timeZoneDBConfig = timeZoneDBConfig;
    }

    public String getTimeZoneURI(String city, String country) {
        return String.format(
                timeZoneDBConfig.getUrl(),
                timeZoneDBConfig.getApiKey(),
                UriEncoder.encode(city),
                country);
    }
}