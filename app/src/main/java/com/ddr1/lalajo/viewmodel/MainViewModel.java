package com.ddr1.lalajo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.model.TVshowItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "e148de1e526fe445f5906ccc85ee7fb4";
    private MutableLiveData<ArrayList<MovieItem>> listMovies = new MutableLiveData<>();

    public void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> listMovieItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(object);
                        listMovieItems.add(movieItems);
                    }
                    listMovies.postValue(listMovieItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public LiveData<ArrayList<MovieItem>> getMovies() {
        return listMovies;
    }

    private MutableLiveData<ArrayList<TVshowItem>> listTvs = new MutableLiveData<>();

    public void setTvs() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVshowItem> listTvItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        TVshowItem tVshowItem = new TVshowItem(object);
                        listTvItems.add(tVshowItem);
                    }
                    listTvs.postValue(listTvItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public LiveData<ArrayList<TVshowItem>> getTvs() {
        return listTvs;
    }

    public void setSearchMovie(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> listMovieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(object);
                        listMovieItems.add(movieItems);
                    }
                    listMovies.postValue(listMovieItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public void setSearchTV(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVshowItem> listTvItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        TVshowItem tVshowItem = new TVshowItem(object);
                        listTvItems.add(tVshowItem);
                    }
                    listTvs.postValue(listTvItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

}
