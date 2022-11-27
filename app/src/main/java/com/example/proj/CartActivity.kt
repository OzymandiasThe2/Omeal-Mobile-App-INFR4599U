package com.example.proj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.Model.ShoppingCartModel
import com.example.proj.eventbus.UpdateCartEvent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.RoundingMode
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(), CartLoadListener {

//    val cartRecycler = findViewById<RecyclerView>(R.id.cart_recycler)
    var cartLoadListener: CartLoadListener?= null
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onUpdateCartEvent(event: UpdateCartEvent) {
        loadCartFirebase()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        loadCartFirebase()
    }


    private fun loadCartFirebase() {
        Log.e("cart", "loadCartFirebase:loadCartFirebase ")
        val cartModels : MutableList<ShoppingCartModel> = ArrayList()
        FirebaseDatabase.getInstance().getReference("Cart")
            .child("USER_ID")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val  cartModel = cartSnapshot.getValue(ShoppingCartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener!!.onLoadCartSuccess(cartModels)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener!!.onLoadCartFailed(error.message)
                }

            })
    }

    private fun init(){
        cartLoadListener = this
        val cartRecycler = findViewById<RecyclerView>(R.id.cart_recycler)
        val layoutManager = LinearLayoutManager(this)
        cartRecycler!!.layoutManager = layoutManager
        cartRecycler!!.addItemDecoration(DividerItemDecoration(this,layoutManager.orientation))

    }

    override fun onLoadCartSuccess(cartModelList: List<ShoppingCartModel>) {
        val adapter = CartAdapter(this, cartModelList)
        Log.e("cart", "onLoadCartSuccess:adapter $adapter")
        var sum = 0.0
        Log.e("cart", "onLoadCartSuccess:SUM0 $sum")
        val cartRecycler = findViewById<RecyclerView>(R.id.cart_recycler)
        val cartTitle = findViewById<TextView>(R.id.cart_title)
        for (ShoppingCartModel in cartModelList!!) {
            sum+= ShoppingCartModel!!.totalPrice
        }
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.UP
        val sumtext = df.format(sum)
        cartTitle.text = StringBuilder("S").append(sumtext)
        cartRecycler!!.adapter = adapter

        Log.e("cart", "onLoadCartSuccess:SUM1 $sum")
        Log.e("cart", "onLoadCartSuccess:cartRecycler $cartRecycler")
        Log.e("cart", "onLoadCartSuccess:cartTitle" + cartTitle.text)
//        Log.e("cart", "onLoadCartSuccess:totalPrice $totalPrice")
//        Log.e("cart", "onLoadCartSuccess: $")

    }

    override fun onLoadCartFailed(message: String?) {
        Toast.makeText(this, message!!, Toast.LENGTH_LONG).show()
    }
}