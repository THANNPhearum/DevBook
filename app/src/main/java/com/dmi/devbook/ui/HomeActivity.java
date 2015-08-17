package com.dmi.devbook.ui;

import android.os.Bundle;

import com.dmi.devbook.R;

public class HomeActivity extends AbstractMenuActivity {

    private static final String TEST_STRING_KEY = "TEST_STRING_KEY";
    private String myTestString = "initial_value";


    public String getMyTestString() {
        return myTestString;
    }

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Uncomment these lines to start using bugsense
//        if(!BuildConfig.DEBUG) {
//            BugSenseHandler.initAndStartSession(HomeActivity.this, getResources().getString(R.string.bugsense_api_key));
//        }
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            myTestString = savedInstanceState.getString(TEST_STRING_KEY, "default");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEST_STRING_KEY, "saving_state");
    }
}
