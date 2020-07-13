package com.homie.psychq.main.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.homie.psychq.main.ui.factspool.DateFragment;
import com.homie.psychq.main.ui.factspool.NumberFragment;
import com.homie.psychq.main.ui.factspool.YearFragment;


/*Adapter for switching through fragments of FactsPool section*/

public class FactsPoolAdapter extends FragmentPagerAdapter {



    public FactsPoolAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new NumberFragment(); //ChildFragment1 at position 0
            case 1:
                return new DateFragment(); //ChildFragment2 at position 1
            case 2:
                return new YearFragment(); //ChildFragment3 at position 2
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
