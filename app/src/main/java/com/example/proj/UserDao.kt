package com.example.proj

import androidx.room.*
import com.example.proj.Model.User


@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Query("SELECT * FROM user_table WHERE id LIKE :idNum LIMIT 1")
    suspend fun findByCreditCard(idNum:Int):User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

//    @Query("UPDATE user_table SET firstName =:firstName, lastName=:lastName WHERE id LIKE :idNum")
//    suspend fun update(firstName:String, lastName:String, idNum: Int)

}