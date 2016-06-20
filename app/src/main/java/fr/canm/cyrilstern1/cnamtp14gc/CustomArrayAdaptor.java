package fr.canm.cyrilstern1.cnamtp14gc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by cyrilstern1 on 19/06/2016.
 */

public class CustomArrayAdaptor extends ArrayAdapter<RowItem> {

    public CustomArrayAdaptor(Context context, ArrayList<RowItem> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowItem ri = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layoutrow, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewRow);
        tvName.setText(ri.getCourseRow());
        return convertView;
    }

    @Override
    public void add(RowItem object) {
        super.add(object);
    }
}
