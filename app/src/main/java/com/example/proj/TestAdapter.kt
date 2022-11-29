package com.example.proj

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj.Model.RestaurantData
import com.example.proj.Model.ShoppingCartModel
import com.example.proj.eventbus.UpdateCartEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TestAdapter(
    private val menuList: ArrayList<RestaurantData>,
    private val context: Context,
    private val cartListener: CartLoadListener
) : RecyclerView.Adapter<TestAdapter.MenuViewHolder>() {

//    val mutableList : MutableList<ShoppingList> = arrayListOf()


    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //        val restName : TextView = itemView.findViewById(R.id.rest_name)
//        val imageUrl : ImageView = itemView.findViewById(R.id.rest_image)
//        val menuLayoutRelative: RelativeLayout = itemView.findViewById(R.id.menu_layout_relative)
        val menuItemName: TextView = itemView.findViewById(R.id.menu_item_name)
        val menuUrl: ImageView = itemView.findViewById(R.id.menu_image)
        val menuPrice: TextView = itemView.findViewById(R.id.menu_price)
        val menuDescription: TextView = itemView.findViewById(R.id.menu_description)
        val orderButton: Button = itemView.findViewById(R.id.button_add_cart)
//        val lateItem: TextView = itemView.findViewById(R.id.latest_item)

        private var clickListener: RecyclerClickListener? = null
        fun setClickListener(clickListener: RecyclerClickListener) {
            this.clickListener = clickListener;
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener!!.onItemClickListener(v, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        Log.e("cart", "onBindViewHolder CALLLED onBindViewHolder")

        val currentItem = menuList[position]
        holder.menuItemName.text = currentItem.foodname
        holder.menuPrice.text = currentItem.foodprice
        holder.menuDescription.text = currentItem.fooddescription
        Glide.with(context).load(currentItem.foodimage).into(holder.menuUrl)

        val orderButton = holder.orderButton

//        button.setOnClickListener {
//            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
//
//            val intent = Intent(context, FoodMenu::class.java)
//
//
//            intent.putExtra("test", position.toString())
//
//
//            Log.e("database", "onDataChange: $position")
//            context.startActivity(intent)
//
//        }
        //        holder.setClickListener(object : RecyclerClickListener {
//            override fun onItemClickListener(view: View?, position: Int) {
//
//                Log.e("cart", "onItemClickListener CALLLED onItemClickListener")
//                addToCart(currentItem)
//            }
//        }
        orderButton.setOnClickListener {
            Log.e("cart", "oCLICKED")
            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
            addToCart(currentItem)
        }


    }
//    fun writeNewCartItem(userId: String, name: String, email: String) {
//        val user = User(name, email)
//
//        database.child("users").child(userId).setValue(user)
//    }



    private fun addToCart(restData: RestaurantData) {
        val userCart = FirebaseDatabase.getInstance("https://omeal-project-default-rtdb.firebaseio.com/").getReference("Cart")
            .child("USER_ID")
        val df = DecimalFormat("#.##")

        Log.e("cart", "CURRENT ITEM$restData")
        var key = UUID.randomUUID().toString()
//        restData.child(key).child(userId).setValue(user)

        userCart.child(key)
            .addListenerForSingleValueEvent(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        val cartModel = snapshot.getValue(ShoppingCartModel::class.java)
                        val updateData:MutableMap<String,Any> = HashMap()
                        cartModel!!.quantity = cartModel!!.quantity+1;
                        updateData["quantity"] = cartModel!!.quantity
                        updateData["totalPrice"] = cartModel!!.quantity * cartModel.price!!.toFloat()
//                        df.roundingMode = RoundingMode.DOWN
//                        updateData["totalPrice"] = df.format(updateData["totalPrice"])
                        userCart.child(key)
                            .updateChildren(updateData)
                            .addOnSuccessListener {
                                EventBus.getDefault().postSticky(UpdateCartEvent())
                                cartListener.onLoadCartFailed("Added to cart")
                            }
                            .addOnFailureListener { e-> cartListener.onLoadCartFailed(e.message) }

                    }else {
                        val cartModel = ShoppingCartModel()
                        cartModel.key = key
                        cartModel.foodname = restData.foodname
                        cartModel.price = restData.foodprice
                        cartModel.quantity = 1
                        cartModel.totalPrice = restData.foodprice!!.toFloat()

                        userCart.child(key)
                            .setValue(cartModel)
                            .addOnSuccessListener {
                                EventBus.getDefault().postSticky(UpdateCartEvent())
                                cartListener.onLoadCartFailed("Added to cart")
                            }
                            .addOnFailureListener { e-> cartListener.onLoadCartFailed(e.message) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    cartListener.onLoadCartFailed(error.message)
                }
            })



    }

////BACKUP of VIEWHOLDER
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
//
//        class myHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
//
//            private var clickListener:RecyclerClickListener?= null
//            fun setClickListener(clickListener: RecyclerClickListener) {
//                this.clickListener = clickListener;
//            }
//
//            init {
//                itemView.setOnClickListener(this)
//            }
//
//            override fun onClick(v: View?) {
//                clickListener!!.onItemClickListener(v,adapterPosition)
//            }
//
//        }


}



