package com.aradhya.krishna.whiteboard.base.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.aradhya.krishna.whiteboard.base.UI.Assessments.ClassAssignments;
import com.aradhya.krishna.whiteboard.base.UI.Assessments.TestFragment;
import com.aradhya.krishna.whiteboard.base.UI.Grievances.AnonymousComplaintsFragment;
import com.aradhya.krishna.whiteboard.base.UI.Grievances.PrivateComplaints;
import com.aradhya.krishna.whiteboard.base.UI.Notifications.GeneralNotificationFragments;
import com.aradhya.krishna.whiteboard.base.UI.Notifications.PlacementsFragment;

import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class viewPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabtitles;
    private static final String TAG = "VIEWPAGERADAPTER";
    private String fragname;
    public viewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public viewPagerAdapter(FragmentManager fragmentManager, List<String> tabs, String type) {
        super(fragmentManager);
        this.tabtitles = tabs;
        Log.d(TAG,tabs.toString());
        Log.d(TAG,type);

        fragname = type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (fragname){
            case "Grievance":
                Log.d(TAG,"tabtype"+fragname);
                switch (position){
                    case 0:
                        Log.d(TAG,"fragment for position"+position);
                        Fragment anonymous = new AnonymousComplaintsFragment();
                        return anonymous;

                    case 1:
                        Fragment privatecomplaints = new PrivateComplaints();
                        return privatecomplaints;

                    case 2:
                        return null;

                }
            break;
            case "Assessments":
                switch (position){
                    case 0:
                        Fragment assignmentFragment = new ClassAssignments();
                        return assignmentFragment;
                    case 1:
                        Fragment testFragment = new TestFragment();
                        return testFragment;
                }
                break;
            case "Notification":
                switch (position){
                    case 0:
                        Fragment generalNotifications = new GeneralNotificationFragments();
                        return generalNotifications;
                    case 1:
                        Fragment placementNotifications = new PlacementsFragment();
                        return placementNotifications;
                }
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabtitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG,"tab at "+String.valueOf(position)+" "+tabtitles.get(position));
        return tabtitles.get(position);
    }
}
