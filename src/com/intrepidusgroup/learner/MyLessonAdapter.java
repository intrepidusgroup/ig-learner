package com.intrepidusgroup.learner;

import android.content.Context;

public class MyLessonAdapter extends TwoLineArrayAdapter<Lesson> {
    public MyLessonAdapter(Context context, Lesson[] lessons) {
        super(context, lessons);
    }

    @Override
    public String lineOneText(Lesson e) {
        return e.name;
    }

    @Override
    public String lineTwoText(Lesson e) {
        return e.shortDescription;
    }
    
    @Override
    public int getIconId(Lesson e) {
        return e.iconResourceId;
    }
}