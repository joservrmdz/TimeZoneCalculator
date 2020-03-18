package org.timezonecalculator.timezonedb.services;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import org.timezonecalculator.timezonedb.transport.TimeZone;
import org.yaml.snakeyaml.util.UriEncoder;
import ratpack.exec.Promise;
import ratpack.http.client.HttpClient;

import javax.inject.Singleton;
import java.net.URI;

import static ratpack.http.MediaType.APPLICATION_JSON;

@Singleton
public class TimeZoneService {
    private static final String ACCEPT_HEADER = "Accept";

    private final HttpClient httpClient;
    private final TimeZoneDBConfig config;
    private final Gson gson;

    @Inject
    public TimeZoneService(HttpClient httpClient, TimeZoneDBConfig config, Gson gson) {
        this.httpClient = httpClient;
        this.config = config;
        this.gson = gson;
    }

    public Promise<TimeZone> getTimeZone(String city, String country) {
        URI api = URI.create(String.format(config.getUrl(), config.getApiKey(), UriEncoder.encode(city), country));

        return httpClient.get(api, spec -> spec.getHeaders().set(ACCEPT_HEADER, APPLICATION_JSON))
            .map(response -> response.getBody().getText())
            .map(text -> gson.fromJson(text, TimeZone.class));

    }
}
