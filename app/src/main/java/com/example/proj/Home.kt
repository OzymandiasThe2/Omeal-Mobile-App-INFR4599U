package com.example.proj

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.Model.RestaurantData
import com.example.proj.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*


class Home : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    private lateinit var restOrderBtn: Button
    private lateinit var databaseRef: DatabaseReference
    private lateinit var restRecyclerView: RecyclerView
    private lateinit var restArrayList: ArrayList<RestaurantData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        restRecyclerView = findViewById(R.id.rest_list_display)
        restRecyclerView.layoutManager = LinearLayoutManager(this)
        restRecyclerView.setHasFixedSize(true)

        restArrayList = arrayListOf<RestaurantData>()

        getRestData()


//        firebaseDatabase = FirebaseDatabase.getInstance()
//        databaseReference = firebaseDatabase?.getReference("Restaurants")
//
//        getData()
//        restOrderBtn = findViewById(R.id.rest_order_button)
//        val clickText = findViewById<TextView>(R.id.rest_name)
//        clickText.setOnClickListener {
//
//            val myIntent = Intent(this, Home::class.java)
//            startActivity(myIntent)
//
////            forgetpasswordmain.movementMethod = LinkMovementMethod.getInstance();
//
//        }
//        restOrderBtn = findViewById(R.id.rest_order_button)
//        restOrderBtn.setOnClickListener {
//            val intent = Intent(this, FoodMenu::class.java)
//            startActivity(intent)
//        }

    }


    private fun getRestData() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Restaurants")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (restSnapshot in snapshot.children) {
                        val rest = restSnapshot.getValue(RestaurantData::class.java)
                        restArrayList.add(rest!!)
                    }
                    restRecyclerView.adapter = RestAdapter(restArrayList, this@Home)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("database", "onCancelled: ${error.toException()}")
            }

        })
    }


//    private fun getData() {
//        databaseReference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.e("database", "onDataChange: $snapshot")
//
//            }
//            override fun onCancelled(error:DatabaseError) {
//                Log.e("database", "onCancelled: ${error.toException()}")
//            }
//
//        })
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

//    override fun onStart() {
//        super.onStart()
//        restOrderBtn = findViewById(R.id.rest_order_button)
//        restOrderBtn.setOnClickListener {
//            val intent = Intent(this, FoodMenu::class.java)
//            startActivity(intent)
//        }
//
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

