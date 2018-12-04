package com.example.openai.translate;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DbHelpter {
    Context context;
    SQLiteDatabase db;
    public DbHelpter(Context context){
        this.context= context;
        AssetDatabaseOpenHelper assetDB =  new AssetDatabaseOpenHelper(context);
        db =  assetDB.StoreDatabase();
    }


    private ArrayList<String> get(String sql,String...selectionArgs){
        ArrayList<String> contents = new ArrayList<String>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while(c.moveToNext()){
            contents.add(c.getString(0));
        }
        c.close();
        return contents;
    }
    public String getmean(String word){
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM anhviet WHERE word = '"+word+"'",null);
        if (cursor != null)
            cursor.moveToFirst();
        String content = cursor.getString(2);
        cursor.close();
        db.close();
        return content;
    }
    public ArrayList<String> getword()
    {
        String sql = "select word from anhviet";
        return get(sql);
    }
    public void addhis(String word)
    {
        db.execSQL("update anhviet set his = ((select max(his) from anhviet)+1) where word = '"+word+"'");
        db.close();
    }
    public void addfav(String word)
    {
        db.execSQL("update anhviet set fav = 1 where word = '"+word+"'");
        db.close();
    }
    public void delallhis()
    {
        db.execSQL("update anhviet set his = 0");
        db.close();
    }
    public void delfav(String word)
    {
        db.execSQL("update anhviet set fav = 0 where word = '"+word+"'");
        db.close();
    }
    public ArrayList<String> gethis() {
        String sql = "select word from anhviet where his > 0 order by his desc";
        return get(sql);
    }
    public ArrayList<String> gettoeic() {
        String sql = "select word from anhviet where toeic > 0";
        return get(sql);
    }
    public ArrayList<String> getielts() {
        String sql = "select word from anhviet where ielts > 0";
        return get(sql);
    }
    public ArrayList<String> gettoefl() {
        String sql = "select word from anhviet where toefl > 0";
        return get(sql);
    }
    public ArrayList<String> getfav() {
        String sql = "select word from anhviet where fav > 0";
        return get(sql);
    }

}
