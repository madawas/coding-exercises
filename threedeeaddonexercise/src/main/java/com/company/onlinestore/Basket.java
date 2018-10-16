package com.company.onlinestore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Basket {
    private List<Product> products;
	private Set<AddOn> addOns;
	private String notification;

	public Basket() {
	    products = new ArrayList<>();
	    addOns = new HashSet<>();
    }

    public Set<AddOn> getAddOns() {
        return addOns;
    }

    public void setAddOns(Set<AddOn> addOns) {
        this.addOns = addOns;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
