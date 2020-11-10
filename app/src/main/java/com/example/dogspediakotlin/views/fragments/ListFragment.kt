package com.example.dogspediakotlin.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.adapters.DogsListAdapter
import com.example.dogspediakotlin.viewModels.ListDataViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListDataViewModel
    private val dogsListAdapter = DogsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListDataViewModel::class.java)
        viewModel.generateDummyData()

        list_dogs_data.apply {
            adapter = dogsListAdapter
        }

        refresh_list_data.setOnRefreshListener {
            list_dogs_data.visibility = View.GONE
            txt_error.visibility= View.GONE
            loading_indicator.visibility= View.VISIBLE
            viewModel.refreshByPassCache()
            refresh_list_data.isRefreshing = false

        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(viewLifecycleOwner, Observer {dogs ->
            dogs?.let {
                list_dogs_data.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(viewLifecycleOwner, Observer {isError ->
            isError?.let {
                txt_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading ->
            isLoading?.let {
                loading_indicator.visibility = if (it) View.VISIBLE else View.GONE

                if (it){
                    txt_error.visibility = View.GONE
                    list_dogs_data.visibility = View.GONE
                }
            }
        })




    }

}