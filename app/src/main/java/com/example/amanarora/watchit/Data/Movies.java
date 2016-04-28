package com.example.amanarora.watchit.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aman's Laptop on 3/20/2016.
 */
public class Movies implements Parcelable {

    private String mTitle;
    private String mreleaseDate;
    private String mPoster;
    private Double mRating;
    private String mSynopsis;
    private int mId;
    private String mTrailerKey;
    private String mBackDrop;
    private int isFavorite = 0;
    private int[] genreId;

    public String getGenreId() {
        String genres = "";
        String[] genreList = new String[genreId.length];
        for (int x = 0; x < genreId.length; x++) {
            if (genreId[x] == 28) {
                genreList[x] = "Action";
            } else if (genreId[x] == 12) {
                genreList[x] = "Adventure";
            } else if (genreId[x] == 16) {
                genreList[x] = "Animation";
            } else if (genreId[x] == 35) {
                genreList[x] = "Comedy";
            } else if (genreId[x] == 80) {
                genreList[x] = "Crime";
            } else if (genreId[x] == 99) {
                genreList[x] = "Documentary";
            } else if (genreId[x] == 18) {
                genreList[x] = "Drama";
            } else if (genreId[x] == 10751) {
                genreList[x] = "Family";
            } else if (genreId[x] == 14) {
                genreList[x] = "Fantasy";
            } else if (genreId[x] == 10769) {
                genreList[x] = "Foreign";
            } else if (genreId[x] == 36) {
                genreList[x] = "History";
            } else if (genreId[x] == 27) {
                genreList[x] = "Horror";
            } else if (genreId[x] == 10402) {
                genreList[x] = "Music";
            } else if (genreId[x] == 9648) {
                genreList[x] = "Mystery";
            } else if (genreId[x] == 10749) {
                genreList[x] = "Romance";
            } else if (genreId[x] == 878) {
                genreList[x] = "Science Fiction";
            } else if (genreId[x] == 10770) {
                genreList[x] = "TV Movie";
            } else if (genreId[x] == 53) {
                genreList[x] = "Thriller";
            } else if (genreId[x] == 10752) {
                genreList[x] = "War";
            } else if (genreId[x] == 37) {
                genreList[x] = "Western";
            }

        }
        for (int x = 0; x < genreList.length; x++) {
            if (x == genreList.length - 1) {
                genres += genreList[x];
                break;
            }
            //Log.v("Genre", mMovie[position].getGenreId()[x] +"for" + mMovie[position].getTitle());
            genres += genreList[x];
            genres += ", ";
        }
        return genres;
    }


    public void setGenreId(int[] genreId) {
        this.genreId = genreId;
    }


    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getBackDrop() {
        return mBackDrop;
    }

    public void setBackDrop(String backDrop) {
        mBackDrop = backDrop;
    }


    public String getTrailerKey() {
        return mTrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Movies() {
    }

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
        dest.writeInt(mId);
        dest.writeIntArray(genreId);
        dest.writeString(mBackDrop);
        dest.writeInt(isFavorite);
        dest.writeString(mTrailerKey);

    }

    private Movies(Parcel in) {

        mTitle = in.readString();
        mSynopsis = in.readString();
        mreleaseDate = in.readString();
        mRating = in.readDouble();
        mPoster = in.readString();
        mId = in.readInt();
        genreId = in.createIntArray(); //Instead of readIntArray use createIntArray
        mBackDrop = in.readString();
        isFavorite = in.readInt();
        mTrailerKey = in.readString();


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
