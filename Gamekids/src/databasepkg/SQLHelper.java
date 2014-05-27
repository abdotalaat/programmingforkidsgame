package databasepkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

	static final String dbName = "myDB";
	static final String levels = "LevelsScore";
    static final String levelID = "levelNum";
    static final String subLevelID = "subLevelNum";
    static final String levelScore = "levelScore";
	public SQLHelper(Context context){
		 super(context, dbName, null, 1);
		 SQLiteDatabase database=getWritableDatabase();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+levels+" (" +
                 levelID + " INTEGER NOT NULL, " +
                 subLevelID + " INTEGER NOT NULL, " +
                 levelScore + " TEXT," +
                 "PRIMARY KEY("+levelID+","+subLevelID+")"+
                 ")");
		 System.out.println("DATABASE CREATED");

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS "+levels);
         onCreate(db);
		
	}
	
	 public void saveScore(int levelID, int subLevelID, String score)
     {
//THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO WRITE A VALUE TO THE DATABASE 
		 System.out.println("SSSSSSSSSS"+score);
             SQLiteDatabase myDB = this.getWritableDatabase();
             ContentValues cv = new ContentValues();
             cv.put(SQLHelper.levelID, levelID);
             cv.put(SQLHelper.subLevelID,subLevelID);
             cv.put(SQLHelper.levelScore,score);
              myDB.insert(levels,SQLHelper.levelID, cv);
              myDB.close();
     }

	 public  String getScore(int levelID,int subLevelID) {
		// THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO READ A VALUE FROM THE DATABASE                 
             SQLiteDatabase myDB = this.getReadableDatabase();
             String[] lvlsID = new String[2];
             String myScore="";
             lvlsID[0]=String.valueOf(levelID);
             lvlsID[1]=String.valueOf(subLevelID);
             
             Cursor myCursor = myDB.rawQuery("SELECT "+ levelScore +" FROM "+ levels +" WHERE "+ SQLHelper.levelID +"=? AND "+SQLHelper.subLevelID+"=?",lvlsID);
             if(myCursor.getCount()==0)
            	 myScore="empty";
             else{
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(SQLHelper.levelScore);
             System.out.println("inddexx== "+index);
             myScore = myCursor.getString(index);
             System.out.println("SCOREE= "+myScore);
             }
            	 
             myCursor.close();
             myDB.close();
             return myScore;
		         }
	 
	 public int updateScore(int levelID, int subLevelID,String score)
     {
//THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO WRITE A VALUE TO THE DATABASE  
		 System.out.println("IN UPDATEEE");
             SQLiteDatabase myDB = this.getWritableDatabase();
             ContentValues cv = new ContentValues();
             cv.put(SQLHelper.levelScore,score);
             int numRowsAffected = myDB.update(levels, cv, SQLHelper.levelID+"=? AND "+SQLHelper.subLevelID+"=?", new String []{String.valueOf(levelID),String.valueOf(subLevelID)});
             myDB.close();
             return numRowsAffected;
     }
           
		         
}
