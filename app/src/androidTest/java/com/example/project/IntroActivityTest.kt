package com.example.project

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
 class IntroActivityTest{
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<IntroActivity>(
        IntroActivity::class.java
    )
 }