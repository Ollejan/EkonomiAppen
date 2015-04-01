package se.mah.ad0025.inluppg1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Klass som ärver SQLiteOpenHelper. Används för att lagra och hämta data från en databas.
 * @author Jonas Dahlström 5/10-14
 */
public class DatabaseController extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DB_NAME = "ekonomiappen7";
    private static final int DB_VERSION = 1;

    public DatabaseController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table usertable ( id integer PRIMARY KEY autoincrement, firstname VARCHAR(50), lastname VARCHAR(50));");
        sqLiteDatabase.execSQL("CREATE table incometable ( id integer, date VARCHAR(20), title VARCHAR(100), category VARCHAR(50), amount FLOAT, FOREIGN KEY(id) REFERENCES usertable(id));");
        sqLiteDatabase.execSQL("CREATE table expensestable ( id integer, date VARCHAR(20), title VARCHAR(100), category VARCHAR(50), price FLOAT, FOREIGN KEY(id) REFERENCES usertable(id));");
    }

    public void open(){
        database = getWritableDatabase();
    }

    public void close(){
        database.close();
    }

    public void insertUser(String firstname, String lastname){
        ContentValues values = new ContentValues();
        values.put("firstname", firstname);
        values.put("lastname", lastname);

        database.insert("usertable", null, values);
    }

    public void insertIncome(int userID, String date, String title, String category, String amount) {
        ContentValues values = new ContentValues();
        values.put("id", userID);
        values.put("date", date);
        values.put("title", title);
        values.put("category", category);
        values.put("amount", amount);

        database.insert("incometable", null, values);
    }

    public void insertExpenses(int userID, String date, String title, String category, String price) {
        ContentValues values = new ContentValues();
        values.put("id", userID);
        values.put("date", date);
        values.put("title", title);
        values.put("category", category);
        values.put("price", price);

        database.insert("expensestable", null, values);
    }

    public Cursor getAllUsers(){
        return database.rawQuery("SELECT * from usertable", new String[]{});
    }

    public Cursor getAllIncome() {
        return database.rawQuery("SELECT * from incometable", new String[]{});
    }

    public Cursor getAllExpenses() {
        return database.rawQuery("SELECT * from expensestable", new String[]{});
    }

    public Cursor getSpecificIncome(String fromDate, String toDate) {
        return database.rawQuery("SELECT * from incometable WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'", new String[]{});
    }

    public Cursor getSpecificExpense(String fromDate, String toDate) {
        return database.rawQuery("SELECT * from expensestable WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'", new String[]{});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}
}
