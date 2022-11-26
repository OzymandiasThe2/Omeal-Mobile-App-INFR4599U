package com.example.proj

import android.content.Context
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
import com.example.proj.Model.RestaurantData

class TestAdapter(
    private val menuList: ArrayList<RestaurantData>,
    private val context: Context) : RecyclerView.Adapter<TestAdapter.MenuViewHolder>(){
//    val mutableList : MutableList<ShoppingList> = arrayListOf()

    class myHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        private var clickListener:RecyclerClickListener?= null
        fun setClickListener(clickListener: RecyclerClickListener) {
            this.clickListener = clickListener;
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener!!.onItemClickListener(v,adapterPosition)
        }

    }

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
                putString("cart_tax", "13%")
                putString("cart_quantity", "1")
                putString("cart_total", "$11.30")
            }.apply()
        }
        val lastItem = context.getSharedPreferences("shopping_cart",Context.MODE_PRIVATE)
            .getString("cart_latest_item","no items in cart")
//        Log.e("database", "onDataChange: $lastItem")
//        holder.lateItem.text = lastItem

    }


//    override fun onBindViewHolder(holder: MenuViewHolder, position : Int) {
//
//        val currentItem = menuList[position]
//        holder.menuItemName.text = currentItem.foodname
//        holder.menuPrice.text = currentItem.foodprice
//        holder.menuDescription.text = currentItem.fooddescription
//        Glide.with(context).load(currentItem.foodimage).into(holder.menuUrl)
//
//        val button = holder.orderButton
//        button.setOnClickListener {
//
//            Toast.makeText(context, "Added to cart.", Toast.LENGTH_SHORT).show()
//            context.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit().apply{
//                putString("cart_amount", "$10.00")
//                putString("cart_tax", "13%")
//                putString("cart_quantity", "1")
//                putString("cart_total", "$11.30")
//            }.apply()
//        }
//        val lastItem = context.getSharedPreferences("shopping_cart",Context.MODE_PRIVATE)
//            .getString("cart_latest_item","no items in cart")
////        Log.e("database", "onDataChange: $lastItem")
////        holder.lateItem.text = lastItem
//
//    }

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
//        val lateItem: TextView = itemView.findViewById(R.id.latest_item)



    }



}