package com.acm431.huzuratlasi.repository

import com.acm431.huzuratlasi.data.*
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val userDao: UserDao,
    private val medicineDao: MedicineDao
) {
    // User operations
    suspend fun registerUser(username: String, email: String, password: String): Result<Long> {
        return try {
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                Result.failure(Exception("Email already registered"))
            } else {
                val userId = userDao.insertUser(User(username = username, email = email, password = password))
                Result.success(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = userDao.getUser(email, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Medicine operations
    fun getMedicinesForUser(userId: Int): Flow<List<Medicine>> {
        return medicineDao.getMedicinesForUser(userId)
    }

    suspend fun addMedicine(medicine: Medicine) {
        medicineDao.insertMedicine(medicine)
    }

    suspend fun deleteMedicine(medicine: Medicine) {
        medicineDao.deleteMedicine(medicine)
    }

    suspend fun updateMedicine(medicine: Medicine) {
        medicineDao.updateMedicine(medicine)
    }
} 