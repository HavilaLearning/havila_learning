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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val navy = Color(0xFF050C16)
private val navy2 = Color(0xFF0B1B2A)
private val blue2 = Color(0xFF1D608D)
private val gold = Color(0xFFDDBB67)
private val white = Color(0xFFF5F7FA)
private val muted = Color(0xFFAAB8C5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
private fun HavilaTvHome() {
    val context = LocalContext.current
    val live = ChannelRepository.channels.filter { it.isLive && !it.streamUrl.isNullOrBlank() }
    val groups = listOf(
        "Moçambique" to "Canais nacionais",
        "Gospel" to "Gospel e fé",
        "Notícias" to "Notícias e informação",
        "Entretenimento" to "Entretenimento",
        "Família" to "Família",
        "Havila Kids" to "Infantil e educativo"
    )
    LazyColumn(Modifier.fillMaxSize().background(navy), contentPadding = PaddingValues(56.dp, 28.dp, 56.dp, 60.dp), verticalArrangement = Arrangement.spacedBy(28.dp)) {
        item { TopBar() }
        item { PremiumHero() }
        item { SectionTitle("AO VIVO AGORA", "◀ ▶  navegar   •   OK  assistir"); Spacer(Modifier.height(12.dp)); ChannelRow(live, context) }
        items(groups) { (category, subtitle) ->
            val channels = ChannelRepository.byCategory(category)
            if (channels.isNotEmpty()) Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { SectionTitle(category.uppercase(), subtitle); ChannelRow(channels, context) }
        }
    }
}

@Composable
private fun TopBar() {
    Row(Modifier.fillMaxWidth().height(62.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) { Text("HAVILA", color = white, fontSize = 31.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.width(8.dp)); Text("TV", color = gold, fontSize = 31.sp, fontWeight = FontWeight.Bold) }
        Text("ENTRETENIMENTO COM VALORES", color = gold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PremiumHero() {
    Box(Modifier.fillMaxWidth().height(210.dp).background(Brush.horizontalGradient(listOf(Color(0xFF0B2033), Color(0xFF15527A), Color(0xFF0B2033))), RoundedCornerShape(26.dp)).border(1.dp, Color(0xFF356A8D), RoundedCornerShape(26.dp)).padding(42.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("BEM-VINDO À HAVILA TV", color = gold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text("Entretenimento com Valores", color = white, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Text("Notícias • Desporto • Gospel • Filmes • Música • Família", color = Color(0xFFE0E8EF), fontSize = 17.sp)
            Text("Selecione um canal e pressione OK", color = white, fontSize = 13.sp)
        }
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text(title, color = white, fontSize = 22.sp, fontWeight = FontWeight.Bold); Text(subtitle, color = muted, fontSize = 12.sp) }
}

@Composable
private fun ChannelRow(channels: List<Channel>, context: android.content.Context) {
    LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(18.dp), contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)) {
        items(channels, key = { it.id }) { channel -> ChannelCard(channel) { context.startActivity(Intent(context, PlayerActivity::class.java).apply { putExtra("streamUrl", channel.streamUrl); putExtra("channelName", channel.name) }) } }
    }
}

@Composable
private fun ChannelCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(Modifier.width(300.dp).height(164.dp).scale(if (focused) 1.035f else 1f).background(if (focused) blue2 else navy2, RoundedCornerShape(18.dp)).border(if (focused) 3.dp else 1.dp, if (focused) Color.White else Color(0xFF28455D), RoundedCornerShape(18.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onOpen() }.padding(18.dp)) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            LogoBadge(channel); Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(if (channel.isLive) "●  AO VIVO" else "CANAL", color = if (channel.isLive) Color(0xFF7FE3A0) else muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(channel.name, color = white, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 2)
                Text(channel.description, color = muted, fontSize = 11.sp, maxLines = 2)
            }
        }
    }
}

@Composable
private fun LogoBadge(channel: Channel) {
    Box(Modifier.width(82.dp).height(82.dp).background(Color(0xFF081521), RoundedCornerShape(14.dp)).border(1.dp, gold.copy(alpha = .65f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
        if (!channel.logoUrl.isNullOrBlank()) AsyncImage(model = channel.logoUrl, contentDescription = channel.name, modifier = Modifier.width(68.dp).height(68.dp))
        else Text(channel.name.take(3).uppercase(), color = if (channel.category == "Gospel") gold else white, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
    }
}
