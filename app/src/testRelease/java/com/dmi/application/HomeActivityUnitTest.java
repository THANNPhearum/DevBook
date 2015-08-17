package com.dmi.devbook;

import com.dmi.devbook.ui.HomeActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(CustomRobolectricRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
// TODO after upgrade to v3.0-rc3+, try this instead:
//@Config(constants = BuildConfig.class, emulateSdk = 18, packageName="com.dmi.devbook.debug")
// TODO for now, we have to use a -Release build variant to test with, or comment out the line applicationIdSuffix '.debug' in the build.gradl
public class HomeActivityUnitTest {

    private HomeActivity mActivity;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(HomeActivity.class).create().get();


    }

    @Test
    public void myActivityAppearsAsExpectedInitially() {
        String expectedTitle = "Template Application";
        assertEquals(mActivity.getTitle(), expectedTitle);
        // do the same test with assertJ, it's easier to read the code and it has better error messages
        assertThat(mActivity).hasTitle(expectedTitle);
    }

    @Test
    public void testSavingInstanceState() {
        HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().start().resume().visible().get();

        assertEquals("initial_value", activity.getMyTestString());

        // Destroy and re-create activity
        activity.recreate();

        assertThat(activity).isNotNull();

        assertEquals("saving_state", activity.getMyTestString());

    }


//    @Test
//    public void myTextViewContainsLoremIpsum() {
//        // Need to make the view VISIBLE before we can test things on it.
//        HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().start().resume().visible().get();
//        // This test fails. However it works in Espresso. View probably to complex for Robolectric to mock?
//        TextView layout = (TextView) activity.findViewById(R.id.view_lorem);
//
//        assertThat(layout).hasText("Lorem ipsum");
//    }

}