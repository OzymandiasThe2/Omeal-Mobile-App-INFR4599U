package com.example.proj

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj.Model.RestaurantData

class RestAdapter(
    private val restList: ArrayList<RestaurantData>,
    private val context: Context,
) : RecyclerView.Adapter<RestAdapter.MyViewHolder>() {

//    inner class (itemView: View) : RecyclerView.ViewHolder(itemView) {
//        // Your holder should contain and initialize a member variable
//        // for any view that will be set as you render a row
//    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val restName: TextView = itemView.findViewById(R.id.rest_name)
        val imageUrl: ImageView = itemView.findViewById(R.id.rest_image)
//        val nameTextView = itemView.findViewById<TextView>(R.id.contact_name)
        val messageButton: Button = itemView.findViewById<Button>(R.id.rest_order_button)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rest_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RestAdapter.MyViewHolder, position: Int) {
        val currentItem = restList[position]
//        val button = MyViewHolder.messageButton
        val button = viewHolder.messageButton
        button.setOnClickListener {
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, FoodMenu::class.java)


            intent.putExtra("test", position.toString())


            Log.e("database", "onDataChange: $position")
            context.startActivity(intent)

        }

        viewHolder.restName.text = currentItem.name
        Glide.with(context).load(currentItem.image).into(viewHolder.imageUrl)


//        holder?.containerView?.setOnClickListener { clickListener(item)
//    }
    }
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }

    //    class ViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
//        private val buttonToMenu : Button = itemView.findViewById(R.id.rest_order_button)
//
//        init {
//            // Attach a click listener to the entire row view
//            itemView.setOnClickListener(this)
//        }
//
//        // Handles the row being being clicked
//        override fun onClick(v: View?) {
//            val restList: ArrayList<RestaurantData>
//            val position = absoluteAdapterPosition // gets item position
//            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
//                val restList = restList[position]
//                // We can access the data within the views
//                Toast.makeText(context, buttonToMenu.text, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    override fun getItemCount(): Int {
        return restList.size
    }


//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val restName: TextView = itemView.findViewById(R.id.rest_name)
//        val imageUrl: ImageView = itemView.findViewById(R.id.rest_image)
//
//        val buttonClick : Button = itemView.findViewById(R.id.rest_order_button)
////        val buttonToMenu : Button = itemView.findViewById(R.id.rest_order_button)
////        val menuItemName : TextView = itemView.findViewById(R.id.menu_item)
////        val menuUrl : ImageView = itemView.findViewById(R.id.menu_image)
////        val menuPrice : TextView = itemView.findViewById(R.id.menu_price)
//
////        init {
////            itemView.setOnClickListener {
////            }
//        }



    }


