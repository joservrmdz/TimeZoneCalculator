package org.timezonecalculator.timezonedb.services;

import com.google.gson.Gson;
import org.timezonecalculator.timezonedb.transport.Results;
import ratpack.exec.Promise;
import ratpack.http.MediaType;
import ratpack.http.client.HttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;

@Singleton
public class TimeZoneService {
    private final TimeZoneURIBuilder timeZoneURIBuilder;
    private final HttpClient httpClient;
    private static final String ACCEPT_HEADER = "Accept";
    private final Gson gson;

    @Inject
    public TimeZoneService(TimeZoneURIBuilder timeZoneURIBuilder, HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.timeZoneURIBuilder = timeZoneURIBuilder;
        this.gson = gson;
    }

    public Promise<Results> getTimeZone(String city, String country) {
        URI timeZoneDBApiURI = URI.create(timeZoneURIBuilder.getTimeZoneURI(city, country));
        return httpClient.get(timeZoneDBApiURI, requestSpec ->
                requestSpec.getHeaders().set(ACCEPT_HEADER, MediaType.APPLICATION_JSON))
                .map(receivedResponse -> gson.fromJson(receivedResponse.getBody().getText(), Results.class));
    }


}
