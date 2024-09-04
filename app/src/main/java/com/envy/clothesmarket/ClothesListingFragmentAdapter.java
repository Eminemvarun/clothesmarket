package com.envy.clothesmarket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ClothesListingFragmentAdapter extends FragmentStateAdapter {


    public ClothesListingFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ClothesListingFragment();
        }
        if (position == 1) {
            return new SellFragment();
        }
        if (position == 3) {
            return new ProfileFragment();
        }

        if(position==2){
            return new MessagesFragment();
        }

        else {
            Log.e("ENVY", "Fragment position out of bounds");
            return new ClothesListingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
