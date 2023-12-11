package com.example.testing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.testing.R
import com.example.testing.databinding.ItemImageBinding
import com.example.testing.databinding.ItemShoppingBinding
import com.example.testing.local.ShoppingItem
import javax.inject.Inject


class ShoppingItemAdapter @Inject constructor(
   private val glide:RequestManager
):RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemHolder> (){
    class ShoppingItemHolder(itemview:ItemShoppingBinding) :RecyclerView.ViewHolder(itemview.root){
        val binding=itemview
    }

private val diffCallBack= object :DiffUtil.ItemCallback<ShoppingItem>(){
    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean = oldItem.id==newItem.id
    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =oldItem.hashCode()==newItem.hashCode()
}
    private val differ=AsyncListDiffer(this,diffCallBack)
    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder {
        val binding =ItemShoppingBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return ShoppingItemHolder(binding)
    }


    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        val shoppingItem= shoppingItems[position]
     holder.binding.apply {
         tvName.text= shoppingItem.name
         tvShoppingItemAmount.text=shoppingItem.amount.toString()
         tvShoppingItemPrice.text=shoppingItem.price.toString()
         glide.load(shoppingItem.imageUrl).into(ivShoppingImage)

     }


    }
    override fun getItemCount(): Int=shoppingItems.size







}
