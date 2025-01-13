package com.acm431.huzuratlasi.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                        "Haberler",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = newsItem.category,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = newsItem.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = newsItem.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
