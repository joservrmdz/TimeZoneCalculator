package org.timezonecalculator.timezonedb.transport;

import java.util.Set;

public class TimeZone {
    private String status;
    private String message;
    private String totalPage;
    private String currentPage;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public Set<Zones> getZones() {
        return zones;
    }

    private Set<Zones> zones;

}
