package in.varadhismartek.pathshalaerp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.varadhismartek.pathshalaerp.AddStudent.NewAdmissionActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment implements View.OnClickListener {

    TextView setbarriers, newAdmission,tryFragment;


    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_landing, container, false);

        setbarriers = v.findViewById(R.id.setBarriers);
        newAdmission= v.findViewById(R.id.newAdmission);


        setbarriers.setOnClickListener(this);
        newAdmission.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();

        switch (v.getId()){
            case R.id.setBarriers:
                mainActivity.loadFragment(Constant.FRAGMENT_ADMISSIONBARRIER, null);
                break;

            case R.id.newAdmission:
                Intent in = new Intent(getActivity() , NewAdmissionActivity.class);
                startActivity(in);
                break;

        }
    }
}
