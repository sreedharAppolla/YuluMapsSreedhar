package com.example.yulumapssreedhar.model.pojoClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailListPojo implements Parcelable {
    private String id;
    private String title;
    private double latitude;
    private double longitude;
    private String image;

    public DetailListPojo(String id, String title, double latitude, double longitude, String image) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    protected DetailListPojo(Parcel in) {
        id = in.readString();
        title = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        image = in.readString();
    }

    public static final Creator<DetailListPojo> CREATOR = new Creator<DetailListPojo>() {
        @Override
        public DetailListPojo createFromParcel(Parcel in) {
            return new DetailListPojo(in);
        }

        @Override
        public DetailListPojo[] newArray(int size) {
            return new DetailListPojo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(image);
    }
}
