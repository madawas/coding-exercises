package com.acme.serviceavailability.impl;

import com.acme.serviceavailability.AvailabilityChecker;
import com.acme.serviceavailability.AvailabilityStatus;
import com.acme.serviceavailability.TechnicalFailureException;

import java.util.Arrays;

/**
 * Implementation of {@link AvailabilityChecker}
 */
public class AvailabilityCheckerImpl implements AvailabilityChecker {

    // Assume that these post codes has the 3D capability
    private final String[] validPostCodes = {"3156", "3224", "6753", "6544"};
    // Assume that these post codes are planned to have the 3D capability in future
    private final String[] plannedPostCodes = {"4525", "2416"};
    // Assume that these post codes does not have 3D capability
    private final String[] unavailablePostCodes = {"7676", "8233", "6754"};


    @Override
    public String isPostCodeIn3DTVServiceArea(String postCode) throws TechnicalFailureException {

        AvailabilityStatus serviceStatus;

        if (postCode == null) {
            serviceStatus = AvailabilityStatus.POSTCODE_INVALID;
        } else if (Arrays.stream(validPostCodes).anyMatch(postCode::equals)) {
            serviceStatus = AvailabilityStatus.SERVICE_AVAILABLE;
        } else if (Arrays.stream(plannedPostCodes).anyMatch(postCode::equals)) {
            serviceStatus = AvailabilityStatus.SERVICE_PLANNED;
        } else if (Arrays.stream(unavailablePostCodes).anyMatch(postCode::equals)){
            throw new TechnicalFailureException();
        } else {
            serviceStatus = AvailabilityStatus.POSTCODE_INVALID;
        }

        return serviceStatus.toString();
    }
}
