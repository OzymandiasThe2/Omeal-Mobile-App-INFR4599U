package com.example.proj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj.Model.MenuData
import com.example.proj.Model.RestaurantData

class TestAdapter(
    private val menuList: ArrayList<MenuData>,
    private val context: Context) : RecyclerView.Adapter<TestAdapter.MenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position : Int) {
        val currentItem = menuList[position]
        holder.menuItemName.text = currentItem.name
        holder.menuPrice.text = currentItem.price
        Glide.with(context).load(currentItem.image).into(holder.menuUrl)

    }

    override fun getItemCount(): Int {
        return menuList.size
    }


    open class MenuViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        val restName : TextView = itemView.findViewById(R.id.rest_name)
//        val imageUrl : ImageView = itemView.findViewById(R.id.rest_image)
        val menuItemName : TextView = itemView.findViewById(R.id.menu_item)
        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)


    }



}