package com.dmi.devbook;

import android.test.ActivityInstrumentationTestCase2;

import com.dmi.devbook.ui.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {


    public HomeActivityTest() { super(HomeActivity.class); }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }


    public void testGson(){

    }
//    public void testTextViewContainsLoremIpsum() {
//        onView(withId(R.id.view_lorem))
//                .check(matches(withText(containsString("Lorem ipsum"))));
//    }

}
