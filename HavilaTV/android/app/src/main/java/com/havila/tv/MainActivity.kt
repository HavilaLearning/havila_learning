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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val gold = Color(0xFFDDBB67)
private val navy = Color(0xFF06111D)
private val surface = Color(0xFF102235)
private val surfaceFocus = Color(0xFF183A55)
private val textMuted = Color(0xFFB8C6D2)
private val focusBorder = Color(0xFFF2F6FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
fun HavilaTvHome() {
    val context = LocalContext.current
    val sectionCategories = listOf(
        "Moçambique" to "Canais moçambicanos",
        "Gospel" to "Gospel e fé",
        "Notícias" to "Notícias e informação",
        "Entretenimento" to "Entretenimento",
        "Família" to "Família",
        "Havila Kids" to "Infantil e educativo"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(navy),
        contentPadding = PaddingValues(start = 54.dp, end = 54.dp, top = 30.dp, bottom = 54.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { TopBar() }
        item { HeroBanner() }

        item {
            SectionHeader("CANAIS AO VIVO", "Use ◀ ▶ e pressione OK")
            Spacer(Modifier.height(10.dp))
            ChannelRow(ChannelRepository.channels.filter { it.streamUrl != null }, context)
        }

        items(sectionCategories) { (category, subtitle) ->
            val channels = ChannelRepository.byCategory(category)
            if (channels.isNotEmpty()) {
                Column {
                    SectionHeader(category.uppercase(), subtitle)
                    Spacer(Modifier.height(10.dp))
                    ChannelRow(channels, context)
                }
            }
        }
    }
}

@Composable
private fun TopBar() {
    Row(
        Modifier.fillMaxWidth().height(54.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("HAVILA", color = Color.White, fontSize = 30.sp)
            Spacer(Modifier.width(8.dp))
            Text("TV", color = gold, fontSize = 30.sp)
        }
        Text("ENTRETENIMENTO COM VALORES", color = Color(0xFFD9C88E), fontSize = 13.sp)
    }
}

@Composable
private fun HeroBanner() {
    Box(
        Modifier.fillMaxWidth().height(210.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF10283D), Color(0xFF1D527A), Color(0xFF10283D))
                ), RoundedCornerShape(22.dp)
            )
            .border(1.dp, Color(0xFF3B6684), RoundedCornerShape(22.dp))
            .padding(horizontal = 40.dp, vertical = 30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.78f),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text("BEM-VINDO À HAVILA TV", color = gold, fontSize = 13.sp)
            Text("Entretenimento com Valores", color = Color.White, fontSize = 34.sp)
            Text(
                "Notícias, desporto, gospel, filmes, música e conteúdos para toda a família.",
                color = Color(0xFFE1EAF0), fontSize = 16.sp
            )
            Text("Pressione OK para começar", color = Color.White, fontSize = 13.sp)
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 21.sp)
        Text(subtitle, color = textMuted, fontSize = 12.sp)
    }
}

@Composable
private fun ChannelRow(channels: List<Channel>, context: android.content.Context) {
    LazyRow(
        Modifier.fillMaxWidth().focusGroup(),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 7.dp)
    ) {
        items(channels) { channel ->
            ChannelCard(channel) {
                context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                    putExtra("streamUrl", channel.streamUrl)
                    putExtra("channelName", channel.name)
                })
            }
        }
    }
}

@Composable
private fun ChannelCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(
        Modifier.width(292.dp).height(142.dp)
            .scale(if (focused) 1.025f else 1f)
            .background(if (focused) surfaceFocus else surface, RoundedCornerShape(16.dp))
            .border(
                if (focused) 3.dp else 1.dp,
                if (focused) focusBorder else Color(0xFF2B4961),
                RoundedCornerShape(16.dp)
            )
            .onFocusChanged { focused = it.isFocused }
            .clickable(enabled = channel.streamUrl != null) { onOpen() }
            .focusable()
            .padding(horizontal = 20.dp, vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.82f),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                if (channel.streamUrl != null) "●  AO VIVO" else "○  DISPONÍVEL EM BREVE",
                color = if (channel.streamUrl != null) Color(0xFF8DE0AA) else textMuted,
                fontSize = 10.sp
            )
            Text(channel.name, color = Color.White, fontSize = 19.sp)
            Text(channel.description, color = textMuted, fontSize = 12.sp, maxLines = 2)
        }
        if (channel.streamUrl != null) {
            Text("▶", color = gold, fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}
