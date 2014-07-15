package com.databaseManagement;

import java.util.ArrayList;
import java.util.List;

import com.model.CollectionEntry;
import com.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	//Database Version
	private static final int DATABASE_VERSION = 1;
	//Database Name
	private static final String DATABASE_NAME = "favouriteCollection";
	//CollectionsTable table name
	private static final String TABLE_COLLECTIONS = "collections";
	//Collections Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_IDENTIFIER = "identifier";
	
	public DatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	//TABLE CREATING
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		String CREATE_COLLECTIONS_TABLE = "CREATE TABLE " 
				+ TABLE_COLLECTIONS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_TITLE + " TEXT,"
				+ KEY_IDENTIFIER + " TEXT" + ")";
		db.execSQL(CREATE_COLLECTIONS_TABLE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS "
					+ TABLE_COLLECTIONS);
		// Create tables again
		onCreate(db);
	}
	/**
	 * thiis fonction will add a collection in the Database
	 * @param collection
	 */
	public void addCollection(CollectionEntry collection) 
	{
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, collection.getTitle()); 
		values.put(KEY_IDENTIFIER,Utils.ParseCollectionIdentifier(collection.getIdentifier())); 
		db.insert(TABLE_COLLECTIONS, null, values);
		db.close(); 
	}

	//CHECK IF COLLECTION IS ALREADY ADDED
	public boolean containsCollection(CollectionEntry collection)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery ="SELECT  * FROM " 
							+ TABLE_COLLECTIONS 
							+" WHERE "
							+KEY_IDENTIFIER	
							+" =?";
		Cursor cursor = db.rawQuery(selectQuery, new String[]{collection.getIdentifier()});
		if (!cursor.moveToFirst()) //no record exist
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	// GETTING A COLLECTION
	public CollectionEntry getCollection(String identifier) 
	{
		SQLiteDatabase db = this.getReadableDatabase();
		CollectionEntry collection=null;
		Cursor cursor = db.query(TABLE_COLLECTIONS, new String[] { KEY_ID,KEY_TITLE, KEY_IDENTIFIER }, KEY_IDENTIFIER + "=?",new String[] { identifier.toString() }, null, null, null, null);
		if (cursor != null)
		{
			cursor.moveToFirst();
			collection = new CollectionEntry(cursor.getString(2));
		}
		return collection;
	}
	// GETTING ALL COLLECTIONS
	public List<CollectionEntry> getAllCollections() 
	{
		List<CollectionEntry> collections = new ArrayList<CollectionEntry>();
		String selectQuery = "SELECT  * FROM " 
							 + TABLE_COLLECTIONS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				CollectionEntry collection = new CollectionEntry();
				collection.setID(Integer.parseInt(cursor.getString(0)));
				collection.setTitle(cursor.getString(1));
				collection.setIdentifier(cursor.getString(2));
				// Adding collection  to list
				collections.add(collection);
			} while (cursor.moveToNext());
		}
		// return  list
		return collections;
	}
	// UPDATING SINGLE COLLECTION
	public int updateCollection(CollectionEntry collection) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, collection.getTitle());
		values.put(KEY_IDENTIFIER, collection.getIdentifier());
		
		return db.update(TABLE_COLLECTIONS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(collection.getID()) });
	}
	// DELETE SINGLE COLLECTION FROM THE DATABASE
	public void deleteCollection(CollectionEntry collection) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COLLECTIONS, KEY_ID + " = ?",
				new String[] { String.valueOf(collection.getID()) });
		db.close();
	}
	// RETURN THE NUMBER OF COLLECTION
	public int getCollectionCount() 
	{
		String countQuery = "SELECT * FROM " + TABLE_COLLECTIONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}
}
