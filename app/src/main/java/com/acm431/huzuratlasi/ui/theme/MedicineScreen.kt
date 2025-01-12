package com.acm431.huzuratlasi.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acm431.huzuratlasi.data.Medicine
import com.acm431.huzuratlasi.data.sampleMedicines
import com.acm431.huzuratlasi.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreenContent(navController: NavController, viewModel: AppViewModel, modifier: Modifier = Modifier) {
    val medicines by viewModel.medicines.collectAsState()
    var medicineName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "İlaçlarım",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add Medicine Form
        OutlinedTextField(
            value = medicineName,
            onValueChange = { medicineName = it },
            label = { Text("İlaç Adı") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Doz") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = frequency,
            onValueChange = { frequency = it },
            label = { Text("Kullanım Sıklığı") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Kullanım Zamanı") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (medicineName.isNotBlank() && dosage.isNotBlank() && 
                    frequency.isNotBlank() && time.isNotBlank()) {
                    viewModel.addMedicine(
                        name = medicineName,
                        dosage = dosage,
                        frequency = frequency,
                        time = time
                    )
                    // Clear form
                    medicineName = ""
                    dosage = ""
                    frequency = ""
                    time = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("İlaç Ekle")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Medicine List
        LazyColumn {
            items(medicines) { medicine ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = medicine.name,
                                style = TextStyle(fontSize = 18.sp)
                            )
                            Text(
                                text = "Doz: ${medicine.dosage}",
                                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                            )
                            Text(
                                text = "Sıklık: ${medicine.frequency}",
                                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                            )
                            Text(
                                text = "Zaman: ${medicine.time}",
                                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                            )
                        }
                        IconButton(
                            onClick = {
                                viewModel.deleteMedicine(medicine)
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Sil",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

