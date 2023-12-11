package com.example.testing.repo

import androidx.lifecycle.LiveData
import com.example.testing.local.ShoppingItem
import com.example.testing.remote.ImageResponse
import com.example.testing.remote.Resource

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}