package org.timezonecalculator.timezonedb;

public class TimeZoneDBRequestParameters {
    private String city;
    private String country;

    public TimeZoneDBRequestParameters(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
