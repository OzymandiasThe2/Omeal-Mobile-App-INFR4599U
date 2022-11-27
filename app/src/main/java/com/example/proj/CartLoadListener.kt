package com.example.proj

import com.example.proj.Model.ShoppingCartModel

interface CartLoadListener {
    fun onLoadCartSuccess(cartModelList: List<ShoppingCartModel>)
    fun onLoadCartFailed(message:String?)
}