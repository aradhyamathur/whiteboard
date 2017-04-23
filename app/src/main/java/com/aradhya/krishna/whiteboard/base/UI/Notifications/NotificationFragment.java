package com.aradhya.krishna.whiteboard.base.UI.Notifications;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.UI.Grievances.GrievanceFragment;
import com.aradhya.krishna.whiteboard.base.UI.viewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment{
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_fragment,container,false);
        viewPager = (ViewPager) v.findViewById(R.id.notification_pager);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        List<String> tabs = new ArrayList<>();
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
//        tabs.add("General");
//        tabs.add("Placements");

        viewPager.setAdapter(adapter);
    }


    public class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new GeneralNotificationFragments();
                    break;
                case 1:
                    fragment = new PlacementsFragment();
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
                return "General";
            }else{
                return "Placements";
            }
        }
    }
}
