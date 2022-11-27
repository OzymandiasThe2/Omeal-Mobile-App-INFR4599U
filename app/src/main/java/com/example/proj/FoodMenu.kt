package com.example.proj

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.Model.RestaurantData
import com.example.proj.Model.ShoppingCartModel
import com.example.proj.databinding.ActivityFoodMenuBinding
import com.example.proj.eventbus.UpdateCartEvent
import com.google.firebase.database.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FoodMenu : AppCompatActivity(), CartLoadListener {

    var recyclerView: RecyclerView? = null
    lateinit var cartLoadListener: CartLoadListener
    private lateinit var binding: ActivityFoodMenuBinding
    private lateinit var databaseRef : DatabaseReference
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuArrayList: ArrayList<RestaurantData>

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
    fun onUpdateCartEvent(event:UpdateCartEvent) {
        countCartFromFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        countCartFromFirebase()

        var bundle :Bundle ?=intent.extras

        binding = ActivityFoodMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_food_menu)
        menuRecyclerView = findViewById<RecyclerView>(R.id.menu_list_display)

//        menuRecyclerView = findViewById(R.id.menu_list_display)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
//        menuRecyclerView.setHasFixedSize(true)

        menuArrayList = arrayListOf<RestaurantData>()

        getMenuData()

    }

    private fun countCartFromFirebase() {
        val cartModels : MutableList<ShoppingCartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("USER_ID")
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val  cartModel = cartSnapshot.getValue(ShoppingCartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener.onLoadCartSuccess(cartModels)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener.onLoadCartFailed(error.message)
                }

            })
    }

    private fun init() {
        cartLoadListener = this
    }


    private fun getMenuData() {
//        val bundle: Bundle? = intent.extras
//        val string: String? = intent.getStringExtra("keyString")

        val testString=intent.getStringExtra("test")


//        var strUser: String = intent.getStringExtra("test").toString() // 2
//        val string: String? = intent.getString("keyString")


//        val stringHolder : String = intent.getStringExtra("test").toString()
        Log.e("database", "onDataChange1: $testString")

        databaseRef = FirebaseDatabase.getInstance().getReference("Restaurants/$testString/menu")
        Log.e("database", "onDataChangeDatra: $databaseRef")


        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.e("database", "onDataChange: $snapshot")
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children) {  ///will get 0s for rest
//                        for (restItem in menuSnapshot.children) { //will get image/MENU/name  --> i want info from MENU
//                            Log.e("database", "onDataChange: $menuSnapshot")
                            val rest = menuSnapshot.getValue(RestaurantData::class.java)
                            menuArrayList.add(rest!!)
//                        }
                    }

                    menuRecyclerView.adapter = TestAdapter(menuArrayList, this@FoodMenu,cartLoadListener)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("database", "onCancelled: ${error.toException()}")
            }

        })



    }

    override fun onLoadCartSuccess(cartModelList: List<ShoppingCartModel>) {
        var cartSum = 0
        for (cartModel in cartModelList!!) cartSum+= cartModel!!.quantity
        Log.e("database", cartSum.toString())

    }

    override fun onLoadCartFailed(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}