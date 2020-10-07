package com.falconssoft.woodysystem.models;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Orders implements Serializable {

    private double thickness;
    private double length;
    private double width;
    private String grade;
    private double noOfPieces;
    private String bundleNo;
    private String location;
    private String area;
    private String placingNo;
    private String orderNo;
    private String containerNo;
    private String dateOfLoad;
    private String destination;
    private String picture;
    private boolean checked;
    private Bitmap picBitmap;
    private String packingList;
    private String customer;
    private String newCustomer;
    private int serial;
    private int newSerial;// when swapping two rows in loading order
    private boolean isRemove;// to remove list


    public Orders() {
    }

    public Orders(Double thickness,  Double width ,Double length, String grade, Double noOfPieces, String bundleNo, String location, String area
            , String placingNo, String orderNo, String containerNo, String dateOfLoad, String destination, String picture, String packingList, String customer) {
        this.thickness = thickness;
        this.length = length;
        this.width = width;
        this.grade = grade;
        this.noOfPieces = noOfPieces;
        this.bundleNo = bundleNo;
        this.location = location;
        this.area = area;
        this.placingNo = placingNo;
        this.orderNo = orderNo;
        this.containerNo = containerNo;
        this.dateOfLoad = dateOfLoad;
        this.destination = destination;
        this.picture = picture;
        this.packingList = packingList;
        this.customer = customer;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public String getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(String newCustomer) {
        this.newCustomer = newCustomer;
    }

    public int getNewSerial() {
        return newSerial;
    }

    public void setNewSerial(int newSerial) {
        this.newSerial = newSerial;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(double noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public String getBundleNo() {
        return bundleNo;
    }

    public void setBundleNo(String bundleNo) {
        this.bundleNo = bundleNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPlacingNo() {
        return placingNo;
    }

    public void setPlacingNo(String placingNo) {
        this.placingNo = placingNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getDateOfLoad() {
        return dateOfLoad;
    }

    public void setDateOfLoad(String dateOfLoad) {
        this.dateOfLoad = dateOfLoad;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Bitmap getPicBitmap() {
        return picBitmap;
    }

    public void setPicBitmap(Bitmap picBitmap) {
        this.picBitmap = picBitmap;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("THICKNESS", "'" + thickness + "'");
            obj.put("WIDTH", "'" +width+ "'");
            obj.put("LENGTH", "'" +length+ "'");
            obj.put("GRADE", "'" +grade+ "'");
            obj.put("PIECES","'" + noOfPieces+ "'");
            obj.put("BUNDLE_NO", "'" +bundleNo+ "'");
            obj.put("LOCATION", "'" +location+ "'");
            obj.put("AREA", "'" +area+ "'");
            obj.put("PLACING_NO", "'" +placingNo+ "'");
            obj.put("ORDER_NO", "'" +orderNo+ "'");
            obj.put("CONTAINER_NO", "'" +containerNo+ "'");
            obj.put("DATE_OF_LOAD", "'" +dateOfLoad+ "'");
            obj.put("DESTINATION", "'" +destination+ "'");
            obj.put("PIC", "'" +picture+ "'");
            obj.put("PACKING_LIST", "'" +packingList+ "'");
            obj.put("CUSTOMER", "'" +customer+ "'");
            obj.put("SERIAL", "'" +serial+ "'");
            obj.put("NEW_SERIAL", "'" +newSerial+ "'");
            obj.put("NEW_CUSTOMER", "'" +newCustomer+ "'");


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

    public JSONObject getJSONObjectBundleInfo() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("THICKNESS", "'" + thickness + "'");
            obj.put("WIDTH", "'" +width+ "'");
            obj.put("LENGTH", "'" +length+ "'");
            obj.put("GRADE", "'" +grade+ "'");
            obj.put("PIECES","'" + noOfPieces+ "'");
            obj.put("BUNDLE_NO", "'" +bundleNo+ "'");
            obj.put("LOCATION", "'" +location+ "'");
            obj.put("AREA", "'" +area+ "'");
//            obj.put("PLACING_NO", "'" +placingNo+ "'");
//            obj.put("ORDER_NO", "'" +orderNo+ "'");
//            obj.put("CONTAINER_NO", "'" +containerNo+ "'");
//            obj.put("DATE_OF_LOAD", "'" +dateOfLoad+ "'");
//            obj.put("DESTINATION", "'" +destination+ "'");
//            obj.put("PIC", "'" +picture+ "'");
            obj.put("B_SERIAL", "'" +serial+ "'");
            obj.put("CUSTOMER", "'" +customer+ "'");
            obj.put("CUSTOMER", "'" +customer+ "'");


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Orders)
        {
            Orders temp = (Orders) obj;
            if(this.orderNo.equals(temp.orderNo))
                return true;
        }
        return false;

    }
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub

        return (this.orderNo.hashCode());
    }
}
