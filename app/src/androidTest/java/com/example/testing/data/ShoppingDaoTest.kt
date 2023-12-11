package com.example.testing.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testing.launchFragmentInHiltContainer
import com.example.testing.local.ShoppingDao
import com.example.testing.local.ShoppingItem
import com.example.testing.local.ShoppingItemDatabase

import com.example.testing.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltRule= HiltAndroidRule(this)



    @Inject @Named("test_db") lateinit var database:ShoppingItemDatabase
    private lateinit var dao: ShoppingDao


    @Before
    fun CreateInstance(){
        hiltRule.inject()
        dao=database.shoppingItemDao()

    }

    @Test
    fun insertShoppingItem()= runTest {
        val shoppingItem=ShoppingItem("chipse",1,20f,"url",1)
        dao.insertShoppingItem(shoppingItem)
        val allItems=dao.observeAllShoppingItems().getOrAwaitValueTest()
        assertThat(allItems).contains(shoppingItem)
    }
    @Test
    fun DeleteShoppingItem()= runTest {
        val shoppingItem=ShoppingItem("chipse",1,20f,"url",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allItems=dao.observeAllShoppingItems().getOrAwaitValueTest()
        assertThat(allItems).doesNotContain(shoppingItem)

    }
    @Test
    fun observeTotalPrice()= runTest {
        val shoppingItem1=ShoppingItem("chipse",2,20f,"url",1)
        val shoppingItem2=ShoppingItem("chipse",4,60f,"url",2)
        val shoppingItem3=ShoppingItem("chipse",1,5.5f,"url",3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        val total_Price=dao.observeTotalPrice().getOrAwaitValueTest()
        val allItems=dao.observeAllShoppingItems().getOrAwaitValueTest()
        assertThat(total_Price).isEqualTo(20f*2 + 60f*4 + 5.5f*1)

    }





    @After
    fun finishTest(){
        database.close()
    }
}