package com.example.dogspediakotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.databinding.ItemDataDogsBinding
import com.example.dogspediakotlin.models.DogBreeds
import com.example.dogspediakotlin.views.fragments.ListFragmentDirections
import kotlinx.android.synthetic.main.item_data_dogs.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreeds>): RecyclerView.Adapter<DogsListAdapter.DogsListHolder>(),DogClickListener {

    fun updateDogList(newDogList:List<DogBreeds>){
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsListHolder {
        val view = DataBindingUtil.inflate<ItemDataDogsBinding>(LayoutInflater.from(parent.context),
            R.layout.item_data_dogs,parent,false)
        return DogsListHolder(view)
    }

    override fun getItemCount(): Int = dogsList.size

    override fun onBindViewHolder(holder: DogsListHolder, position: Int) {
        holder.view.dogs = dogsList[position]
        holder.view.listener = this
    }


    override fun onDogClicked(v: View) {
        val uuid = v.dog_Id.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

    class DogsListHolder ( var view: ItemDataDogsBinding): RecyclerView.ViewHolder(view.root)




}