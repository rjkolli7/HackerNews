package com.example.hackernews

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BuildConfigTest {

    @Test
    fun testBuildConfig() {
        assertEquals("com.example.hackernews", BuildConfig.APPLICATION_ID)
        assertEquals("debug", BuildConfig.BUILD_TYPE)
        assertEquals(true, BuildConfig.DEBUG)
        assertEquals(1, BuildConfig.VERSION_CODE)
        assertEquals("1.0", BuildConfig.VERSION_NAME)
        assertTrue(BuildConfig.FLAVOR.isEmpty())
    }
}
