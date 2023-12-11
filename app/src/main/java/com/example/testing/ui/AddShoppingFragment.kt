package com.example.testing.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.test.services.events.TestStatus
import com.bumptech.glide.RequestManager
import com.example.testing.R
import com.example.testing.databinding.FragmentAddShoppingBinding
import com.example.testing.databinding.FragmentShoppingBinding
import com.example.testing.remote.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingFragment @Inject constructor(
    val glide:RequestManager
) : Fragment() {

    private lateinit var binding: FragmentAddShoppingBinding
    lateinit var viewModel: ShoppingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddShoppingBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding.ivShoppingImage.setOnClickListener { findNavController().navigate(R.id.action_addShoppingFragment_to_imagePackerFragment) }
        subscribeToObserver()
        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertItemShopping(
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemPrice.text.toString(),
                binding.etShoppingItemAmount.text.toString()
            )
        }
        val callBack=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeToObserver(){
        viewModel.currentImagesUrl.observe(viewLifecycleOwner){
            glide.load(it).into(binding.ivShoppingImage)
        }
        viewModel.insertItemShoppingStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS ->{Snackbar.make(binding.root,"adding Shopping Item",Snackbar.LENGTH_SHORT).show()
                        viewModel.insertItemShoppingIntodb(result.data!!)
                        findNavController().popBackStack()
                    }
                    Status.LOADING ->{Snackbar.make(binding.root,"Loading",Snackbar.LENGTH_SHORT).show()}
                    Status.ERROR -> {Snackbar.make(binding.root,result.message?:"Faild To adding Shopping Item",Snackbar.LENGTH_SHORT).show()}

                }
            }
        }
    }
}