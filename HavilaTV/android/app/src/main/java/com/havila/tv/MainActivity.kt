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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

private data class Category(val icon: String, val name: String, val subtitle: String)
private val gold = Color(0xFFFFC629)
private val navy = Color(0xFF07131F)
private val surface = Color(0xFF102235)
private val surfaceFocus = Color(0xFF1B344C)
private val textMuted = Color(0xFFB8C6D2)
private val focusBorder = Color(0xFFE6EEF5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
fun HavilaTvHome() {
    val context = LocalContext.current
    val categories = listOf(
        Category("NEWS", "Notícias", "Atualidade"), Category("G", "Gospel", "Fé e esperança"),
        Category("SPORT", "Desporto", "Paixão que une"), Category("FILM", "Filmes", "Grandes histórias"),
        Category("KIDS", "Havila Kids", "Aprender e brincar"), Category("MUSIC", "Música", "Sons que inspiram"),
        Category("MZ", "Moçambique", "Nossa terra"), Category("FAM", "Família", "Para todos")
    )

    var selected by remember { mutableStateOf("Início") }

    Column(Modifier.fillMaxSize().background(navy).padding(horizontal = 42.dp, vertical = 28.dp)) {
        TopBar()
        Spacer(Modifier.height(18.dp))
        HeroBanner()
        Spacer(Modifier.height(24.dp))
        TvSectionTitle("CANAIS AO VIVO", "← →  •  OK para assistir")
        Spacer(Modifier.height(10.dp))
        LazyRow(
            Modifier.fillMaxWidth().focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
        ) {
            items(ChannelRepository.channels) { channel ->
                ChannelCard(channel) {
                    context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                        putExtra("streamUrl", channel.streamUrl)
                        putExtra("channelName", channel.name)
                    })
                }
            }
        }
        Spacer(Modifier.height(22.dp))
        TvSectionTitle("EXPLORE POR CATEGORIA", null)
        Spacer(Modifier.height(10.dp))
        LazyRow(
            Modifier.fillMaxWidth().focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
        ) {
            items(categories) { category -> CategoryCard(category, selected == category.name) { selected = category.name } }
        }
    }
}

@Composable
private fun TopBar() {
    Row(Modifier.fillMaxWidth().height(48.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("HAVILA", color = Color.White, fontSize = 30.sp)
            Spacer(Modifier.width(8.dp))
            Text("TV", color = gold, fontSize = 30.sp)
        }
        Text("ENTRETENIMENTO COM VALORES", color = textMuted, fontSize = 12.sp)
    }
}

@Composable
private fun HeroBanner() {
    Box(
        Modifier.fillMaxWidth().height(190.dp)
            .background(Brush.horizontalGradient(listOf(Color(0xFF10283D), Color(0xFF1C496C), Color(0xFF10283D))), RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFF315A77), RoundedCornerShape(18.dp))
            .padding(horizontal = 34.dp, vertical = 27.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(9.dp), modifier = Modifier.fillMaxWidth(0.72f)) {
            Text("BEM-VINDO À HAVILA TV", color = gold, fontSize = 12.sp)
            Text("Entretenimento com Valores", color = Color.White, fontSize = 32.sp)
            Text("Notícias, desporto, gospel, filmes, música e conteúdos para toda a família.", color = Color(0xFFD9E3EA), fontSize = 15.sp)
            Text("Pressione OK para começar", color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
private fun TvSectionTitle(title: String, hint: String?) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = Color.White, fontSize = 21.sp)
        if (hint != null) Text(hint, color = textMuted, fontSize = 12.sp)
    }
}

@Composable
private fun ChannelCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(
        Modifier.width(245.dp).height(125.dp)
            .scale(if (focused) 1.035f else 1f)
            .background(if (focused) surfaceFocus else surface, RoundedCornerShape(15.dp))
            .border(if (focused) 3.dp else 1.dp, if (focused) focusBorder else Color(0xFF2A445B), RoundedCornerShape(15.dp))
            .onFocusChanged { focused = it.isFocused }
            .clickable { onOpen() }.focusable()
            .padding(horizontal = 17.dp, vertical = 15.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(if (channel.streamUrl != null) "●  AO VIVO" else "○  DISPONÍVEL EM BREVE", color = if (channel.streamUrl != null) Color(0xFF75E0A0) else textMuted, fontSize = 10.sp)
            Text(channel.name, color = Color.White, fontSize = 19.sp)
            Text(channel.description, color = textMuted, fontSize = 12.sp, maxLines = 2)
        }
        Text("▶", color = gold, fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
private fun CategoryCard(category: Category, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Column(
        Modifier.width(160.dp).height(90.dp)
            .scale(if (focused) 1.035f else 1f)
            .background(if (focused) surfaceFocus else surface, RoundedCornerShape(13.dp))
            .border(if (focused) 3.dp else 1.dp, if (focused) focusBorder else Color(0xFF2A445B), RoundedCornerShape(13.dp))
            .onFocusChanged { focused = it.isFocused }
            .clickable { onClick() }.focusable().padding(13.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(category.icon, color = gold, fontSize = 10.sp)
        Text(category.name, color = Color.White, fontSize = 15.sp)
        Text(category.subtitle, color = textMuted, fontSize = 10.sp, maxLines = 1)
    }
}
