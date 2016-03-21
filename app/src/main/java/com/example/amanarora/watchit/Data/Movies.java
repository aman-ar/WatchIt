package com.example.amanarora.watchit.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aman's Laptop on 3/20/2016.
 */
public class Movies implements Parcelable{

    private String mTitle;
    private String mreleaseDate;
    private String mPoster;
    private Double mRating;
    private String mSynopsis;

    public Movies (){}
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getreleaseDate() {
        String[] format = mreleaseDate.split("-");
        return format[0].trim();
        //return mreleaseDate;

    }

    public void setreleaseDate(String releaseDate) {
            mreleaseDate = releaseDate;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }


    @Override
    public int describeContents() {
        return 0; //ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mTitle);
        dest.writeString(mSynopsis);
        dest.writeString(mreleaseDate);
        dest.writeDouble(mRating);
        dest.writeString(mPoster);
    }

    private Movies(Parcel in) {

        mTitle = in.readString();
        mSynopsis = in.readString();
        mreleaseDate = in.readString();
        mRating = in.readDouble();
        mPoster = in.readString();

    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
