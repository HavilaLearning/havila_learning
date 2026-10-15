package com.havila.tv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.focus.onFocusChanged
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage

private val Bg = Color(0xFF030A12)
private val Gold = Color(0xFFF2B82B)
private val White = Color(0xFFF7F7F7)
private val Muted = Color(0xFFC0C5CA)
private val CardBorder = Color(0xFF34404B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

private data class CategoryUi(val name: String, val image: String, val icon: String)

private val categories = listOf(
    CategoryUi("Notícias", "https://images.unsplash.com/photo-1504711434969-e33886168f5c?auto=format&fit=crop&w=900&q=80", "▤"),
    CategoryUi("Gospel", "https://images.unsplash.com/photo-1507692049790-de58290a4334?auto=format&fit=crop&w=900&q=80", "✝"),
    CategoryUi("Desporto", "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?auto=format&fit=crop&w=900&q=80", "⚽"),
    CategoryUi("Filmes", "https://images.unsplash.com/photo-1485846234645-a62644f84728?auto=format&fit=crop&w=900&q=80", "▣"),
    CategoryUi("Havila Kids", "https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?auto=format&fit=crop&w=900&q=80", "♣"),
    CategoryUi("Música", "https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&w=900&q=80", "♫"),
    CategoryUi("Moçambique", "https://images.unsplash.com/photo-1523805009345-7448845a9e53?auto=format&fit=crop&w=900&q=80", "◈"),
    CategoryUi("Família", "https://images.unsplash.com/photo-1511895426328-dc8714191300?auto=format&fit=crop&w=900&q=80", "●")
)

@Composable
private fun HavilaTvHome() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(0) }
    val live = ChannelRepository.channels.filter { it.isLive && !it.streamUrl.isNullOrBlank() }
    Row(Modifier.fillMaxSize().background(Bg)) {
        SideBar(selected) { selected = it }
        Column(Modifier.fillMaxSize().padding(end = 28.dp)) {
            TopActions()
            Hero()
            CategorySection()
            FeaturedChannels(live, context)
        }
    }
}

@Composable
private fun SideBar(selected: Int, onSelected: (Int) -> Unit) {
    val items = listOf(
        Icons.Default.Home to "Início",
        Icons.Default.PlayArrow to "Canais",
        Icons.Default.List to "Categorias",
        Icons.Default.FavoriteBorder to "Favoritos",
        Icons.Default.Settings to "Configurações"
    )
    Column(Modifier.width(260.dp).fillMaxHeight().background(Color(0xFF050E18)).padding(top = 22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Logo()
        Spacer(Modifier.height(32.dp))
        items.forEachIndexed { index, item ->
            val active = selected == index
            Row(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp).clip(RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp)).background(if (active) Gold else Color.Transparent).clickable { onSelected(index) }.focusable(), verticalAlignment = Alignment.CenterVertically) {
                Icon(item.first, item.second, tint = if (active) Color.Black else White, modifier = Modifier.padding(start = 28.dp).width(30.dp))
                Text(item.second, color = if (active) Color.Black else White, fontSize = 17.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp))
            }
        }
    }
}

@Composable
private fun Logo() {
    Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text("HAVILA", color = White, fontSize = 39.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-1).sp)
            Spacer(Modifier.width(7.dp))
            Text("TV", color = Gold, fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)
        }
        Text("Boa Informação. Bons Valores. Para Toda a Família.", color = Muted, fontSize = 9.sp, maxLines = 1)
    }
}

@Composable
private fun TopActions() {
    Row(Modifier.fillMaxWidth().height(58.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(24.dp))
        Text("Pesquisar", color = White, fontSize = 16.sp, modifier = Modifier.padding(start = 9.dp, end = 34.dp))
        Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(25.dp))
        Text("Configurações", color = White, fontSize = 16.sp, modifier = Modifier.padding(start = 9.dp, end = 38.dp))
        Text("18:24", color = White, fontSize = 17.sp)
    }
}

@Composable
private fun Hero() {
    Box(Modifier.fillMaxWidth().height(290.dp).clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)), contentAlignment = Alignment.CenterStart) {
        AsyncImage(model = "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?auto=format&fit=crop&w=1800&q=85", contentDescription = "Havila TV hero", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().alpha(.72f))
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Bg.copy(alpha = .96f), Bg.copy(alpha = .48f), Color.Transparent))))
        Column(Modifier.padding(start = 42.dp, top = 22.dp)) {
            Text("Bem-vindo à Havila TV", color = White, fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
            Text("Televisão digital para toda a família", color = White, fontSize = 25.sp, modifier = Modifier.padding(top = 4.dp))
            Text("NOTÍCIAS  •  EDUCAÇÃO  •  FÉ  •  CULTURA  •  DESPORTO  •  ENTRETENIMENTO", color = White.copy(alpha = .9f), fontSize = 14.sp, letterSpacing = 1.sp, modifier = Modifier.padding(top = 20.dp))
        }
    }
}

@Composable
private fun CategorySection() {
    Column(Modifier.padding(start = 30.dp, top = 4.dp)) {
        Text("CATEGORIAS", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(Modifier.height(12.dp))
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(18.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categories) { _, category -> CategoryCard(category) }
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryUi) {
    var focused by remember { mutableStateOf(false) }
    Box(Modifier.width(280.dp).height(112.dp).scale(if (focused) 1.025f else 1f).clip(RoundedCornerShape(12.dp)).border(if (focused) 3.dp else 1.dp, if (focused) Gold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { }) {
        AsyncImage(model = category.image, contentDescription = category.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().alpha(.9f))
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color.Black.copy(alpha = .55f), Color.Black.copy(alpha = .25f), Color.Black.copy(alpha = .65f)))))
        Row(Modifier.align(Alignment.Center).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(category.icon, color = White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
            Text(category.name, color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@Composable
private fun FeaturedChannels(channels: List<Channel>, context: android.content.Context) {
    Column(Modifier.padding(start = 30.dp, top = 20.dp)) {
        Text("CANAIS EM DESTAQUE", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(Modifier.height(10.dp))
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(18.dp), contentPadding = PaddingValues(end = 32.dp)) {
            itemsIndexed(channels.take(10), key = { _, channel -> channel.id }) { index, channel ->
                FeaturedChannelCard(channel) {
                    context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                        putExtra("streamUrl", channel.streamUrl)
                        putExtra("channelName", channel.name)
                        putExtra("channelIndex", index)
                    })
                }
            }
        }
    }
}

@Composable
private fun FeaturedChannelCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Column(Modifier.width(210.dp).scale(if (focused) 1.025f else 1f).onFocusChanged { focused = it.isFocused }.focusable().clickable { onOpen() }) {
        Box(Modifier.fillMaxWidth().height(105.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFE9E9E9)).border(if (focused) 3.dp else 1.dp, if (focused) Gold else CardBorder, RoundedCornerShape(10.dp))) {
            if (!channel.logoUrl.isNullOrBlank()) AsyncImage(model = channel.logoUrl, contentDescription = channel.name, contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize().padding(18.dp))
            else Text(channel.name, color = Color.Black, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
            if (channel.isLive) Box(Modifier.align(Alignment.TopEnd).background(Color(0xFFE51D2A), RoundedCornerShape(bottomStart = 8.dp))) { Text("AO VIVO", color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)) }
        }
        Text(channel.name, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, top = 7.dp))
        Text(channel.description, color = Muted, fontSize = 12.sp, maxLines = 1, modifier = Modifier.padding(start = 8.dp, top = 2.dp))
    }
}
