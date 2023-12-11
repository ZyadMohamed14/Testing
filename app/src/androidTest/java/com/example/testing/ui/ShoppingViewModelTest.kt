package com.example.testing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testing.utils.Constans
import com.example.testing.data.FakeShoppingRepository
import com.example.testing.data.MainCorotuineRule
import com.example.testing.data.getOrAwaitValueTest
import com.example.testing.remote.Status
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

class ShoppingViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCorotuineRule= MainCorotuineRule()
    private lateinit var viewModel: ShoppingViewModel
    @Before
    fun setup(){
        viewModel= ShoppingViewModel(FakeShoppingRepository())
    }
    @Test
    fun `insertShoppingItemWithEmptyFeildReturnsError`(){
        viewModel.insertItemShopping("name","","")
        val value= viewModel.insertItemShoppingStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insertShoppingItemWithToLongNameReturnsError`(){
        val name= buildString {
            for (i in 1..Constans.MAX_NAME_LENGTH +1){
                append(1)
            }
        }
        viewModel.insertItemShopping(name,"5","3.0")
        val value= viewModel.insertItemShoppingStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insertShoppingItemWithToLongPriceReturnsError`(){
        val price= buildString {
            for (i in 1..Constans.MAX_PRICE_LENGTH +1){
                append(1)
            }
        }
        viewModel.insertItemShopping("name",price,"3.0")
        val value= viewModel.insertItemShoppingStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insertShoppingItemWithToHighAmountReturnsError`(){

        viewModel.insertItemShopping("name","5","99999999999999999999999999999999999")
        val value= viewModel.insertItemShoppingStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insertShoppingItemWithVaildInputReturnSucsses`(){
        viewModel.insertItemShopping("name","5","3")
        val value= viewModel.insertItemShoppingStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}
