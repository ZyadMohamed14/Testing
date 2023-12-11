package com.example.testing.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@Suppress("DEPRECATION")
class MainCorotuineRule (
    private val dispatcer:CoroutineDispatcher=TestCoroutineDispatcher()
):TestWatcher(),TestCoroutineScope by TestCoroutineScope(dispatcer) {
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcer)
    }

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}