package com.example.proj

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.example.proj.Model.MenuData
import com.example.proj.Model.RestaurantData
import com.example.proj.Model.ShoppingList

class TestAdapter(
    private val menuList: ArrayList<RestaurantData>,
    private val context: Context) : RecyclerView.Adapter<TestAdapter.MenuViewHolder>(){
    val mutableList : MutableList<ShoppingList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position : Int) {

        val currentItem = menuList[position]
        holder.menuItemName.text = currentItem.foodname
        holder.menuPrice.text = currentItem.foodprice
        holder.menuDescription.text = currentItem.fooddescription
        Glide.with(context).load(currentItem.foodimage).into(holder.menuUrl)

        val button = holder.orderButton
        button.setOnClickListener {

            Toast.makeText(context, "Added to cart.", Toast.LENGTH_SHORT).show()
            context.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit().apply{
                putString("cart_amount", "$10.00")
            }.apply()
        }

    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    open class MenuViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        val restName : TextView = itemView.findViewById(R.id.rest_name)
//        val imageUrl : ImageView = itemView.findViewById(R.id.rest_image)
        val menuItemName : TextView = itemView.findViewById(R.id.menu_item_name)
        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)
        val menuDescription : TextView = itemView.findViewById(R.id.menu_description)
        val orderButton: Button = itemView.findViewById<Button>(R.id.button_add_cart)



    }



}