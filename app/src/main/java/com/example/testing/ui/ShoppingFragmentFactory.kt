package com.example.testing.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.testing.adapter.ImageAdapter
import com.example.testing.adapter.ShoppingItemAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    val glide: RequestManager,
    val shoppingItemAdapter: ShoppingItemAdapter
):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickerFragment::class.java.name->ImagePickerFragment(imageAdapter)
            AddShoppingFragment::class.java.name -> AddShoppingFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(shoppingItemAdapter)
            else ->super.instantiate(classLoader, className)
        }
    }

}