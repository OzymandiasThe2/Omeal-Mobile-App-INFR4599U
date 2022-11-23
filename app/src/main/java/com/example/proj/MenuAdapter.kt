//package com.example.proj
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.proj.Model.MenuData
//import com.example.proj.Model.RestaurantData
//
//class MenuAdapter(
//    private val restList: ArrayList<MenuData>,
//    private val context: Context
//) : RecyclerView.Adapter<RestAdapter.MyViewHolder>(){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestAdapter.MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rest_item, parent, false)
//        return MenuAdapter.MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: RestAdapter.MyViewHolder, position: Int) {
////        val context: Context
//        val currentItem = restList[position]
//        holder.menuItemName.text = currentItem.name
//        holder.menuPrice.text = currentItem.price
//        Glide.with(context).load(currentItem.image).into(holder.menuUrl)
////        holder.imageUrl.setImageDrawable()
//    }
//
//    override fun getItemCount(): Int {
//        return restList.size
//    }
//
//
//    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//
//        val menuItemName : TextView = itemView.findViewById(R.id.menu_item)
//        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
//        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)
//
//
//    }
//
////    override fun onBindViewHolder(holder: RestAdapter.MyViewHolder, position: Int) {
//////        val context: Context
////        val currentItem = restList[position]
////        holder.menuItemName.text = currentItem.name
////        holder.menuPrice.text = currentItem.price
////        Glide.with(context).load(currentItem.image).into(holder.menuUrl)
//////        holder.imageUrl.setImageDrawable()
////        }
//
////    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
////        val menuItemName : TextView = itemView.findViewById(R.id.menu_item)
////        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
////        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)
////
////
////    }
//
//}