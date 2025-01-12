package com.acm431.huzuratlasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.acm431.huzuratlasi.data.Medicine
import com.acm431.huzuratlasi.data.User
import com.acm431.huzuratlasi.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val repository: AppRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines

    fun register(username: String, email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = repository.registerUser(username, email, password)
            onResult(result.map { })
        }
    }

    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = repository.loginUser(email, password)
            result.onSuccess { user ->
                _currentUser.value = user
                // Start collecting medicines for this user
                viewModelScope.launch {
                    repository.getMedicinesForUser(user.id).collect { medicines ->
                        _medicines.value = medicines
                    }
                }
            }
            onResult(result.map { })
        }
    }

    fun addMedicine(name: String, dosage: String, frequency: String, time: String) {
        viewModelScope.launch {
            currentUser.value?.let { user ->
                val medicine = Medicine(
                    userId = user.id,
                    name = name,
                    dosage = dosage,
                    frequency = frequency,
                    time = time
                )
                repository.addMedicine(medicine)
            }
        }
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.deleteMedicine(medicine)
        }
    }
}

class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 