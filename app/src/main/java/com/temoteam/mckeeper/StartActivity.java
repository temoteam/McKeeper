package com.temoteam.mckeeper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Date;

import static androidx.navigation.Navigation.findNavController;

public class StartActivity extends AppCompatActivity {


    private static final String STATE_CURRENT_FRAGMENT = "state_current_fragment";
    private int k = 0;
    private StartFragment startFragment;
    private AddShiftFragment addShiftFragment;
    private InitialFragment initialFragment;
    private String nowMonth;
    private SQLiteDatabase myDB;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


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

           // onNavigationItemSelected(1);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("TAG", token);
                        Toast.makeText(StartActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_news, R.id.navigation_timetable, R.id.navigation_info)
                .build();
        navController = findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
    }

    public void onNavigationItemSelected(int item) {


        if (item == 1) {

            navController.navigate(R.id.action_navigation_init_to_navigation_home);

        } else if (item == 2) {
            navController.navigate(R.id.action_navigation_home_to_navigation_addShift);

        } else if (item == 3) {
            navController.navigate(R.id.action_navigation_home_to_navigation_init);

        }


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
