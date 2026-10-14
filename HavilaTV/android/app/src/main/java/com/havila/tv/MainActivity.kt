package com.havila.tv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Category(val icon: String, val name: String, val subtitle: String)

private val gold = Color(0xFFFFC629)
private val navy = Color(0xFF06101C)
private val panel = Color(0xFF102238)

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
        Category("📰", "Notícias", "Atualidade e sociedade"),
        Category("✝", "Gospel", "Fé e esperança"),
        Category("⚽", "Desporto", "Paixão que une"),
        Category("🎬", "Filmes", "Grandes histórias"),
        Category("👧", "Havila Kids", "Aprender e brincar"),
        Category("🎵", "Música", "Sons que inspiram"),
        Category("🇲🇿", "Moçambique", "Nossa terra, nossa gente"),
        Category("👨‍👩‍👧", "Família", "Juntos somos mais fortes")
    )
    val channels = ChannelRepository.channels
    var selected by remember { mutableStateOf("Início") }

    Row(modifier = Modifier.fillMaxSize().background(navy)) {
        SideMenu(selected = selected, onSelect = { selected = it })
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp, vertical = 22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("HAVILA TV", color = Color.White, fontSize = 30.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text("⌕  Pesquisar", color = Color.LightGray, fontSize = 16.sp)
                    Text("⚙  Configurações", color = Color.LightGray, fontSize = 16.sp)
                }
            }
            HeroBanner()
            Text("AO VIVO", color = Color.White, fontSize = 22.sp)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(2.dp)) {
                items(channels) { channel ->
                    ChannelCard(channel) {
                        context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                            putExtra("streamUrl", channel.streamUrl)
                            putExtra("channelName", channel.name)
                        })
                    }
                }
            }
            Text("Para toda a família", color = Color.White, fontSize = 21.sp)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(2.dp)) {
                items(categories) { category -> CategoryCard(category, selected == category.name) { selected = category.name } }
            }
            Text("⭐  Em destaque", color = gold, fontSize = 20.sp)
            Text("Conteúdos que informam, educam, inspiram e entretêm.", color = Color(0xFFB9C7D5), fontSize = 15.sp)
        }
    }
}

@Composable
private fun SideMenu(selected: String, onSelect: (String) -> Unit) {
    val items = listOf("⌂  Início", "▣  Canais", "▦  Categorias", "♥  Favoritos", "⚙  Configurações")
    Column(modifier = Modifier.width(190.dp).fillMaxHeight().background(Color(0xFF081727)).padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("HAVILA", color = Color.White, fontSize = 25.sp)
        Text("TV", color = gold, fontSize = 25.sp)
        Text("Entretenimento com Valores", color = Color(0xFFB9C7D5), fontSize = 11.sp)
        Spacer(Modifier.height(16.dp))
        items.forEach { item ->
            val key = item.substringAfter("  ")
            val active = selected == key
            Text(item, color = if (active) Color.Black else Color.White, fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth().background(if (active) gold else Color.Transparent, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 13.dp).onFocusChanged { if (it.isFocused) onSelect(key) }.focusable())
        }
    }
}

@Composable
private fun HeroBanner() {
    Box(modifier = Modifier.fillMaxWidth().height(170.dp).background(Brush.horizontalGradient(listOf(Color(0xFF14283D), Color(0xFF304B62))), RoundedCornerShape(16.dp)).padding(25.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("BEM-VINDO À HAVILA TV", color = gold, fontSize = 12.sp)
            Text("Entretenimento\ncom Valores", color = Color.White, fontSize = 31.sp)
            Text("Conteúdos que informam, educam, inspiram e unem toda a família.", color = Color(0xFFD5DEE7), fontSize = 14.sp)
            Text("▶  Assistir Agora", color = Color.Black, fontSize = 14.sp, modifier = Modifier.background(gold, RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 9.dp))
        }
    }
}

@Composable
private fun ChannelCard(channel: Channel, onOpen: () -> Unit) {
    Box(modifier = Modifier.width(250.dp).height(105.dp).background(panel, RoundedCornerShape(12.dp)).border(1.dp, Color(0xFF30485E), RoundedCornerShape(12.dp)).padding(15.dp).onFocusChanged { }.focusable()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("●  ${if (channel.isLive) "AO VIVO" else "OFFLINE"}", color = if (channel.isLive) Color(0xFFFF5B5B) else Color.Gray, fontSize = 11.sp)
            Text(channel.name, color = Color.White, fontSize = 20.sp)
            Text(channel.description, color = Color(0xFFB9C7D5), fontSize = 13.sp)
        }
        Text("▶", color = gold, fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
private fun CategoryCard(category: Category, active: Boolean, onClick: () -> Unit) {
    Column(modifier = Modifier.width(150.dp).height(92.dp).background(if (active) gold else panel, RoundedCornerShape(10.dp)).border(if (active) 2.dp else 1.dp, if (active) gold else Color(0xFF30485E), RoundedCornerShape(10.dp)).padding(12.dp).onFocusChanged { if (it.isFocused) onClick() }.focusable(), verticalArrangement = Arrangement.SpaceBetween) {
        Text(category.icon, fontSize = 24.sp)
        Text(category.name, color = if (active) Color.Black else Color.White, fontSize = 15.sp)
        Text(category.subtitle, color = if (active) Color(0xFF26323D) else Color(0xFF9FB0BF), fontSize = 9.sp, maxLines = 1)
    }
}
