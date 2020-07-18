package Data;

/**
 * Created by Sai sreenivas on 2/15/2017.
 */

public class Constants {

    final static int DATABASE_VERSION = 2;
    final static String DATABASE_NAME = "colorsdb";
    final static String TABLE_NAME = "color_data";
    final static String COLUMN_ID = "color_id";
    final static String COLUMN_COLOR_HEXA = "color_hexa";

    final static String CREATE_COLORS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY , " + COLUMN_COLOR_HEXA + " TEXT " + ")";
}
