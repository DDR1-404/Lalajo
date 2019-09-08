package com.ddr1.favorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ddr1.favorite.db.DatabaseContract;

import org.json.JSONObject;

import static com.ddr1.favorite.db.DatabaseContract.getColumnInt;
import static com.ddr1.favorite.db.DatabaseContract.getColumnString;

public class TVshowItem implements Parcelable {
    private int id;
    private String poster_path;
    private String title;
    private String release_date;
    private String vote_average;
    private String original_language;
    private String popularity;
    private String overview;

    public TVshowItem(int id, String poster_path, String title, String release_date, String vote_average, String original_language, String popularity, String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.original_language = original_language;
        this.popularity = popularity;
        this.overview = overview;
    }

    public TVshowItem(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.TVColumns.ID_TV);
        this.poster_path = getColumnString(cursor, DatabaseContract.TVColumns.POSTER_TV);
        this.title = getColumnString(cursor, DatabaseContract.TVColumns.TITLE_TV);
        this.release_date = getColumnString(cursor, DatabaseContract.TVColumns.RELEASE_DATE_TV);
        this.vote_average = getColumnString(cursor, DatabaseContract.TVColumns.VOTE_TV);
        this.original_language = getColumnString(cursor, DatabaseContract.TVColumns.LANGUAGE_TV);
        this.popularity = getColumnString(cursor, DatabaseContract.TVColumns.POPULARITY_TV);
        this.overview = getColumnString(cursor, DatabaseContract.TVColumns.OVERVIEW_TV);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public TVshowItem(JSONObject object) {
        try {
            Integer id = object.getInt("id");
            String poster_path = object.getString("poster_path");
            String title = object.getString("name");
            String release_date = object.getString("first_air_date");
            String vote_average = object.getString("vote_average");
            String original_language = object.getString("original_language");
            String popularity = object.getString("popularity");
            String overview = object.getString("overview");

            this.id = id;
            this.poster_path = poster_path;
            this.title = title;
            this.release_date = release_date;
            this.vote_average = vote_average;
            this.original_language = original_language;
            this.popularity = popularity;
            this.overview = overview;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
        dest.writeString(this.original_language);
        dest.writeString(this.popularity);
        dest.writeString(this.overview);
    }

    public TVshowItem() {
    }

    private TVshowItem(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
        this.original_language = in.readString();
        this.popularity = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<TVshowItem> CREATOR = new Parcelable.Creator<TVshowItem>() {
        @Override
        public TVshowItem createFromParcel(Parcel source) {
            return new TVshowItem(source);
        }

        @Override
        public TVshowItem[] newArray(int size) {
            return new TVshowItem[size];
        }
    };
}
