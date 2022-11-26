package com.example.proj

import android.view.View

interface RecyclerClickListener {
    fun onItemClickListener(view: View?, position: Int)
}