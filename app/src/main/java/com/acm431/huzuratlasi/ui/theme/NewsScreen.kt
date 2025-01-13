package com.acm431.huzuratlasi.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.animation.animateContentSize

data class NewsItem(
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val newsItems = remember {
        listOf(
            NewsItem(
                "Yeni Sağlık Teknolojileri",
                "Yapay zeka destekli sağlık uygulamaları hayatımızı kolaylaştırıyor. Modern tıp teknolojileri ile tanı ve tedavi süreçleri daha etkili hale geliyor.",
                LocalDateTime.now().minusDays(1),
                "Teknoloji"
            ),
            NewsItem(
                "Sağlıklı Yaşam İpuçları",
                "Günlük egzersiz ve dengeli beslenme ile sağlıklı bir yaşam sürmek mümkün. Uzmanlar düzenli fiziksel aktivite ve doğru beslenmenin önemini vurguluyor.",
                LocalDateTime.now().minusDays(2),
                "Yaşam"
            ),
            NewsItem(
                "Mevsimsel Sağlık Önerileri",
                "Değişen hava koşullarında bağışıklık sistemimizi güçlü tutmak için neler yapmalıyız? İşte uzmanlardan öneriler...",
                LocalDateTime.now().minusDays(3),
                "Sağlık"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Sağlık Haberleri",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(newsItems) { newsItem ->
                NewsCard(newsItem = newsItem)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(newsItem: NewsItem) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Category and Date Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = newsItem.category,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
                
                Text(
                    text = newsItem.date.format(DateTimeFormatter.ofPattern("dd MMMM")),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title with Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (newsItem.category) {
                        "Teknoloji" -> Icons.Default.Computer
                        "Yaşam" -> Icons.Default.Favorite
                        "Sağlık" -> Icons.Default.HealthAndSafety
                        else -> Icons.Default.Article
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(28.dp)
                )
                
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = newsItem.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 40.dp) // Align with title text
            )
        }
    }
}
