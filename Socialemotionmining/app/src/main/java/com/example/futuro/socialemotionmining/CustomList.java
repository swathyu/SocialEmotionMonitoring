package com.example.futuro.socialemotionmining;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by futuro on 07-02-2018.
 */

public class CustomList extends ArrayAdapter<Appmodel> {

    int resource;
    String response;
    Context context;

    public CustomList(Context context, int resource, List<Appmodel> items) {
        super(context, resource, items);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout contactsView;
        Appmodel contact = getItem(position);
        if(convertView==null) {
            contactsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, contactsView, true);
        } else {
            contactsView = (LinearLayout) convertView;
        }
        TextView contactName =(TextView)contactsView.findViewById(R.id.txt);
        contactName.setTypeface(null, Typeface.BOLD);
        contactName.setTextSize(TypedValue.COMPLEX_UNIT_PX,18);
        TextView contactPhone = (TextView)contactsView.findViewById(R.id.tx1);
        contactPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX,14);
        contactName.setText(contact.getAppname());
        contactPhone.setText(contact.getCat());
        return contactsView;
    }
}