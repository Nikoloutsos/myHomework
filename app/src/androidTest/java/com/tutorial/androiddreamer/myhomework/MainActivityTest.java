package com.tutorial.androiddreamer.myhomework;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.tutorial.androiddreamer.myhomework.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(JUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void addNoteTest(){
            onView(withId(R.id.fab_activity_main_add_note)).perform(click());
            String str = ((Long)System.currentTimeMillis()).toString();

            onView(withId(R.id.et_activity_add_note_subject)).perform(replaceText(str));
            onView(withId(R.id.et_activity_add_note_note)).perform(replaceText("This is a note!"));
            onView(withId(R.id.btn_menu_add_note_activity_save_note)).perform(click()); // Note is created


        onView(withId(R.id.rv_activity_main))
                .check(matches(hasDescendant(withText(str))));
        onView(withId(R.id.rv_activity_main)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,swipeLeft()));

    }



    @Test
    public void undoFunctionalityTest(){

    }





}