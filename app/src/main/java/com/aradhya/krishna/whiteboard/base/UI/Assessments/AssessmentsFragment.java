package com.aradhya.krishna.whiteboard.base.UI.Assessments;


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
import com.aradhya.krishna.whiteboard.base.UI.viewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class AssessmentsFragment extends Fragment {
    ViewPager pager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assessments_fragment,container,false);
        pager = (ViewPager) v.findViewById(R.id.assessment_pager);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> tabs = new ArrayList<>();
        tabs.add("Assignments");
        tabs.add("Test");
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

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
                    fragment = new ClassAssignments();
                    break;
                case 1:
                    fragment = new TestFragment();
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
                return "Assignments";
            }else{
                return "Test";
            }
        }
    }
}
