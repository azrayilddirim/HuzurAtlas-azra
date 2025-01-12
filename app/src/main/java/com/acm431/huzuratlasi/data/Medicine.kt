package com.acm431.huzuratlasi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,  // Foreign key to link with user
    val name: String,
    val dosage: String,
    val frequency: String,
    val time: String
)

// Sample data
val sampleMedicines = listOf(
    Medicine(1, 1, "Aspirin", "500mg", "Günde 2 kez", "Sabah-Akşam"),
    Medicine(2, 1, "Parol", "500mg", "Günde 3 kez", "Sabah-Öğle-Akşam"),
    Medicine(3, 1, "Vitamin D", "1000IU", "Günde 1 kez", "Sabah")
) 