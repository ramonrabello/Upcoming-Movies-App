package com.arctouch.codechallenge.details

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.matcher.IntentMatchers.hasType
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.arctouch.codechallenge.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumentation tests for [MovieDetailsActivity] class.
 */
@RunWith(AndroidJUnit4::class)
class MovieDetailsActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MovieDetailsActivity>(MovieDetailsActivity::class.java, true, false)
    lateinit var context: Context

    @Before
    fun beforeTest() {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun whenActivityOpened_withMovieId_shouldCheckIfAllViewsAreVisible() {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, 353081)
        activityTestRule.launchActivity(intent)
        Thread.sleep(5000)
        onView(withId(R.id.movie_poster_image)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_backdrop_image)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_name)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_backdrop_image)).check(matches(isDisplayed()))
    }

    @Test
    fun whenActivityOpened_clickOnFab_shouldCheckIfHasIntentExtras() {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, 353081)
        activityTestRule.launchActivity(intent)
        Thread.sleep(5000)
        onView(withId(R.id.fab)).perform(click())
    }
}