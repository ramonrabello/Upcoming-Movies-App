package com.arctouch.codechallenge.home

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AutoCompleteTextView
import com.arctouch.codechallenge.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for [HomeActivity] class.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    @Test
    fun whenActivityOpened_shouldCheckIfAllViewsAreVisible(){
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenListLoaded_scrollToPosition_clickOnItem() {
        Thread.sleep(3000)
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(scrollToPosition<HomeAdapter.ViewHolder>(10))
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(actionOnItemAtPosition<HomeAdapter.ViewHolder>(10, click()))
    }

    @Test
    fun typeOnSearchMenuItem_typeSearchQuery() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(ViewActions.typeText("Mission Impossible"))
    }
}