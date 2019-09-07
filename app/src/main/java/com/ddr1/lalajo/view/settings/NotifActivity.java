package com.ddr1.lalajo.view.settings;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ddr1.lalajo.R;
import com.ddr1.lalajo.notification.DailyReminder;
import com.ddr1.lalajo.notification.ReleaseReminder;

public class NotifActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch dailySw, releaseSw;
    ReleaseReminder releaseReminder;
    DailyReminder dailyReminder;
    SharedPreferences dailyPref, releasePref;

    private static final String DAILY = "daily";
    private static final String RELEASE = "release";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        dailySw = findViewById(R.id.switchDaily);
        dailySw.setOnCheckedChangeListener(this);

        releaseSw = findViewById(R.id.switchRelease);
        releaseSw.setOnCheckedChangeListener(this);

        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();

        dailyPref = getSharedPreferences(DAILY, MODE_PRIVATE);
        dailySw.setChecked(dailyPref.getBoolean(DAILY, false));

        releasePref = getSharedPreferences(RELEASE, MODE_PRIVATE);
        releaseSw.setChecked(releasePref.getBoolean(RELEASE, false));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case (R.id.switchDaily):
                if (isChecked) {
                    String alarm = "07:00";
                    dailyReminder.setDailyAlarm(getApplicationContext(), alarm);
                    SharedPreferences.Editor editor = getSharedPreferences(DAILY, MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, true);
                    editor.apply();
                } else {
                    dailyReminder.cancelDailyAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(DAILY, MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, false);
                    editor.apply();
                }
                break;

            case (R.id.switchRelease):
                if (isChecked) {
                    String time = "08:00";
                    releaseReminder.setReleaseAlarm(getApplicationContext(), time);
                    SharedPreferences.Editor editor = getSharedPreferences(RELEASE, MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, true);
                    editor.apply();
                } else {
                    releaseReminder.cancelAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(RELEASE, MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, false);
                    editor.apply();
                }
                break;
        }
    }
}
