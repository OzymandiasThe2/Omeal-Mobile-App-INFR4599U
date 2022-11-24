package com.example.proj

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
    private lateinit var menuArrayList: ArrayList<MenuData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_food_menu)
        menuRecyclerView = findViewById<RecyclerView>(R.id.menu_list_display)

//        menuRecyclerView = findViewById(R.id.menu_list_display)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
//        menuRecyclerView.setHasFixedSize(true)

        menuArrayList = arrayListOf<MenuData>()

        getMenuData()

    }

    private fun getMenuData() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Menu")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children){
                        val menu = menuSnapshot.getValue(MenuData::class.java)
                        menuArrayList.add(menu!!)
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