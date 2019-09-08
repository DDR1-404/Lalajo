package com.ddr1.lalajo.notification;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.ddr1.lalajo.MainActivity;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.model.NotificationItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReleaseReminder extends BroadcastReceiver {

    private static final String API_KEY = "e148de1e526fe445f5906ccc85ee7fb4";

    private final static String GROUP_KEY = "group_key";
    private final static String TIME_FORMAT = "HH:mm";
    public int ID_RELEASE = 110;
    private int notificationId;

    ArrayList<MovieItem> movieItems;
    List<NotificationItem> notificationItems = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        getRelease(context);
    }

    private void getRelease(final Context context) {

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = dateFormat.format(calendar.getTime());

                    movieItems = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray jsonArray = responseObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String releaseDate = object.getString("release_date");
                        if (releaseDate.equals(currentDate)) {
                            MovieItem movies = new MovieItem(object);
                            movieItems.add(movies);
                        }
                    }

                    if (movieItems.size() > 0) {
                        for (int i = 0; i < movieItems.size(); i++) {
                            String title = movieItems.get(i).getTitle();
                            String message = context.getString(R.string.title_release);
                            notificationItems.add(new NotificationItem(notificationId, title, message));
                            sendNotification(context);
                            notificationId++;
                        }
                    } else {
                        String title = context.getString(R.string.app_name);
                        String message = context.getString(R.string.no_yet_Nmovies);
                        notificationItems.add(new NotificationItem(notificationId, title, message));
                        sendNotification(context);
                        notificationId++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void sendNotification(Context context) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Movie Catalogue";

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID_RELEASE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentTitle(notificationItems.get(notificationId).getTitle())
                .setContentText(notificationItems.get(notificationId).getMessage())
                .setGroup(GROUP_KEY)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, notification);
        }
    }

    public void setReleaseAlarm(Context context, String time) {
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
