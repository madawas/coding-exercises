package com.acme.serviceavailability;

/**
 * Enum for holding the status information
 */
public enum AvailabilityStatus {
    SERVICE_AVAILABLE("3DTV service is available for the given post code"),
    SERVICE_UNAVAILABLE("3DTV service is unavailable for the given post code"),
    SERVICE_PLANNED("3DTV service is not available right now, but it should be available within the next 3 months"),
    POSTCODE_INVALID("The supplied postcode is invalid");

    private final String description;

    AvailabilityStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
