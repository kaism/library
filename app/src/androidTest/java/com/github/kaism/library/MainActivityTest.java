package com.github.kaism.library;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {
	@Rule
	public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

	@Test
	public void verifyDisplay() {
		// verify app name is displayed
		onView(withText(R.string.app_name)).check(matches(isDisplayed()));

		// verify library navigation link is displayed
		onView(withId(R.id.nav_list)).check(matches(isDisplayed()));

		// verify explore navigation link is displayed
		onView(withId(R.id.nav_explore)).check(matches(isDisplayed()));

		// verify button to scan a book is displayed
		onView(withId(R.id.nav_scan)).check(matches(isDisplayed()));
	}

}
