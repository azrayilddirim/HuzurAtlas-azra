package com.acm431.huzuratlasi.ui.theme

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.acm431.huzuratlasi.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentUser by viewModel.currentUser.collectAsState()
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Profil",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Avatar with Animation
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandVertically()
            ) {
                Surface(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Avatar",
                        modifier = Modifier
                            .padding(24.dp)
                            .size(72.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Information with Animation
            currentUser?.let { user ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = user.username,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = user.email,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Profile Actions with Animations
                ProfileActionButton(
                    icon = Icons.Default.Edit,
                    text = "Profili Düzenle",
                    onClick = { 
                        showEditDialog = true
                    }
                )

                ProfileActionButton(
                    icon = Icons.Default.Settings,
                    text = "Ayarlar",
                    onClick = { 
                        showSettingsDialog = true
                    }
                )

                ProfileActionButton(
                    icon = Icons.Default.Notifications,
                    text = "Bildirimler",
                    onClick = { 
                        showNotificationsDialog = true
                    }
                )

                ProfileActionButton(
                    icon = Icons.Default.Security,
                    text = "Güvenlik",
                    onClick = { 
                        scope.launch {
                            snackbarHostState.showSnackbar("Güvenlik ayarları yakında eklenecek!")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Logout Button with Animation
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically()
                ) {
                    Button(
                        onClick = {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Çıkış Yap")
                    }
                }
            }
        }

        // Edit Profile Dialog
        if (showEditDialog) {
            EditProfileDialog(
                onDismiss = { showEditDialog = false },
                onConfirm = { newUsername ->
                    // Handle profile edit
                    scope.launch {
                        snackbarHostState.showSnackbar("Profil güncellendi!")
                    }
                    showEditDialog = false
                }
            )
        }

        // Settings Dialog
        if (showSettingsDialog) {
            SettingsDialog(
                onDismiss = { showSettingsDialog = false }
            )
        }

        // Notifications Dialog
        if (showNotificationsDialog) {
            NotificationsDialog(
                onDismiss = { showNotificationsDialog = false }
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Profili Düzenle") },
        text = {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Yeni Kullanıcı Adı") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(username) }) {
                Text("Kaydet")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

@Composable
fun SettingsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ayarlar") },
        text = {
            Column {
                ListItem(
                    headlineContent = { Text("Karanlık Mod") },
                    trailingContent = { Switch(checked = false, onCheckedChange = {}) }
                )
                ListItem(
                    headlineContent = { Text("Bildirimler") },
                    trailingContent = { Switch(checked = true, onCheckedChange = {}) }
                )
                ListItem(
                    headlineContent = { Text("Dil") },
                    supportingContent = { Text("Türkçe") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tamam")
            }
        }
    )
}

@Composable
fun NotificationsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Bildirimler") },
        text = {
            Column {
                ListItem(
                    headlineContent = { Text("İlaç Hatırlatıcıları") },
                    trailingContent = { Switch(checked = true, onCheckedChange = {}) }
                )
                ListItem(
                    headlineContent = { Text("Sağlık Haberleri") },
                    trailingContent = { Switch(checked = true, onCheckedChange = {}) }
                )
                ListItem(
                    headlineContent = { Text("Sistem Bildirimleri") },
                    trailingContent = { Switch(checked = true, onCheckedChange = {}) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tamam")
            }
        }
    )
}

@Composable
fun ProfileActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = text)
    }
} 