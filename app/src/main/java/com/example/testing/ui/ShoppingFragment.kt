package com.example.testing.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.adapter.ShoppingItemAdapter
import com.example.testing.databinding.FragmentShoppingBinding
import com.example.testing.local.ShoppingItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter

) : Fragment() {
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var binding:FragmentShoppingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentShoppingBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setUpRcyclerView(){

        binding.rvShoppingItems.apply {
            adapter=shoppingItemAdapter
            layoutManager=LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallBack).attachToRecyclerView(this)
        }

    }
    private fun SubscribetoObsrerver(){
        viewModel?.shoppingItem?.observe(viewLifecycleOwner){
            Log.d("benz", "SubscribetoObsrerver${it}")
            shoppingItemAdapter.shoppingItems= it
        }

        viewModel?.totalPrice?.observe(viewLifecycleOwner){
            val price=it?:0f
            val priceText = "TotalPrice${price}"
            binding.tvShoppingItemPrice.text=priceText
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        SubscribetoObsrerver()
        setUpRcyclerView()

        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addShoppingFragment)
        }
    }
    private val itemTouchCallBack = object :ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ){

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
           val postion =viewHolder.layoutPosition
            val item =shoppingItemAdapter.shoppingItems[postion]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(binding.root,"Delete Item sucssess",3000).apply {
                setAction("Undo"){
                    viewModel?.insertItemShoppingIntodb(item)
                }
                show()
            }
        }

    }
}