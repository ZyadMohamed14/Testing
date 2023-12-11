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
import javax.inject.Inject

class ImageAdapter @Inject constructor(
   private val glide:RequestManager
):RecyclerView.Adapter<ImageAdapter.ImageViewHolder> (){
    var images:List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    class ImageViewHolder(itemview: ItemImageBinding) :RecyclerView.ViewHolder(itemview.root){
        val binding=itemview
    }

private val diffCallBack= object :DiffUtil.ItemCallback<String>(){

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }


    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }

}
    private val differ=AsyncListDiffer(this,diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =ItemImageBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url= images[position]
     holder.binding.apply {

         glide.load(url).into(ivShoppingImage)
         this.ivShoppingImage.setOnClickListener {
          OnItemClickLisnener?.let {click ->
              click(url)

          }
         }
     }


    }
    override fun getItemCount(): Int=images.size

    private var OnItemClickLisnener:((String)->Unit)?=null
    fun setOnItemClickListener(listnere:(String)->Unit){
        OnItemClickLisnener=listnere
    }





}