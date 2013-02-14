package com.intrepidusgroup.learner;

// no need for getters and setters here - I just don't care, this is for the display and is only updated once.
public class Lesson {
	public String name;
	public String shortDescription;
	public int iconResourceId;
	public Lesson (String n, String s, int id) {
		name = n;
		shortDescription = s;
		iconResourceId = id;
	}
}
