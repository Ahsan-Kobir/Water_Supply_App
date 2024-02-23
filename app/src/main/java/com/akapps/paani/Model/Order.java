package com.akapps.paani.Model;

import java.util.List;

public class Order {
    private int id;
    private String user_uid;
    private String paymentMethod;
    private String totalPrice;
    private long time;
    private DeliveryAddress deliveryAddress;
    private List<Product> productList;
}
