package com.example.proj

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.proj.Model.User
import com.example.proj.databinding.ActivityProfileEdit2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileEdit : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEdit2Binding
    private lateinit var userDB: UserData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEdit2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = UserData.getDatabase(this)

        binding.butConfirm.setOnClickListener {
            writeData()
            Log.d("ROOM", "1")
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            Log.d("ROOM", "1")

        }
        binding.butClear.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                userDB.userDao().deleteAll()
            }

        }

    }

    private fun writeData() {

//        GlobalScope.launch(Dispatchers.IO) {
//            userDB.userDao().deleteAll()
//        }
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val credCardNum = binding.creditCardNumber.text.toString()
        val city = binding.city.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zipCode.text.toString()
        val email = binding.emailAddress.text.toString()
        val address = binding.address.text.toString()
        val cvv = binding.creditCardCvv.text.toString()
        val creditExpiry = binding.creditCardExpire.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && credCardNum.isNotEmpty()) {
            val user = User(
                1,
                firstName,
                lastName,
                address,
                city,
                country,
                zip,
                email,
                credCardNum.toInt(),
                cvv,
                creditExpiry
            )
            GlobalScope.launch(Dispatchers.IO) {
                userDB.userDao().deleteAll()
                userDB.userDao().insert(user)
            }
            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.creditCardNumber.text.clear()
            binding.city.text.clear()
            binding.country.text.clear()
            binding.zipCode.text.clear()
            binding.emailAddress.text.clear()
            binding.address.text.clear()
            binding.creditCardCvv.text.clear()
            binding.creditCardExpire.text.clear()

            Toast.makeText(this@ProfileEdit, "Information Saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@ProfileEdit, "Please Input Data", Toast.LENGTH_SHORT).show()
        }

    }
//    private suspend fun displayData(user: User) {
//        withContext(Dispatchers.Main){
//            binding.
//        }
//
//    }

//    private fun readData() {
//        val credCardNum = binding.creditCardNumber.text.toString()
//        if (credCardNum.isNotEmpty()) {
//            lateinit var user: User
//            GlobalScope.launch{
//                user = userDB.userDao().findByCreditCard(credCardNum.toInt())
//                displayData(user)
//            }
//        }
//
//    }


}