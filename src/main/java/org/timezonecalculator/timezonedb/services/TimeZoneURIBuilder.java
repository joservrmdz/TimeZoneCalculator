package org.timezonecalculator.timezonedb.services;

import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.yaml.snakeyaml.util.UriEncoder;
import ratpack.handling.Context;

import javax.inject.Singleton;

@Singleton
public class TimeZoneURIBuilder {
    public TimeZoneURIBuilder() {
    }

    public String getTimeZoneURI(String city, Context ctx, String country) {
        return String.format(
                ctx.get(TimeZoneDBConfig.class).getUrl(),
                ctx.get(TimeZoneDBConfig.class).getApiKey(),
                UriEncoder.encode(city),
                country);
    }
}