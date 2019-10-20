package com.pro.deepak.com.update.mRecycler;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.pro.deepak.com.update.R;
import com.pro.deepak.com.update.mRecycler.fieldsData;

import java.util.List;

public class fieldsAdapter extends ArrayAdapter<fieldsData>
{

    public fieldsAdapter(Context context, int resource, List<fieldsData> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.model_event, parent, false);
        }

        TextView SubjectTV = convertView.findViewById(R.id.subjectTV);
        TextView deadLineTV =  convertView.findViewById(R.id.deadlineTV);
        TextView descriptionTV = convertView.findViewById(R.id.descriptionTV);

        fieldsData message = getItem(position);

        deadLineTV.setVisibility(View.VISIBLE);

        SubjectTV.setText(message.getSubject());
        deadLineTV.setText(message.getDeadline());
        descriptionTV.setText(message.getDesc());

        return convertView;
    }
}

