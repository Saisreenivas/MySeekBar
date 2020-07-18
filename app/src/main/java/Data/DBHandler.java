package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Model.ColorModel;

import static Data.Constants.*;

/**
 * Created by Sai sreenivas on 2/15/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final ArrayList<ColorModel> allColors = new ArrayList<>();

    public DBHandler(Context context) {
        super(context, DATABASE_NAME,  null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLORS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addColor(String mColor){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(COLUMN_COLOR_HEXA, mColor);
        db.insert(TABLE_NAME, null, value);
        db.close();
    }

    public ArrayList<ColorModel> showAllColors(){
        allColors.clear();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_COLOR_HEXA}, null, null,
                null, null, COLUMN_ID + " DESC");

        if(cursor.moveToFirst()){
            do {
                ColorModel colorModel = new ColorModel();
                colorModel.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                colorModel.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR_HEXA)));
                allColors.add(colorModel);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return allColors;
    }
}
