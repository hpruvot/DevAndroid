package com.example.megamovies.app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class Provider extends ContentProvider {
	

	private static final String authority = "com.example.megamovies.provider";

	protected static final String uriMovies = "content://"+authority+"/movies";
    protected static final String uriMovieID = "content://"+authority+"/movie";
	private static final String uriMovieAdd = "content://"+authority+"/movie/add";
	protected static final String uriSearch = "content://"+authority+"/search";

	public static final Uri CONTENT_URI_MOVIES = Uri.parse(uriMovies);
	public static final Uri CONTENT_URI_MOVIE_ADD = Uri.parse(uriMovieAdd);
	public static final Uri CONTENT_URI_SEARCH = Uri.parse(uriSearch);
	
	public static final int MOVIES = 0;
	public static final int MOVIE_ID = 1;
	public static final int MOVIE_ADD = 2;
	public static final int SEARCH = 3;

    private DatabaseHelper DH;
	private SQLiteDatabase mDb;
	
	public static UriMatcher uriMatcher;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(authority, "movies", MOVIES);
		uriMatcher.addURI(authority, "movie/#", MOVIE_ID);
		uriMatcher.addURI(authority, "movie/add", MOVIE_ADD);
		uriMatcher.addURI(authority, "search/", SEARCH);
	}
	
	public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
/*		Log.i("Provider", "delete()");
		int ret = 0;
		int id = 0;
		switch (uriMatcher.match(uri)) {
			case CONTACT_ID:
				Log.i("Provider", "CONTACT_ID");
				try {
					id = Integer.parseInt(uri.getPathSegments().get(1));
					ret = (int) mDb.delete(DB.Contact.TABLE_NAME, "id = " + id, null);
					getContext().getContentResolver().notifyChange(CONTENT_URI_CONTACT, null);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case CONTACT_ADRESSE:
				Log.i("Provider", "CONTACT_ADRESSE");
				try {
					id = Integer.parseInt(uri.getPathSegments().get(1));
					ret = (int) mDb.delete(DB.Adresse.TABLE_NAME, "id = " + id, null);
					getContext().getContentResolver().notifyChange(CONTENT_URI_ADRESSE, null);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case CONTACT_MAIL:
				Log.i("Provider", "CONTACT_MAIL");
				try {
					id = Integer.parseInt(uri.getPathSegments().get(1));
					ret = (int) mDb.delete(DB.Mail.TABLE_NAME, "id = " + id, null);
					getContext().getContentResolver().notifyChange(CONTENT_URI_MAIL, null);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case CONTACT_NUMERO:
				Log.i("Provider", "CONTACT_NUMERO");
				try {
					id = Integer.parseInt(uri.getPathSegments().get(1));
					ret = (int) mDb.delete(DB.Numero.TABLE_NAME, "id = " + id, null);
					getContext().getContentResolver().notifyChange(CONTENT_URI_NUMERO, null);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				throw new IllegalArgumentException("URI non support�e : "+uri);
		}
		return ret;
	}
*/
	public Uri insert(Uri uri, ContentValues cont) {
		Log.i("Provider", "insert()");
		long id = 0;
		switch (uriMatcher.match(uri)) {
			case MOVIE_ADD:
				Log.i("Provider", "CONTACT:");
				id = mDb.insert(DB.Film.TABLE_NAME, null, cont);
				if (id>0) {
					Uri geturi = ContentUris.withAppendedId(CONTENT_URI_MOVIES, id);
					getContext().getContentResolver().notifyChange(uri,  null);
					Log.i("Provider", "Movie added : "+id);
					return geturi;
				}
				return null;
			default:
				throw new IllegalArgumentException("URI non supportee : "+uri);
		}
	}

	public boolean onCreate() {
		Log.i("Provider", "onCreate()");
        Context context = getContext();
		DH = new DatabaseHelper(context);
		mDb = DH.getWritableDatabase();
        return (DH == null) ? false : true;
	}

	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.i("Provider", "query()");
		Cursor ret = null;
		int id = 0;
		switch (uriMatcher.match(uri)) {
			case MOVIE_ID:
				Log.i("Provider", "case MOVIE_ID:");
				// Recupere l'index du contact.
				try { 
					id = Integer.parseInt(uri.getPathSegments().get(1));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					return null;
				}
				// Recupere le curseur contenant le contact.
				ret = mDb.rawQuery("SELECT * FROM " + DB.Film.TABLE_NAME + " WHERE " + DB.Film.ID +
						" = ?", new String[] {String.valueOf(id)});
				// Enregistre le ContentResolver de facon a ce qu'il soit notifie si
				// le curseur change.
				ret.setNotificationUri(getContext().getContentResolver(), uri);
				return ret;
            case MOVIES:
                Log.i("Provider", "case MOVIES:");
                // Recupere le curseur contenant l'ensemble des entete de film.
                ret = mDb.rawQuery("SELECT " + DB.Film.ID + " as _id," + DB.Film.TITLE + ", "  +
                        DB.Film.YEAR + ", " + DB.Film.REALISATOR + ", " + DB.Film.AVAILABLE+ ", " +
                        DB.Film.VIEWED + ", " + DB.Film.IMAGE + ", " + DB.Film.GENDER + " FROM " +
                        DB.Film.TABLE_NAME + " ORDER BY " + DB.Film.TITLE + ";", null);

                // Enregistre le ContentResolver de facon a ce qu'il soit notifie si
                // le curseur change.
                ret.setNotificationUri(getContext().getContentResolver(), uri);
                return ret;
            case SEARCH:
                Log.i("Provider", "case SEARCH:");
                // Recupere le curseur contenant l'ensemble des entete de film.
                ret = mDb.rawQuery("SELECT " + DB.Film.ID + " as _id," + DB.Film.TITLE + ", "  +
                        DB.Film.YEAR + ", " + DB.Film.REALISATOR + ", " + DB.Film.AVAILABLE+ ", " +
                        DB.Film.VIEWED + ", " + DB.Film.IMAGE + ", " + DB.Film.GENDER + " FROM " +
                        DB.Film.TABLE_NAME + " WHERE  (upper(" +
                        DB.Film.TITLE + ") LIKE '%" + selection.toUpperCase() + "%') ORDER BY " + DB.Film.TITLE + ";", null);

                // Enregistre le ContentResolver de facon a ce qu'il soit notifie si
                // le curseur change.
                ret.setNotificationUri(getContext().getContentResolver(), uri);
                return ret;
			default:
				throw new IllegalArgumentException("URI non supportee : "+uri);
		}
	}

	public int update(Uri uri, ContentValues cont, String condition, String[] conditionArgs) {
        return 0;
    }
