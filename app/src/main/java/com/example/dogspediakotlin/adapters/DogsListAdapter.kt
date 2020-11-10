package com.example.dogspediakotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.databinding.ItemDataDogsBinding
import com.example.dogspediakotlin.models.DogBreeds
import kotlinx.android.synthetic.main.item_data_dogs.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreeds>): RecyclerView.Adapter<DogsListAdapter.DogsListHolder>() {

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
    }

    class DogsListHolder ( var view: ItemDataDogsBinding): RecyclerView.ViewHolder(view.root)
}