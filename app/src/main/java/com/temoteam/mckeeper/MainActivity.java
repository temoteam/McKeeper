package com.temoteam.mckeeper;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends FragmentActivity {

    private int k = 0;
    private StartFragment startFragment;
    private AddShiftFragment addShiftFragment;
    private InitialFragment initialFragment;
    private String nowMonth;

    private SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Date date = new Date();
        nowMonth = date.toString().split(" ")[1];

        myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS shifts" + nowMonth + " (date VARCHAR(200), allTime INT, lunch INT, allMoney FLOAT, pererabotki FLOAT)"
        );
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(this);
        int rate = myPreferences.getInt("rate", 0);
        if (rate == 0) {

            onNavigationItemSelected(3);
        } else {

            onNavigationItemSelected(1);
        }

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            if (k == 0) {
                Toast toast = Toast.makeText(this,
                        "Tap again to exit", Toast.LENGTH_SHORT);
                toast.show();
                k++;
            } else {
                super.onBackPressed();
            }
        }

    }


    public void onNavigationItemSelected(int item) {
        k = 0;

        android.app.FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        startFragment = new StartFragment();
        addShiftFragment = new AddShiftFragment();
        initialFragment = new InitialFragment();
        if (item == 1) {
            ftrans.add(R.id.container, startFragment).replace(R.id.container, startFragment);

        } else if (item == 2) {
            ftrans.add(R.id.container, addShiftFragment).replace(R.id.container, addShiftFragment).addToBackStack("2");

        } else if (item == 3) {
            ftrans.replace(R.id.container, initialFragment);

        }
        ftrans.commit();


    }


    public void insertData(String date, int alltime, float allMoney, float pererabotki) {

        ContentValues row = new ContentValues();
        row.put("date", date);
        row.put("allTime", alltime);
        row.put("lunch", 0);
        row.put("allMoney", allMoney);
        row.put("pererabotki", pererabotki);
        myDB.insert("shifts" + nowMonth, null, row);

    }

    public float getAllMoney(String month, int i) {
        int money = 0;
        if (i == 11) {
            Cursor myCursor =
                    myDB.rawQuery("select date, allMoney from shifts" + month, null);
            int k = 0;
            while (myCursor.moveToNext()) {
                int z = Integer.parseInt(myCursor.getString(0).split("\\.")[0]);
                if (z > 14) {
                    Log.i("kekek", myCursor.getString(1));
                    money = myCursor.getInt(1) + money;
                }
            }
            myCursor.close();
        }
        if (i == 26) {
            Cursor myCursor =
                    myDB.rawQuery("select date, allMoney from shifts" + month, null);

            while (myCursor.moveToNext()) {
                int z = Integer.parseInt(myCursor.getString(0).split("\\.")[0]);
                if (z < 15) {
                    Log.i("kekek", myCursor.getString(1));
                    money = myCursor.getInt(1) + money;
                }
            }
            myCursor.close();
        }
        return money;
    }

    public int getAllHours(String month) {
        int hours = 0;
        Cursor myCursor =
                myDB.rawQuery("select allTime from shifts" + month, null);
        while (myCursor.moveToNext()) {
            hours = myCursor.getInt(0) + hours;
        }
        myCursor.close();

        return hours;
    }

    public float getAllPererabotki(String month) {
        float pererabotki = 0;
        Cursor myCursor =
                myDB.rawQuery("select pererabotki from shifts" + month, null);
        while (myCursor.moveToNext()) {
            pererabotki = myCursor.getInt(0) + pererabotki;
        }
        myCursor.close();

        return pererabotki;
    }


}
