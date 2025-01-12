package com.acm431.huzuratlasi.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.acm431.huzuratlasi.R

data class NewsItem(
    val title: String,
    val description: String,
    val imageUrl: String,
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
                "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d",
                LocalDateTime.now().minusDays(1),
                "Teknoloji"
            ),
            NewsItem(
                "Sağlıklı Yaşam İpuçları",
                "Günlük egzersiz ve dengeli beslenme ile sağlıklı bir yaşam sürmek mümkün. Uzmanlar düzenli fiziksel aktivite ve doğru beslenmenin önemini vurguluyor.",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                LocalDateTime.now().minusDays(2),
                "Yaşam"
            ),
            NewsItem(
                "Mevsimsel Sağlık Önerileri",
                "Değişen hava koşullarında bağışıklık sistemimizi güçlü tutmak için neler yapmalıyız? İşte uzmanlardan öneriler...",
                "https://images.unsplash.com/photo-1505576399279-565b52d4ac71",
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(newsItems) { newsItem ->
                NewsCard(newsItem)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = newsItem.imageUrl,
                    contentDescription = newsItem.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.placeholder_image),
                    fallback = painterResource(id = R.drawable.placeholder_image),
                    placeholder = painterResource(id = R.drawable.placeholder_image)
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = newsItem.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = newsItem.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = newsItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
