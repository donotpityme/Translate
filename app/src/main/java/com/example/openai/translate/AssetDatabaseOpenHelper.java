package com.example.openai.translate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AssetDatabaseOpenHelper {
    private static final String DB_NAME = "sql.db";
    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase StoreDatabase() {
        String path = "/data/data/com.example.openai.translate/databases";
        File pathDb = new File(path);
        pathDb.setWritable(true);
        pathDb.setReadable(true);
        try {
            if (!pathDb.exists()){
                pathDb.mkdir();
                pathDb.setWritable(true);
            }
            if (!new File(path + "/" + DB_NAME).exists()) {
                copy(path);
            }
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        Log.i("DB",context.getDatabasePath(DB_NAME).getPath());
        return SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copy(String path) throws IOException {

        // mở file trong thư mục assets
        InputStream is = context.getAssets().open(DB_NAME);
        FileOutputStream fos = new FileOutputStream(path + "/" + DB_NAME);
        byte buffer[] = new byte[1024];
        int length;

        // đọc và ghi vào thư mục databases
        while ((length = is.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        is.close();
        fos.flush();
        fos.close();
    }

}
