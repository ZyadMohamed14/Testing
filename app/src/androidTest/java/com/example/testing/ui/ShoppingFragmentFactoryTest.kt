package com.example.testing.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.testing.adapter.ImageAdapter
import com.example.testing.adapter.ShoppingItemAdapter
import com.example.testing.data.FakeShoppingRepository
import javax.inject.Inject

class ShoppingFragmentFactoryTest@Inject constructor(
    private val imageAdapter: ImageAdapter,
    val glide: RequestManager,
    val shoppingItemAdapter: ShoppingItemAdapter
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickerFragment::class.java.name->ImagePickerFragment(imageAdapter)
            AddShoppingFragment::class.java.name -> AddShoppingFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(shoppingItemAdapter,
                ShoppingViewModel(FakeShoppingRepository())
            )
            else ->super.instantiate(classLoader, className)
        }
    }

}