package com.example.testing.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase :RoomDatabase(){
    abstract fun shoppingItemDao(): ShoppingDao
    companion object {
        @Volatile
        private var INSTANCE: ShoppingItemDatabase? = null

        fun getInstance(context: Context): ShoppingItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingItemDatabase::class.java,
                    "shopping_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}