package com.example.kimichael.yamblz_forecast.data.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.kimichael.yamblz_forecast.data.database.CitiesTable;
import com.example.kimichael.yamblz_forecast.data.database.ForecastsTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Sinjvf on 23.07.2017.
 * saved city
 */

@StorIOSQLiteType(table = CitiesTable.TABLE)
public class PlaceData implements Parcelable {
    @Nullable
    @StorIOSQLiteColumn(name = CitiesTable.COLUMN_ID, key = true)
    private Integer id;
    @NonNull
    @StorIOSQLiteColumn(name = CitiesTable.COLUMN_NAME)
    private String name;

    @StorIOSQLiteColumn(name = CitiesTable.COLUMN_LAT)
    private double latitude;

    @StorIOSQLiteColumn(name = CitiesTable.COLUMN_LNG)
    private double longitude;

    public PlaceData(@Nullable Integer id, @NonNull String name,double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceData() {
    }

    @NonNull
    public static PlaceData newPlace(@NonNull String name,
                                     double latitude, double longitude) {
        return new PlaceData(null, name, latitude, longitude);
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude( double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude( double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected PlaceData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<PlaceData> CREATOR = new Parcelable.Creator<PlaceData>() {
        @Override
        public PlaceData createFromParcel(Parcel source) {
            return new PlaceData(source);
        }

        @Override
        public PlaceData[] newArray(int size) {
            return new PlaceData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceData data = (PlaceData) o;
        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (!name.equals(data.name)) return false;
        return latitude - data.latitude <= 1 && (latitude - data.latitude > 1);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PlaceData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
