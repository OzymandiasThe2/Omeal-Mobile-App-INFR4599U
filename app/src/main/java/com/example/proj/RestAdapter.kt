package com.example.proj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj.Model.RestaurantData

class RestAdapter(
    private val restList: ArrayList<RestaurantData>,
    private val context: Context) : RecyclerView.Adapter<RestAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rest_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position : Int) {
        val currentItem = restList[position]
        holder.restName.text = currentItem.name
        Glide.with(context).load(currentItem.image).into(holder.imageUrl)
    }

    override fun getItemCount(): Int {
        return restList.size
    }


    open class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val restName : TextView = itemView.findViewById(R.id.rest_name)
        val imageUrl : ImageView = itemView.findViewById(R.id.rest_image)
//        val menuItemName : TextView = itemView.findViewById(R.id.menu_item)
//        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
//        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)


    }

}