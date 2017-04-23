package com.aradhya.krishna.whiteboard.base.UI.Grievances;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.UI.viewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class GrievanceFragment extends Fragment {
    private ViewPager viewPager;
    private static final String TAG="GrievanceFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grievance_fragment_layout,container,false);
        viewPager = (ViewPager) v.findViewById(R.id.grievance_pager);
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"grievance frag");

        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);

    }


    public class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new AnonymousComplaintsFragment();
                    break;
                case 1:
                    fragment = new PrivateComplaints();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position==0)
            {
                return "Anonymous";
            }else{
                return "Private";
            }
        }
    }


}
