package com.temoteam.mckeeper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*

        if (null != savedInstanceState) {
            k = savedInstanceState.getInt(STATE_CURRENT_FRAGMENT, 0);
            onNavigationItemSelected(k);
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelected());
        getLayoutInflater().inflate(R.id.bottom_navigation, bottomNavigationView);

        if (rate == 0) {

            onNavigationItemSelected(3);
        } else {

            onNavigationItemSelected(1);
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
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
                */

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        android.app.FragmentTransaction fTrans;
/*
        if (id == R.id.action_info) {

            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, new InfoFragment());
            fTrans.addToBackStack(null);
            fTrans.commit();
        }

        if (id == R.id.action_news) {

            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, new NewsFragment());
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
        if (id == R.id.action_rasp) {

            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, new RaspFragment());
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*
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
*/
    }


    public void onNavigationItemSelected(int item) {

/*
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
*/

    }



}
