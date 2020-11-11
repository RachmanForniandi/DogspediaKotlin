package com.example.dogspediakotlin.views.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.databinding.FragmentDetailBinding
import com.example.dogspediakotlin.models.DogPalette
import com.example.dogspediakotlin.utils.getProgressDrawable
import com.example.dogspediakotlin.utils.loadImage
import com.example.dogspediakotlin.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_data_dogs.*


class DetailFragment : Fragment() {
    private lateinit var viewModel:DetailViewModel
    private var dogUuid = 0
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer {dog ->
            dog?.let {
               /* tvNameOfDog.text = dog.dogBreed
                tvDogPurpose.text = dog.bredFor
                tvDogTemperament.text = dog.temperament
                tvDogLifespan.text = dog.lifeSpan
                context?.let {  imgDetailAnimal.loadImage(dog.imageUrl, getProgressDrawable(it))}*/
                dataBinding.dogs = dog

                it.imageUrl.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String?) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{palette ->
                            val intColor = palette?.vibrantSwatch?.rgb ?:0
                            val myPalette = DogPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send_sms ->{
                view?.let {}
            }

            R.id.action_share ->{
                view?.let {}
            }
        }
        return super.onOptionsItemSelected(item)
    }

}