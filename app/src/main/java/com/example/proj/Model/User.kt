package com.example.proj.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    @ColumnInfo(name = "firstName") val firstName:String?,
    @ColumnInfo(name = "lastName") val lastName:String?,
    @ColumnInfo(name = "address") val address:String?,
    @ColumnInfo(name = "city") val city:String?,
    @ColumnInfo(name = "country") val country:String?,
    @ColumnInfo(name = "zip") val zip:String?,
    @ColumnInfo(name = "email") val email:String?,
    @ColumnInfo(name = "creditCardNum") val creditCardNum:Int?,
    @ColumnInfo(name = "creditCardCVV") val creditCardCVV:String?,
    @ColumnInfo(name = "creditCardExpire") val creditCardExpire:String?,
)
