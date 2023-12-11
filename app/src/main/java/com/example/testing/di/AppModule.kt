package com.example.testing.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.testing.R
import com.example.testing.adapter.ImageAdapter
import com.example.testing.local.ShoppingDao
import com.example.testing.local.ShoppingItemDatabase
import com.example.testing.remote.PixabayAPI
import com.example.testing.repo.DefaultShoppingRepository
import com.example.testing.repo.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, "shop_db").build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingItemDao()
    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) =DefaultShoppingRepository(dao,api) as ShoppingRepository
    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pixabay.com")
            .build()
            .create(PixabayAPI::class.java)
    }
    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context:Context
    )=Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground)
    )
    @Singleton
    @Provides
    fun providesIamgeAdapter(glide:RequestManager)=ImageAdapter(glide)
}