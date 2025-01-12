package com.acm431.huzuratlasi.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Insert
    suspend fun insertMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Query("SELECT * FROM medicines WHERE userId = :userId")
    fun getMedicinesForUser(userId: Int): Flow<List<Medicine>>

    @Update
    suspend fun updateMedicine(medicine: Medicine)
} 