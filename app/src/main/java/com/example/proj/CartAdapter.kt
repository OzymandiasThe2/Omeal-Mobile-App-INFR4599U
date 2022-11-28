package com.example.proj

import android.app.AlertDialog
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
import com.example.proj.Model.ShoppingCartModel
import com.example.proj.eventbus.UpdateCartEvent
import com.google.firebase.database.FirebaseDatabase
import org.greenrobot.eventbus.EventBus
import java.nio.file.attribute.AclEntry

class CartAdapter (
    private val context: Context,
    private val cartModelList:List<ShoppingCartModel>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var butDel:ImageView?= null
        var butAdd:ImageView?= null
        var butClear:ImageView?= null
        var butConfirm:ImageView?= null
        var textName:TextView?= null
        var textPrice:TextView?= null
        var textQuality:TextView?= null



        init {
            Log.e("cart", "CartAdapter CALLLED")

            butDel = itemView.findViewById(R.id.cart_sub_quantity) as ImageView
            butAdd = itemView.findViewById(R.id.cart_plus_quantity) as ImageView
            butClear = itemView.findViewById(R.id.cart_food_clear) as ImageView
            butConfirm = itemView.findViewById(R.id.cart_confirm) as ImageView
            textName = itemView.findViewById(R.id.cart_food_name) as TextView
            textPrice = itemView.findViewById(R.id.cart_food_price) as TextView
            textQuality = itemView.findViewById(R.id.cart_quantity) as TextView


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        Log.e("cart", "CartAdapter CALLLED1")
        return CartViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.cart_item, parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Log.e("cart", "CartAdapter CALLLED2")


        holder.textName!!.text=StringBuilder().append(cartModelList[position].foodname)
        holder.textPrice!!.text=StringBuilder("$").append(cartModelList[position].price)
        holder.textQuality!!.text=StringBuilder("").append(cartModelList[position].quantity)

        holder.butDel!!.setOnClickListener{_ -> removeCartItem(holder,cartModelList[position])}
        holder.butAdd!!.setOnClickListener{_ -> addCartItem(holder,cartModelList[position])}
        holder.butClear!!.setOnClickListener{_ -> val dialog = AlertDialog.Builder(context)
            .setTitle("Removing Food Item")
            .setMessage("Please confirm you want to remove item from cart")
            .setNegativeButton("Cancel") {dialog,_-> dialog.dismiss()}
            .setPositiveButton("Confirm") {dialog,_-> notifyItemRemoved(position)
                FirebaseDatabase.getInstance().getReference("Cart")
                    .child("USER_ID")
                    .child(cartModelList[position].key!!)
                    .removeValue()
                    .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }
            }
            .create()
            dialog.show()
        Log.e("cart", "onBindViewHolder:textName" + holder.textName)

        }
        holder.butConfirm!!.setOnClickListener{_ -> val dialog = AlertDialog.Builder(context)
            .setTitle("Confirm Purchase?")
            .setMessage("Please confirm you want to buy the food. Our food drone service will come and drop it by shortly")
            .setNegativeButton("Cancel") {dialog,_-> dialog.dismiss()}
            .setPositiveButton("Confirm") {dialog,_-> notifyItemRemoved(position)
                FirebaseDatabase.getInstance().getReference("Cart")
                    .child("USER_ID")
                    .child(cartModelList[position].key!!)
                    .removeValue()
                    .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }
            }
            .create()
            dialog.show()
            Log.e("cart", "onBindViewHolder:textName" + holder.textName)

        }

    }



    private fun addCartItem(holder: CartViewHolder, shoppingCartModel: ShoppingCartModel) {
        Log.e("cart", "CartAdapter CALLLED3")

        shoppingCartModel.quantity +=1
            shoppingCartModel.totalPrice = shoppingCartModel.quantity * shoppingCartModel.price!!.toFloat()
            holder.textQuality!!.text = StringBuilder("").append(shoppingCartModel.quantity)
            updateFirebase(shoppingCartModel)
    }

    private fun removeCartItem(holder: CartViewHolder, shoppingCartModel: ShoppingCartModel) {
        Log.e("cart", "CartAdapter CALLLED4")

        if(shoppingCartModel.quantity >1) {
            shoppingCartModel.quantity -=1
            shoppingCartModel.totalPrice = shoppingCartModel.quantity * shoppingCartModel.price!!.toFloat()
            holder.textQuality!!.text = StringBuilder("").append(shoppingCartModel.quantity)
            updateFirebase(shoppingCartModel)
        }

    }

    private fun updateFirebase(shoppingCartModel: ShoppingCartModel) {
        Log.e("cart", "CartAdapter CALLLED5")

        FirebaseDatabase.getInstance().getReference("Cart")
            .child("USER_ID")
            .child(shoppingCartModel.key!!)
            .setValue(shoppingCartModel)
            .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }
    }

    override fun getItemCount(): Int {
        Log.e("cart", "CartAdapter CALLLED6")

        return cartModelList.size
    }


}