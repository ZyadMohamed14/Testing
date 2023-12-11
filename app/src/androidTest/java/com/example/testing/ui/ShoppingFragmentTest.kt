package com.example.testing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.testing.launchFragmentInHiltContainer
import com.example.testing.R
import com.example.testing.adapter.ShoppingItemAdapter
import com.example.testing.data.FakeShoppingRepository
import com.example.testing.data.getOrAwaitValueTest
import com.example.testing.local.ShoppingItem
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{

    @get:Rule
    var hiltRule= HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    lateinit var factory: ShoppingFragmentFactory
    @Before
    fun setup(){
        hiltRule.inject()
    }
    /*
    @Test
    fun swipItemShopping_deleteItemFromdb(){
        val shoppingItem=ShoppingItem("test",1,1f,"test",1)
        val testViewModel:ShoppingViewModel?=null

        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = factory) {
             viewModel=testViewModel
            viewModel?.insertItemShoppingIntodb(shoppingItem)
        }
        onView(withId( R.id.rvShoppingItems)).perform(RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemHolder>(
            0, swipeLeft()
        ))
        assertThat(testViewModel?.shoppingItem?.getOrAwaitValueTest()).isEmpty()
    }

     */
    @Test
    fun clickAddShoppingButtonToNavagteToAddShoppingFragment(){
        val navController= mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment>{
            Navigation.setViewNavController(requireView(),navController)
        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        verify(navController).navigate(R.id.action_shoppingFragment_to_addShoppingFragment)
    }
    
}
