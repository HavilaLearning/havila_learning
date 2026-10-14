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
private val navy = Color(0xFF06101C)
private val sidebar = Color(0xFF091827)
private val panel = Color(0xFF102238)
private val panel2 = Color(0xFF152B42)
private val muted = Color(0xFFAFC0D0)

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
        Category("N", "Notícias", "Atualidade e sociedade"), Category("G", "Gospel", "Fé e esperança"),
        Category("D", "Desporto", "Paixão que une"), Category("F", "Filmes", "Grandes histórias"),
        Category("K", "Havila Kids", "Aprender e brincar"), Category("M", "Música", "Sons que inspiram"),
        Category("MZ", "Moçambique", "Nossa terra, nossa gente"), Category("FAM", "Família", "Juntos somos mais fortes")
    )
    var selected by remember { mutableStateOf("Início") }

    Row(Modifier.fillMaxSize().background(navy)) {
        SideMenu(selected) { selected = it }
        Column(Modifier.fillMaxSize().padding(start = 28.dp, end = 30.dp, top = 20.dp, bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
            Header()
            HeroBanner()
            SectionTitle("CANAIS", "Use ← → e pressione OK para assistir")
            LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(horizontal = 3.dp, vertical = 5.dp)) {
                items(ChannelRepository.channels) { channel ->
                    ChannelCard(channel) { context.startActivity(Intent(context, PlayerActivity::class.java).apply { putExtra("streamUrl", channel.streamUrl); putExtra("channelName", channel.name) }) }
                }
            }
            SectionTitle("PARA TODA A FAMÍLIA", null)
            LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 3.dp, vertical = 5.dp)) {
                items(categories) { category -> CategoryCard(category, selected == category.name) { selected = category.name } }
            }
        }
    }
}

@Composable private fun Header() {
    Row(Modifier.fillMaxWidth().height(42.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) { Text("HAVILA", Color.White, fontSize = 27.sp); Spacer(Modifier.width(7.dp)); Text("TV", gold, fontSize = 27.sp) }
        Text("Entretenimento com Valores", muted, fontSize = 13.sp)
    }
}

@Composable private fun SideMenu(selected: String, onSelect: (String) -> Unit) {
    val items = listOf("⌂  Início", "▣  Canais", "▦  Categorias", "♥  Favoritos", "⚙  Configurações")
    Column(Modifier.width(185.dp).fillMaxHeight().background(sidebar).padding(horizontal = 16.dp, vertical = 20.dp).focusGroup(), verticalArrangement = Arrangement.spacedBy(9.dp)) {
        Text("HAVILA", Color.White, fontSize = 25.sp); Text("TV", gold, fontSize = 25.sp); Text("ENTRETENIMENTO COM VALORES", muted, fontSize = 9.sp); Spacer(Modifier.height(18.dp))
        items.forEach { item -> val key = item.substringAfter("  "); MenuItem(item, selected == key) { onSelect(key) } }
    }
}

@Composable private fun MenuItem(text: String, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }; val highlighted = active || focused
    Text(text, color = if (highlighted) Color.Black else Color.White, fontSize = 14.sp, modifier = Modifier.fillMaxWidth().background(if (highlighted) gold else Color.Transparent, RoundedCornerShape(9.dp)).border(if (focused) 2.dp else 0.dp, if (focused) Color.White else Color.Transparent, RoundedCornerShape(9.dp)).padding(horizontal = 11.dp, vertical = 12.dp).onFocusChanged { focused = it.isFocused }.clickable { onClick() }.focusable())
}

@Composable private fun HeroBanner() {
    Box(Modifier.fillMaxWidth().height(142.dp).background(Brush.horizontalGradient(listOf(Color(0xFF10263B), Color(0xFF1D4A72), Color(0xFF17324A))), RoundedCornerShape(16.dp)).border(1.dp, Color(0xFF2C5A7D), RoundedCornerShape(16.dp)).padding(horizontal = 24.dp, vertical = 17.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) { Text("BEM-VINDO À HAVILA TV", gold, fontSize = 11.sp); Text("Entretenimento com Valores", Color.White, fontSize = 27.sp); Text("Conteúdos que informam, educam, inspiram e unem toda a família.", Color(0xFFD6E0E8), fontSize = 13.sp); Text("OK  •  Selecionar canal", Color(0xFFE8EEF3), fontSize = 11.sp) }
    }
}

@Composable private fun SectionTitle(title: String, hint: String?) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) { Text(title, Color.White, fontSize = 20.sp); if (hint != null) Text(hint, muted, fontSize = 11.sp) }
}

@Composable private fun ChannelCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(Modifier.width(270.dp).height(118.dp).scale(if (focused) 1.025f else 1f).background(if (focused) Color(0xFF183653) else panel, RoundedCornerShape(13.dp)).border(if (focused) 2.dp else 1.dp, if (focused) Color(0xFFBFD2E0) else Color(0xFF2C4962), RoundedCornerShape(13.dp)).onFocusChanged { focused = it.isFocused }.clickable { onOpen() }.focusable().padding(horizontal = 16.dp, vertical = 13.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) { Text("●  ${if (channel.streamUrl != null) "DISPONÍVEL" else "CATÁLOGO"}", if (channel.streamUrl != null) Color(0xFF6DDE9A) else Color(0xFF899AAA), fontSize = 10.sp); Text(channel.name, Color.White, fontSize = 20.sp); Text(channel.description, muted, fontSize = 12.sp, maxLines = 2) }
        Text("▶", gold, fontSize = 27.sp, modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable private fun CategoryCard(category: Category, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }; val highlighted = active || focused
    Column(Modifier.width(148.dp).height(86.dp).scale(if (focused) 1.025f else 1f).background(if (highlighted) panel2 else panel, RoundedCornerShape(10.dp)).border(if (focused) 2.dp else 1.dp, if (focused) Color(0xFFBFD2E0) else Color(0xFF2C4962), RoundedCornerShape(10.dp)).onFocusChanged { focused = it.isFocused }.clickable { onClick() }.focusable().padding(11.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(category.icon, gold, fontSize = 12.sp); Text(category.name, Color.White, fontSize = 14.sp); Text(category.subtitle, muted, fontSize = 9.sp, maxLines = 1)
    }
}
