package com.example.testing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.testing.R
import com.example.testing.data.FakeShoppingRepository
import com.example.testing.data.getOrAwaitValueTest
import com.example.testing.launchFragmentInHiltContainer
import com.example.testing.local.ShoppingItem
import com.google.common.truth.Truth.assertThat

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingFragmentTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }
    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
        launchFragmentInHiltContainer<AddShoppingFragment>(
            fragmentFactory = fragmentFactory
        ) {
           viewModel=testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItem.getOrAwaitValueTest())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)

        }

        pressBack()

        verify(navController).popBackStack()
    }

}