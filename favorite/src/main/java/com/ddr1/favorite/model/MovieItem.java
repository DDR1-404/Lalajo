package com.ddr1.favorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ddr1.favorite.db.DatabaseContract;

import org.json.JSONObject;

import static com.ddr1.favorite.db.DatabaseContract.getColumnInt;
import static com.ddr1.favorite.db.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    private int id;
    private String poster_path;
    private String title;
    private String release_date;
    private String vote_average;
    private String original_language;
    private String popularity;
    private String overview;

    public MovieItem(int id, String poster_path, String title, String release_date, String vote_average, String original_language, String popularity, String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.original_language = original_language;
        this.popularity = popularity;
        this.overview = overview;
    }

    public MovieItem(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.MoviesColumns.ID);
        this.poster_path = getColumnString(cursor, DatabaseContract.MoviesColumns.POSTER);
        this.title = getColumnString(cursor, DatabaseContract.MoviesColumns.TITLE);
        this.release_date = getColumnString(cursor, DatabaseContract.MoviesColumns.RELEASE_DATE);
        this.vote_average = getColumnString(cursor, DatabaseContract.MoviesColumns.VOTE);
        this.original_language = getColumnString(cursor, DatabaseContract.MoviesColumns.LANGUAGE);
        this.popularity = getColumnString(cursor, DatabaseContract.MoviesColumns.POPULARITY);
        this.overview = getColumnString(cursor, DatabaseContract.MoviesColumns.OVERVIEW);
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

    public MovieItem(JSONObject object) {
        try {
            Integer id = object.getInt("id");
            String poster_path = object.getString("poster_path");
            String title = object.getString("title");
            String release_date = object.getString("release_date");
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

    public MovieItem() {
    }

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
        this.original_language = in.readString();
        this.popularity = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}