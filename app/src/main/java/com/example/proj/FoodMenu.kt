package com.example.proj

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.Model.MenuData
import com.example.proj.Model.RestaurantData
import com.example.proj.databinding.ActivityFoodMenuBinding
import com.example.proj.databinding.ActivityHomeBinding
import com.google.firebase.database.*


class FoodMenu : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    private lateinit var binding: ActivityFoodMenuBinding
    private lateinit var databaseRef : DatabaseReference
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuArrayList: ArrayList<RestaurantData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                    menuRecyclerView.adapter = TestAdapter(menuArrayList, this@FoodMenu)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("database", "onCancelled: ${error.toException()}")
            }

        })



    }

}