package com.example.tourplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class CustomUserAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final ArrayList<User> values;


    public CustomUserAdapter(@NonNull Context context, @NonNull ArrayList<User> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_user_row, parent, false);

        TextView firstName = rowView.findViewById(R.id.lvfirstName);
        TextView lastName = rowView.findViewById(R.id.lvlastName);
        TextView email = rowView.findViewById(R.id.lvemail);
        TextView phone = rowView.findViewById(R.id.lvphone);

        firstName.setText(values.get(position).firstName);
        lastName.setText(values.get(position).lastName);
        email.setText(values.get(position).email);
        phone.setText(values.get(position).phone);


        return rowView;
    }
}