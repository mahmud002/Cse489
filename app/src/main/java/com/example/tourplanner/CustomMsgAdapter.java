package com.example.tourplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class CustomMsgAdapter extends ArrayAdapter<Msg> {

    private final Context context;
    private final ArrayList<Msg> values;


    public CustomMsgAdapter(@NonNull Context context, @NonNull ArrayList<Msg> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_msg_row, parent, false);

        TextView email = rowView.findViewById(R.id.lvmEmail);
        TextView msg = rowView.findViewById(R.id.msg);



        email.setText(values.get(position).email);
        msg.setText(values.get(position).msg);



        return rowView;
    }
}