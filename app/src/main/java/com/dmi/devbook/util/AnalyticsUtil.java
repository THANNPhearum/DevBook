package com.dmi.devbook.util;

import com.dmi.devbook.R;
import com.dmi.devbook.TemplateApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by arthur on 04/03/15.
 */
public final class AnalyticsUtil {

    private AnalyticsUtil() {
        //not called, but required by checkstyle
    }

    public static void trackScreen(final TemplateApplication app, final ScreenName screen) {
        Tracker t = app.getTracker();
        t.setScreenName(app.getString(screen.getScreenNameRef()));
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public static void trackEvent(final TemplateApplication app, final Event event, final String label) {
        Tracker t = app.getTracker();
        t.send(new HitBuilders.EventBuilder()
                .setCategory(app.getString(event.getCategoryRef()))
                .setAction(app.getString(event.getActionRef()))
                .setLabel(label)
                .build());
    }

    public static enum ScreenName {

        SPLASH(R.string.activity_splash),
        HOME(R.string.activity_home),
        LOREM(R.string.fragment_lorem);

        private final int mScreenNameRef;

        private ScreenName(final int resourceId) {
            mScreenNameRef = resourceId;
        }

        public int getScreenNameRef() {
            return mScreenNameRef;
        }
    }

    public static enum Event {

        LINK_CLICK(R.string.link_article, R.string.link_action);

        private final int mCategoryRef;
        private final int mActionRef;

        private Event(final int categoryRef, final int actionRef) {
            mCategoryRef = categoryRef;
            mActionRef = actionRef;
        }

        public int getCategoryRef() {
            return mCategoryRef;
        }

        public int getActionRef() {
            return mActionRef;
        }
    }
}
