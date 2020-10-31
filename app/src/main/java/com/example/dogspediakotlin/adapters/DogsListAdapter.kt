package com.example.dogspediakotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dogspediakotlin.R
import com.example.dogspediakotlin.models.DogBreeds
import kotlinx.android.synthetic.main.item_data_dogs.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreeds>): RecyclerView.Adapter<DogsListAdapter.DogsListHolder>() {

    fun updateDogList(newDogList:List<DogBreeds>){
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_dogs,parent,false)
        return DogsListHolder(view)
    }

    override fun onBindViewHolder(holder: DogsListHolder, position: Int) {
        holder.itemView.txt_dog_name.text = dogsList[position].dogBreed
        holder.itemView.txt_dog_lifespan.text = dogsList[position].lifeSpan
    }

    override fun getItemCount(): Int = dogsList.size

    class DogsListHolder (itemView: View):RecyclerView.ViewHolder(itemView){

    }
}