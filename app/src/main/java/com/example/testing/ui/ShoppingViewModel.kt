package com.example.testing.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.utils.Constans
import com.example.testing.local.ShoppingItem
import com.example.testing.remote.Event
import com.example.testing.remote.ImageResponse
import com.example.testing.remote.Resource
import com.example.testing.repo.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repo:ShoppingRepository):ViewModel() {
         val shoppingItem=repo.observeAllShoppingItems()
         val totalPrice=repo.observeTotalPrice()

    private val _images=MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> get() = _images

    private val _currentImagesUrl=MutableLiveData<String>()
    val currentImagesUrl: LiveData<String> get() = _currentImagesUrl

    private val _insertItemShoppingStatus=MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertItemShoppingStatus: LiveData<Event<Resource<ShoppingItem>>> get() = _insertItemShoppingStatus

    fun deleteShoppingItem(shoppingItem: ShoppingItem) =viewModelScope.launch {
        repo.deleteShoppingItem(shoppingItem)
    }
    fun insertItemShoppingIntodb(shoppingItem: ShoppingItem)= viewModelScope.launch {
        repo.insertShoppingItem(shoppingItem)
    }
    fun insertItemShopping(name :String,price:String,totalAmount:String){
        if(name.isEmpty()||price.isEmpty()||totalAmount.isEmpty()){
            _insertItemShoppingStatus.postValue(Event(Resource.error("This Feild Must Not Be Empty",null)))
            return
        }
        if(name.length> Constans.MAX_NAME_LENGTH){
            _insertItemShoppingStatus.postValue(Event(Resource.error("This Feild Must Not Be less than ${Constans.MAX_NAME_LENGTH} lenght",null)))
            return
        }
        if(price.length> Constans.MAX_PRICE_LENGTH){
            _insertItemShoppingStatus.postValue(Event(Resource.error("This Feild Must Not Be less than ${Constans.MAX_PRICE_LENGTH} lenght",null)))
            return
        }
        var amount:Int=0
        if(isStringNumeric(totalAmount)){
            amount=totalAmount.toInt()
        }else{
            _insertItemShoppingStatus.postValue(Event(Resource.error("Please Enter Vaild a mount",null)))
            return
        }


        val shoppingItem=ShoppingItem(name,amount,price.toFloat(),_currentImagesUrl.value?:"")
        setCurrentImageUrl("")
        _insertItemShoppingStatus.postValue(Event(Resource.success(shoppingItem)))

    }
    fun isStringNumeric(input: String): Boolean {
        return input.all { it.isDigit() }
    }
    fun setCurrentImageUrl(url:String){
        _currentImagesUrl.postValue(url)
    }
    fun searchImage(imageQuery:String){
        if(imageQuery.isEmpty()){
            return
        }
        _images.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val response=repo.searchForImage(imageQuery)
            _images.postValue(Event(response))
        }
    }
    fun getCurrentItem(): LiveData<List<ShoppingItem>> = repo.observeAllShoppingItems()



}