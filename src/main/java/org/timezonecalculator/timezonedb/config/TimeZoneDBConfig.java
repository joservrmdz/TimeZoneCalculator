package org.timezonecalculator.timezonedb.config;

import com.google.common.io.Resources;

import javax.inject.Singleton;
import java.net.URL;

@Singleton
public final class TimeZoneDBConfig {
    private static final String CONFIGURATION_FILE_NAME = "config.json";
    private static final URL jsonConfig = Resources.getResource(CONFIGURATION_FILE_NAME);
    private String apiKey;
    private String url;

    public static URL getJsonConfigPath() {
        return jsonConfig;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUrl() {
        return url;

    }
}