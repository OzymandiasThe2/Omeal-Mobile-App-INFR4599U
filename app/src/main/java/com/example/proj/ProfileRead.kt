package com.example.proj

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proj.Model.User
import com.example.proj.databinding.ActivityProfileEdit2Binding
import com.example.proj.databinding.ActivityProfileReadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileRead : AppCompatActivity() {

    private lateinit var binding : ActivityProfileReadBinding
    private lateinit var userDB: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = UserData.getDatabase(this)
        readData()

        binding.editButton.setOnClickListener{
            val intent = Intent(this, ProfileEdit::class.java)
            startActivity(intent)
        }
    }


    @SuppressLint("SetTextI18n")
    private suspend fun displayData(user: User) {
        withContext(Dispatchers.Main){
            binding.readFirstName.text = "First Name: " + user.firstName
            binding.readLastName.text = "Last Name: " +  user.lastName
            binding.readCreditCarNumber.text = "Credit Card Number: " +  user.creditCardNum.toString()
            binding.readExpiry.text = "Credit Card Expiry: " +  user.creditCardExpire
            binding.readCvv.text = "Credit Card CVV: " +  user.creditCardCVV
            binding.readAddress.text = "Address: " +  user.address
            binding.readCity.text = "City: " +  user.city
            binding.readCountry.text = "Country: " +  user.country
            binding.readZip.text = "ZIP Code: " +  user.zip
            binding.readEmail.text = "Email: " +  user.email
        }

    }

    private fun readData() {
        val idNum = 1
        if (idNum == 1) {
            lateinit var user: User
            GlobalScope.launch{
                user = userDB.userDao().findByCreditCard(idNum.toInt())
                displayData(user)
            }
        }

    }
}