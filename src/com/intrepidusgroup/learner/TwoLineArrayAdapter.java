package com.intrepidusgroup.learner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class TwoLineArrayAdapter<T> extends ArrayAdapter<T> {
    private int mListItemLayoutResId;
   

    public TwoLineArrayAdapter(Context context, T[] ts) {
        //this(context, android.R.layout.two_line_list_item, ts);
    	this(context, R.layout.two_line_list_with_image_item, ts);
    }

    public TwoLineArrayAdapter(
            Context context, 
            int listItemLayoutResourceId,
            T[] ts) {
        super(context, listItemLayoutResourceId, ts);
        mListItemLayoutResId = listItemLayoutResourceId;
    }

    @Override
    public android.view.View getView(
            int position, 
            View convertView,
            ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater)getContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;
        if (null == convertView) { 
            listItemView = inflater.inflate(
                mListItemLayoutResId, 
                parent, 
                false);
        }

        // The ListItemLayout must use the standard text item IDs.
        TextView lineOneView = (TextView)listItemView.findViewById(
            R.id.text1);
        
        TextView lineTwoView = (TextView)listItemView.findViewById(
           R.id.text2);

        
    	
        T t = (T)getItem(position); 
        
        
    	int iconResourceId = getIconId(t);
    	if (iconResourceId == 0) {
    		// cover default case
    		iconResourceId = 2130837507;		
    	}
    	// Log.d("IG_LEARNER", "Resource id is " + iconResourceId);
    	try {
    		ImageView icon = (ImageView)listItemView.findViewById(R.id.list_image);
    		icon.setImageResource(iconResourceId);
    		// set padding for the image
    		icon.setPadding(5, 5, 5, 5);
    		icon.setVisibility(0); // constant for visible
    		
    	}
    	catch (NullPointerException e) {
    		Log.d("IG_LEARNER", "Exception is now: " + e.getMessage());
    	}
      
    	
        lineOneView.setPadding(5, 15, 2, 2);
        lineOneView.setTextSize(20);
        lineOneView.setTextColor(Color.rgb(218, 218, 227));
        lineOneView.setText(lineOneText(t));
        
        lineTwoView.setPadding(5, 2, 2, 20);
        lineTwoView.setTextSize(14);
        lineTwoView.setTextColor(Color.LTGRAY);
 
        lineTwoView.setText(lineTwoText(t));

        return listItemView;
    }

    public abstract String lineOneText(T t);

    public abstract String lineTwoText(T t);
    
    public abstract int getIconId(T t);
}