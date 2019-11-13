package com.example.casper.itime;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE");

    @Test
    public void mainActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_frame_layout),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.title_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.title_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.remark_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.remark_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("bb"), closeSoftKeyboard());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.date_layout),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.repeat_layout),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        linearLayout2.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(3);
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.repeat_day_customize_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.repeat_day_customize_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.repeat_day_customize_confirm_button), withText("确定"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // 无法选择图片
//        ViewInteraction linearLayout3 = onView(
//                allOf(withId(R.id.background_image_layout),
//                        childAtPosition(
//                                allOf(withId(R.id.linearLayout3),
//                                        childAtPosition(
//                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
//                                                3)),
//                                2),
//                        isDisplayed()));
//        linearLayout3.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.edit_time_confirm), withContentDescription("confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_time_tool_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        DataInteraction linearLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.home_my_time_grid),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        linearLayout5.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.edit_time), withContentDescription("edit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.time_detail_tool_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.title_edit_text), withText("aa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.title_edit_text), withText("aa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("aacc"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.title_edit_text), withText("aacc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.head_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(closeSoftKeyboard());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.edit_time_confirm), withContentDescription("confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_time_tool_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.time_detail_tool_bar),
                                        childAtPosition(
                                                withId(R.id.time_detail_app_bar_layout),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        DataInteraction linearLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.home_my_time_grid),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        linearLayout6.perform(click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.delete_time), withContentDescription("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.time_detail_tool_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
