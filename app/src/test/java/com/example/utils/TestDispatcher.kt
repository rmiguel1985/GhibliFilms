
package com.example.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@OptIn(ExperimentalCoroutinesApi::class)
open class TestDispatcher {

    @BeforeEach
    open fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    open fun afterEach() {
        Dispatchers.resetMain()
    }
}