/*		Log.i("Provider", "update()");
		int ret = 0;
		String id = "";
		switch (uriMatcher.match(uri)) {
		case CONTACT_ID:
			id = uri.getPathSegments().get(1);
			ret = mDb.update(DB.Contact.TABLE_NAME, cont, "id" + " = " + id, null);
			getContext().getContentResolver().notifyChange(CONTENT_URI_CONTACT, null);
			break;
		case CONTACT_ADRESSE:
			id = uri.getPathSegments().get(1);
			ret = mDb.update(DB.Adresse.TABLE_NAME, cont, "id" + " = " + id, null);
			getContext().getContentResolver().notifyChange(CONTENT_URI_ADRESSE, null);
			break;
		case CONTACT_MAIL:
			id = uri.getPathSegments().get(1);
			ret = mDb.update(DB.Mail.TABLE_NAME, cont, "id" + " = " + id, null);
			getContext().getContentResolver().notifyChange(CONTENT_URI_MAIL, null);
			break;
		case CONTACT_NUMERO:
			id = uri.getPathSegments().get(1);
			ret = mDb.update(DB.Numero.TABLE_NAME, cont, "id" + " = " + id, null);
			getContext().getContentResolver().notifyChange(CONTENT_URI_NUMERO, null);
			break;
		default:
			throw new IllegalArgumentException("URI non support�e : "+uri);
		}
		return ret;
	}*/
	
	public String getType(Uri uri) {
		Log.i("Provider", "getType()");
		switch (uriMatcher.match(uri)) {
			case MOVIES:
				return "vnd.android.cursor.dir/movies";
			case MOVIE_ID:
				return "vnd.android.cursor.item/movie";
			default:
				throw new IllegalArgumentException("Unsupportes URI : " + uri);
		}
	}
	
	public boolean exist(Uri uri) {
		boolean ret = false;
		Cursor c = query(uri, null, null, null, null);
		if (c.getCount() > 0)
			ret = true;
		c.close();
		return ret;
	}
}
