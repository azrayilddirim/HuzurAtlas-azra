package com.acm431.huzuratlasi.ui.theme

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.acm431.huzuratlasi.R

data class EmergencyContact(
    val name: String,
    val number: String,
    val description: String,
    val icon: Int // Resource ID for the icon
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val context = LocalContext.current
    
    val emergencyContacts = listOf(
        EmergencyContact(
            "Acil Çağrı Merkezi",
            "112",
            "Ambulans, İtfaiye, Polis",
            R.drawable.ic_emergency_112
        ),
        EmergencyContact(
            "Polis İmdat",
            "155",
            "Güvenlik durumları için",
            R.drawable.ic_police_155
        ),
        EmergencyContact(
            "Jandarma İmdat",
            "156",
            "Kırsal bölgeler için",
            R.drawable.ic_gendarme_156
        ),
        EmergencyContact(
            "AFAD",
            "122",
            "Afet ve Acil Durum",
            R.drawable.ic_afad_122
        ),
        EmergencyContact(
            "Alo Sağlık",
            "184",
            "Sağlık Danışma Hattı",
            R.drawable.ic_health_184
        )
    )

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Acil Durum Numaraları",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            emergencyContacts.forEach { contact ->
                EmergencyContactCard(
                    contact = contact,
                    onCallClick = { makePhoneCall(contact.number) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactCard(
    contact: EmergencyContact,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = onCallClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Emergency service icon
                Image(
                    painter = painterResource(id = contact.icon),
                    contentDescription = contact.name,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp)
                )
                
                Column {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = contact.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            Button(
                onClick = onCallClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentRed
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Ara",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(contact.number)
            }
        }
    }
}