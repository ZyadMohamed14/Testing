package com.example.testing.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testing.adapter.ImageAdapter
import com.example.testing.databinding.FragmentImagePackerBinding
import com.example.testing.remote.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickerFragment @Inject constructor(
     val imageAdapter: ImageAdapter
): Fragment() {
    private lateinit var binding: FragmentImagePackerBinding
    lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentImagePackerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setUpRV()
        subscribeToObsrever()
        /*
         val job=lifecycleScope.launch {
            delay(2500)
            binding.etSearch.addTextChangedListener {searchImageQuery->
                job.cancel()
                searchImageQuery.let {
                    if(searchImageQuery.toString().isNotEmpty()){
                        viewModel.searchImage(searchImageQuery.toString())
                    }
                }
            }

        }
         */

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(300L)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchImage(editable.toString())
                    }
                }
            }
        }

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }
    }
    private fun subscribeToObsrever(){
        viewModel.images.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let { result->
                when(result.status){
                    Status.SUCCESS ->{
                        val urls=result.data?.hits?.map { imageResult ->imageResult.previewURL }
                        imageAdapter.images=urls?: listOf()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR->{
                        Snackbar.make(
                        binding.root,
                        result.message ?: "An unknown error occured.",
                        Snackbar.LENGTH_LONG
                    ).show()
                        binding.progressBar.visibility = View.GONE}
                    Status.LOADING -> {
                       binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }
    private fun setUpRV(){
        binding.rvImages.apply {
            adapter= imageAdapter
            layoutManager=GridLayoutManager(requireContext(),4)
        }
    }
}