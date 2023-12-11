package com.example.testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResourcesComparererTest{
     private lateinit var resourcesComparerer:ResourcesComparerer

     @Before
     fun CreateInstance(){
         resourcesComparerer=ResourcesComparerer()
     }
    @Test
    fun StringResourcesSameAsGivenString_returnsTrue(){

        val context= ApplicationProvider.getApplicationContext<Context>()
        val result= resourcesComparerer.isEqual(context, res = R.string.app_name, string = "Testing")
        assertTrue(result)
    }
    @Test
    fun StringResourcesDiffrenetAsGivenString_returnsFalse(){

        val context= ApplicationProvider.getApplicationContext<Context>()
        val result= resourcesComparerer.isEqual(context, res = R.string.app_name, string = "Testing2")
        assertFalse(result)
    }
    @Test
    fun emptyStringResourcesSameAsEmptyString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourcesComparerer.isEqual(context, res = R.string.app_name, string = "")
        assertTrue(result)
    }

    @Test
    fun nullStringResourcesSameAsNullString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourcesComparerer.isEqual(context, res = R.string.app_name, string = null.toString())
        assertTrue(result)
    }

}