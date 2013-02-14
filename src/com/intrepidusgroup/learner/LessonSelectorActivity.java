package com.intrepidusgroup.learner;

import java.util.ArrayList;

import com.intrepidusgroup.learner.R;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class LessonSelectorActivity extends ListActivity {

	private ListView lessonListView;
	String[] lessonNames, lessonDescriptions;
	private ArrayAdapter lessonSelectAdapter;
	
	// THe following variables are declared to be used for the splash screen
	private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
   
    private static View splash;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d("LEARNER", "Starting the program");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_selector);

		// Wait for the given time by sending a message to the splash handler
		splash = (View) findViewById(R.id.SplashLayout);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);

		//get the list of lessons and descriptions from the strings.xml resource file
		lessonNames = getResources().getStringArray(R.array.Lessons);
		lessonDescriptions = getResources().getStringArray(R.array.LessonShortDescriptions);
		
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		
		// Log.d("IG_LEARNER", "For lesson 1 the resource id is " + this.getResources().getIdentifier("@drawable/ic_lesson1", null, null));
		// fill an array of Lessons for the view
		for (int i = 0; i < lessonNames.length; i++) {
			Log.d("LEARNER", "Inputting lesson: " + lessonNames[i] + ", Lesson description: " + lessonDescriptions[i]);
			// TODO: Update with the actual icon id
			// needed for retrieval of Lesson icons
			int realLifeLessonNumber = i + 1; 
			// Log.d("IG_LEARNER", "For lesson " + realLifeLessonNumber + " the resource id is " + getImagesIdentifier(realLifeLessonNumber));
			lessons.add((Lesson)new Lesson(lessonNames[i], lessonDescriptions[i], getImagesIdentifier(realLifeLessonNumber)));
		}
	
		// create a listview
		lessonListView = getListView();

		
		
		
		// create an adapter from the data source (based on LessonsArray)
		Lesson[] lessonsArray = new Lesson[lessons.size()];
		lessonsArray = lessons.toArray(lessonsArray);
		
		MyLessonAdapter lessonSelectAdapter = new MyLessonAdapter(this, lessonsArray);
		
		Log.d("LEARNER", "Currently items in list adapter: " + lessonSelectAdapter.getCount());
		
		// link the listview to use the new adapter
		if (lessonListView == null) {
			Log.d("LEARNER","View is null :-(");

		}
		lessonListView.setAdapter(lessonSelectAdapter);
		Log.d("LEARNER", "How about the view? " + lessonListView.getCount());
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Lesson selectedLesson = (Lesson)l.getAdapter().getItem(position);
		String selectedActivityName =  selectedLesson.name + "Activity";
        Log.d("LEARNER", "Clicked Position: " + position + ", Lesson selected: " + selectedLesson.name);
        // Remove the space from Lesson title to match normal Activity naming convention
        startLessonActivity(selectedActivityName.replace(" ",""));
    }
	
	public void startLessonActivity(String selectedActivity) {
		// code to start an activity corresponding to the clicked position
		
		 Intent intent = new Intent();
	     intent.setClassName("com.intrepidusgroup.learner", "com.intrepidusgroup.learner." + selectedActivity);

	    if(this.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            this.startActivity(intent);
            // Make the lesson slide in from the right
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            Log.d("LEARNER", "No such activity: " + selectedActivity);
        }
	}
	
	// I can't believe I'm using reflection to get the icon identifiers.
	private int getImagesIdentifier(int lessonNumber) {
		return getResources().getIdentifier("ic_lesson" + lessonNumber, "drawable", "com.intrepidusgroup.learner");	    
	}

	private static Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                case STOPSPLASH:
                        //remove SplashScreen from view
                        splash.setVisibility(View.GONE);
                        break;
                }
                super.handleMessage(msg);
        }
	};

	

}
