package in.varadhismartek.pathshalaerp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.varadhismartek.pathshalaerp.AddStudent.SetAdmissionBarriersFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer , new LandingFragment())
                .addToBackStack(null).commit();
        loadFragment(Constant.FRAGMENT_LANDING, null);
    }

    public void loadFragment(String fragmentString, Bundle bundle) {

        switch (fragmentString) {

            case Constant.FRAGMENT_LANDING:
                callFragment(new LandingFragment(), Constant.FRAGMENT_LANDING, null, null);
                break;

            case Constant.FRAGMENT_ADMISSIONBARRIER:
                callFragment(new SetAdmissionBarriersFragment(), Constant.FRAGMENT_ADMISSIONBARRIER, null, null);
                break;


        }
    }

    private void callFragment(Fragment fragment, String tag, String addBackStack, Bundle bundle) {
        if(bundle!= null){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer ,fragment,tag).addToBackStack(addBackStack).commit();
            fragment.getArguments();

        }

        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer ,fragment,tag).addToBackStack(addBackStack).commit();

        }

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count==0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }


}
