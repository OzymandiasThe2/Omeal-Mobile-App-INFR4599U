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
//            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, FoodMenu::class.java)


            intent.putExtra("test", position.toString())


            Log.e("database", "onDataChange: $position")
            context.startActivity(intent)

        }

        viewHolder.restName.text = currentItem.name
        Glide.with(context).load(currentItem.image).into(viewHolder.imageUrl)

    }

    override fun getItemCount(): Int {
        return restList.size
    }


}


