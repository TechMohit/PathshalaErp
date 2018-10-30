package in.varadhismartek.pathshalaerp.AddStudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import in.varadhismartek.pathshalaerp.MainActivity;
import in.varadhismartek.pathshalaerp.R;


//this activity is for showing bottom navigation bar
//here we are loaing  fragments which are
//1.Student Info
//2.Family Info
//3.Document Info
//4.Address Info
//5.Facility Info

public class NewAdmissionActivity extends AppCompatActivity {

    Fragment fragment=null;
    Menu menuNav;
    Menu menu;
    private ViewPager viewPager;
    BottomNavigationView StudentAdmissionNavigationButton;
    MenuItem prevMenuItem;
    static boolean active = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admission);


        viewPager = findViewById(R.id.container);
        StudentAdmissionNavigationButton = findViewById(R.id.navigation);
        StudentAdmissionNavigationButton.performClick();
        menu  = StudentAdmissionNavigationButton.getMenu();
        menu.findItem(R.id.student).setIcon(R.drawable.icon_person_fill);


        menuNav=StudentAdmissionNavigationButton.getMenu();
        setupViewPager(viewPager);

        viewPager.setSaveFromParentEnabled(false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Pos", ""+position);

                StudentAdmissionNavigationButton.getMenu().getItem(position).setChecked(true);
                prevMenuItem = StudentAdmissionNavigationButton.getMenu().getItem(position);


                if (prevMenuItem != null)
                {
                    if(position==0){
                        menu.findItem(R.id.student).setIcon(R.drawable.icon_person_fill);
                    }
                    else {
                        menu.findItem(R.id.student).setIcon(R.drawable.icon_person_empty);

                    }
                    if(position==1){
                        menu.findItem(R.id.family).setIcon(R.drawable.icon_family_fill);
                    }
                    else {
                        menu.findItem(R.id.family).setIcon(R.drawable.icon_family_empty);

                    }

                    if(position==2){
                        menu.findItem(R.id.address).setIcon(R.drawable.ic_placeholder);

                    }
                    else {
                        menu.findItem(R.id.address).setIcon(R.drawable.ic_location_pin);

                    }

                    if(position==3){
                        menu.findItem(R.id.facility).setIcon(R.drawable.icon_transpo_fill);

                    }
                    else {
                        menu.findItem(R.id.facility).setIcon(R.drawable.icon_tranpo_empty);

                    }


                    prevMenuItem.setChecked(false);
                }
                else
                {
                    StudentAdmissionNavigationButton.getMenu().getItem(0).setChecked(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        StudentAdmissionNavigationButton.setOnNavigationItemSelectedListener(new
                                                                                     BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                                         @Override
                                                                                         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                              switch (item.getItemId()) {

                                                  case R.id.student:
                                                  viewPager.setCurrentItem(0);
                                                  break;

                                                                                                 //loading fragment 2

                                                  case R.id.family:
                                                  viewPager.setCurrentItem(1);
                                                  break;


                                                                                                 //loading fragment 4

                                                  case R.id.address:
                                                  viewPager.setCurrentItem(2);
                                                  break;


                                                                                                 //loading fragment 5

                                                  case R.id.facility:
                                                  viewPager.setCurrentItem(3);
                                                  break;

                                               }

                                                 return false;

                                             }

                                                                                     });



    }


    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), NewAdmissionActivity.this));
        /*studentInfoFragment=new StudentInfoFragment();
        familyInfoFragment=new FamilyInfoFragment();
        addressInfoFragment = new AddressInfoFragment();
        facilityInfoFragment = new FacilityInfoFragment();

        adapter.addFragment(studentInfoFragment);
        adapter.addFragment(familyInfoFragment);
        adapter.addFragment(addressInfoFragment);
        adapter.addFragment(facilityInfoFragment);

        viewPager.setAdapter(adapter);*/


    }

    private void selectedItems(MenuItem item, Fragment fragment) {
        item.setChecked(true);
        switch (item.getItemId()) {

            //loading fragment 1
            case R.id.student:
                viewPager.setCurrentItem(0);
                menuNav=StudentAdmissionNavigationButton.getMenu();
                break;

            //loading fragment 2

            case R.id.family:
                viewPager.setCurrentItem(1);
                break;


            //loading fragment 4

            case R.id.address:
                viewPager.setCurrentItem(2);
                break;


            //loading fragment 5

            case R.id.facility:
                viewPager.setCurrentItem(3);
                break;

        }

    }



    //here we are handling to move the fragment..after finishing 1st one it will move to 2nd fragment..vice versa
    public void loadNextFragment(int button_enable, Fragment fragment,Bundle bnd) {
        switch (button_enable){
            case 1:
                //getSupportFragmentManager().findFragmentByTag("ABC");
                this.fragment = fragment;
               /* FamilyInfoFragment familyInfoFragment = (FamilyInfoFragment) viewPagerAdapter.getItem(2);
                familyInfoFragment.loadBundle(bnd);*/
                selectedItems(menuNav.getItem(1),fragment);

                break;


            case 2:
                this.fragment = fragment;
                selectedItems(menuNav.getItem(2),fragment);
                break;


            case 3:
                this.fragment = fragment;
                selectedItems(menuNav.getItem(3),fragment);
                break;




        }



    }


    public void loadBundle(Bundle b) {
        /*FamilyInfoFragment familyInfoFragment = new FamilyInfoFragment();
        familyInfoFragment.setArguments(b);*/

    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
    }
}

