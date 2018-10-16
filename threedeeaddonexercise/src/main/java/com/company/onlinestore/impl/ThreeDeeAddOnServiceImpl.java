package com.company.onlinestore.impl;

import com.acme.serviceavailability.AvailabilityChecker;
import com.acme.serviceavailability.AvailabilityStatus;
import com.acme.serviceavailability.TechnicalFailureException;
import com.company.onlinestore.AddOn;
import com.company.onlinestore.Basket;
import com.company.onlinestore.Product;
import com.company.onlinestore.ThreeDeeAddOnService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link ThreeDeeAddOnService}
 */
public class ThreeDeeAddOnServiceImpl implements ThreeDeeAddOnService {

    private AvailabilityChecker availabilityChecker;
    private Map<Product, AddOn> addOnMap;

    public ThreeDeeAddOnServiceImpl(AvailabilityChecker availabilityChecker) {
        this.availabilityChecker = availabilityChecker;
        this.addOnMap = populateAddOnMap();
    }


    @Override
    public void checkFor3DAddOnProducts(Basket basket, String postCode) {
        String serviceAvailability;

        try {
            serviceAvailability = this.availabilityChecker.isPostCodeIn3DTVServiceArea(postCode);
        } catch (TechnicalFailureException e) {
            basket.setNotification(AvailabilityStatus.SERVICE_UNAVAILABLE.getDescription());
            return;
        }

        basket.setNotification(AvailabilityStatus.valueOf(serviceAvailability).getDescription());

        if (AvailabilityStatus.SERVICE_AVAILABLE.toString().equals(serviceAvailability)) {
            addAddOns(basket);
        }
    }

    /**
     * Adds relevant addons to the basket
     * @param basket {@link Basket}
     */
    private void addAddOns(Basket basket) {
        Set<AddOn> addOns = basket.getAddOns();
        for (Product product: basket.getProducts()) {
            if (addOnMap.containsKey(product)) {
                addOns.add(addOnMap.get(product));
            }
        }
    }

    /**
     * Generates product add-on map to keep the mapping between product and map
     * @return {@link Product} and {@link AddOn} map.
     */
    private Map<Product, AddOn> populateAddOnMap() {
        Map<Product, AddOn> addOnMap = new HashMap<>();

        addOnMap.put(Product.SPORTS, AddOn.SPORTS_3D_ADD_ON);
        addOnMap.put(Product.NEWS, AddOn.NEWS_3D_ADD_ON);
        addOnMap.put(Product.MOVIES_1, AddOn.MOVIES_3D_ADD_ON);
        addOnMap.put(Product.MOVIES_2, AddOn.MOVIES_3D_ADD_ON);

        return addOnMap;
    }
}
