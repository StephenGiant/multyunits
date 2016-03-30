package multyunits.wochu.com.multyunits.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SortingSqlHelper extends SQLiteOpenHelper {
    public static final String SortingData = "sortrecord";

    //	 public static final String FBOXDATA = "fboxdata";
    public SortingSqlHelper(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("create table " + SortingData + "(_id integer primary key autoincrement," +
                "boxno varchar(20),orderID varchar(20),fboxno varchar(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
