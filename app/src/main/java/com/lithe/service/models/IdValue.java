package com.lithe.service.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lithe on 09-Mar-18.
 */

public class IdValue implements Parcelable {
    private String id;
    private String value;

    public IdValue() {

    }

    public IdValue(String id, String value) {
        this.id = id;
        this.value = value;
    }

    protected IdValue(Parcel in) {
        id = in.readString();
        value = in.readString();
    }

    public static final Creator<IdValue> CREATOR = new Creator<IdValue>() {
        @Override
        public IdValue createFromParcel(Parcel in) {
            return new IdValue(in);
        }

        @Override
        public IdValue[] newArray(int size) {
            return new IdValue[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(value);
    }
}

