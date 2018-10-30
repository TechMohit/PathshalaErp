package in.varadhismartek.pathshalaerp.AddStudent;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import in.varadhismartek.pathshalaerp.R;


public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    Context mContext;
    ArrayList<String> arrayList;

    public CustomSpinnerAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext=mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);

        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_circle_black_24dp, 0);
        txt.setText(arrayList.get(position));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(arrayList.get(position));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}
