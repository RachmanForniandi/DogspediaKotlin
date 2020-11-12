package com.example.dogspediakotlin.views.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings ->{
                view?.let { Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToSettingsFragment())}
            }
        }
        return super.onOptionsItemSelected(item)
    }

}