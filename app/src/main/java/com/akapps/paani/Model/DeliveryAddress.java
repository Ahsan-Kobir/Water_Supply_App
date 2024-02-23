package com.akapps.paani.Model;

public class DeliveryAddress {
    private String address;
    private String receiverName;
    private String phone;
    private String lon,lat;

    public DeliveryAddress() {
    }

    public DeliveryAddress(String address, String receiverName, String phone, String lon, String lat) {
        this.address = address;
        this.receiverName = receiverName;
        this.phone = phone;
        this.lon = lon;
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
