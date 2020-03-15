package org.timezonecalculator.timezonedb.transport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Zones {
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String countryCode;
    private String countryName;
    private String cityName;
    private String regionName;
    private long timestamp;
    private String formatted;
    private String abbreviation;


    public String getAbbreviation() {
        return abbreviation;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getFormatted() {
        return LocalDateTime.parse(formatted, customFormatter)
                .toLocalTime()
                .toString();
    }

    public String getRegionName() {
        return regionName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zones)) return false;
        Zones zones = (Zones) o;
        return timestamp == zones.timestamp &&
                Objects.equals(getCountryCode(), zones.getCountryCode()) &&
                Objects.equals(getCountryName(), zones.getCountryName()) &&
                Objects.equals(getCityName(), zones.getCityName()) &&
                Objects.equals(getRegionName(), zones.getRegionName()) &&
                Objects.equals(getAbbreviation(), zones.getAbbreviation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getCountryName(), getCityName(), getRegionName(), timestamp, getAbbreviation());
    }
}
