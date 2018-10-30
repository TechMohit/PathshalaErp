package in.varadhismartek.pathshalaerp.AddStudent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;



public class ViewPagerAdapter extends FragmentPagerAdapter {
    String fragments [] = {"All", "Spend" , "Added", "Facility"};
    Context mContext;

    public ViewPagerAdapter(FragmentManager manager, Context mContext) {
        super(manager);
        this.mContext = mContext;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: // Fragment # 0 - This will show FirstFragment
                return new StudentInfoFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new FamilyInfoFragment();
            case 2: // Fragment # 1 - This will show SecondFragment
                return new AddressInfoFragment();
            case 3: // Fragment # 1 - This will show SecondFragment
                return new FacilityInfoFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public int getItemPosition(Object object) {
       return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }

}