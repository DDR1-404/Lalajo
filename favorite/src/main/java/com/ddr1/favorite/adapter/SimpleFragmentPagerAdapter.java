package com.ddr1.favorite.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ddr1.favorite.R;
import com.ddr1.favorite.view.FavoriteMovieFragment;
import com.ddr1.favorite.view.FavoriteTVFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FavoriteMovieFragment();
        } else {
            return new FavoriteTVFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_movie);
            case 1:
                return mContext.getString(R.string.title_tv);
            default:
                return null;
        }
    }

}
