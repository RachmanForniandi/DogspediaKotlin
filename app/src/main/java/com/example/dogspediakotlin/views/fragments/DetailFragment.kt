package com.example.dogspediakotlin.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_data_dogs.*


class DetailFragment : Fragment() {
    private lateinit var viewModel:DetailViewModel
    private var dogUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid

        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer {dog ->
            dog?.let {
                tvNameOfDog.text = dog.dogBreed
                tvDogPurpose.text = dog.breedFor
                tvDogTemperament.text = dog.temperament
                tvDogLifespan.text = dog.lifeSpan
            }
        })
    }

}