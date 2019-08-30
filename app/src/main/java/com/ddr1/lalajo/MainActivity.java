package com.ddr1.lalajo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.ddr1.lalajo.adapter.FragmentAdapter;
import com.ddr1.lalajo.view.favorite.FavoriteFragment;
import com.ddr1.lalajo.view.movies.MovieFragment;
import com.ddr1.lalajo.view.tvshows.TVFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_tv:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_favorite:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = findViewById(R.id.viewpager);
        setupFm(getSupportFragmentManager(), viewPager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new PageChange());
    }

    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);

        Adapter.add(new MovieFragment(), "Page One");
        Adapter.add(new TVFragment(), "Page Two");
        Adapter.add(new FavoriteFragment(), "Page Three");

        viewPager.setAdapter(Adapter);
    }

    public class PageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_movie);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_tv);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_favorite);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
