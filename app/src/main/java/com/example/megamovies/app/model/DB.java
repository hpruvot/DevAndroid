package com.example.megamovies.app.model;

public class DB {

	public DB() {}
	
	public static final class Film {
		public static final String TABLE_NAME = "movies";
		
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String YEAR = "year";
        public static final String GENDER = "gender";
        public static final String REALISATOR = "realisator";
		public static final String ACTORS = "actors";
		public static final String AVAILABLE = "available";
		public static final String VIEWED = "viewed";
		public static final String IMAGE = "image";
		
		public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + ID + 
				" INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + YEAR + " INTEGER, " +
                GENDER + " TEXT, " + REALISATOR + " TEXT, " + ACTORS + " TEXT, " + AVAILABLE +
				" INTEGER, " + VIEWED + " INTEGER, " + IMAGE + " INTEGER);";
		
		public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}
}
