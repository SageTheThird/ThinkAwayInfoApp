package com.homie.psychq.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.homie.psychq.R;


import java.util.List;


/*Custom Spinner For Automated Input Numbers */

public class SpinnerCustomAdapter extends ArrayAdapter<Integer> {



    public SpinnerCustomAdapter(@NonNull Context context, List<Integer> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fpool_spinner_item, parent, false
            );
        }

        TextView num_tv = convertView.findViewById(R.id.spinner_tv);
        String num = String.valueOf(getItem(position));
        num_tv.setText(num);


        return convertView;
    }
}
