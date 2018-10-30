package in.varadhismartek.pathshalaerp.AddStudent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import in.varadhismartek.pathshalaerp.R;


public class AutoCompleteViewAdapter extends ArrayAdapter<NewStudentRegisterModel> {

    private ListAdapterListener mListener;
    public interface ListAdapterListener { // create an interface
        void onClickAutoComplete(String regiistrationNumber); // create callback function
    }



    AutoCompleteTextView autoCompleteTextView;
    private ArrayList<NewStudentRegisterModel> studentdetailsModelArrayList, tempStudent, studentSuggestions;
    Context mContext;

    public AutoCompleteViewAdapter(Context context, ArrayList<NewStudentRegisterModel> objects, AutoCompleteTextView autoCompleteTextView, ListAdapterListener mListener) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.studentdetailsModelArrayList = objects;
        this.tempStudent = new ArrayList<NewStudentRegisterModel>(objects);
        this.studentSuggestions = new ArrayList<NewStudentRegisterModel>(objects);
        this.autoCompleteTextView = autoCompleteTextView;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NewStudentRegisterModel studentdetailsModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_row, parent, false);
        }

        RelativeLayout relativeLayout = convertView.findViewById(R.id.relativeLayoutstudentList);
        TextView studentRegistration = convertView.findViewById(R.id.registrationNumber);

        assert studentdetailsModel != null;
        studentRegistration.setText(studentdetailsModel.getStartRegistrationNumber());

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mListener.onClickAutoComplete(autoCompleteTextView.getText().toString());

            }
        });

        return convertView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            NewStudentRegisterModel customer = (NewStudentRegisterModel) resultValue;
            return customer.getStartRegistrationNumber();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                studentSuggestions.clear();
                for (NewStudentRegisterModel student : tempStudent) {
                    if (student.getStartRegistrationNumber().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        studentSuggestions.add(student);
                    }

                   /* else if(student.getStudentName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        studentSuggestions.add(student);
                    }

                    else if(student.getStudentClass().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        studentSuggestions.add(student);
                    }*/

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = studentSuggestions;
                filterResults.count = studentSuggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<NewStudentRegisterModel> c = (ArrayList<NewStudentRegisterModel>) results.values;
            if (results.count > 0) {
                clear();
                for (NewStudentRegisterModel cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };


}