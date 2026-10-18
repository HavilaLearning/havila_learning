package com.havila.tv

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Bg = Color(0xFF070B12)
private val Surface = Color(0xFF111923)
private val CardBorder = Color(0xFF283646)
private val White = Color(0xFFF5F7FA)
private val Muted = Color(0xFF9AA8B7)
private val GoldStart = Color(0xFFF2C14E)
private val GoldEnd = Color(0xFFD99A22)
private val FocusGold = Color(0xFFFFD76A)
private val LiveRed = Color(0xFFFF5C5C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
private fun HavilaTvHome() {
    val context = LocalContext.current
    var selectedMenu by remember { mutableStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }
    var clock by remember { mutableStateOf(currentTime()) }
    val menuRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        menuRequester.requestFocus()
        while (true) { clock = currentTime(); kotlinx.coroutines.delay(30_000) }
    }
    BackHandler { if (selectedMenu == 0) showExitDialog = true else selectedMenu = 0 }

    Box(Modifier.fillMaxSize().background(Bg)) {
        Row(Modifier.fillMaxSize()) {
            SideBar(selectedMenu, menuRequester) { selectedMenu = it }
            Column(Modifier.fillMaxSize().padding(end = 28.dp)) {
                TopActions(clock)
                Hero()
                DashboardContent(context, menuRequester, selectedMenu)
            }
        }
    }
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Sair da Havila TV", fontWeight = FontWeight.Bold) },
            text = { Text("Deseja realmente sair da aplicação?") },
            confirmButton = { TextButton(onClick = { showExitDialog = false; (context as? Activity)?.finish() }) { Text("Sair") } },
            dismissButton = { TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") } }
        )
    }
}

private fun currentTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

@Composable
private fun SideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit) {
    val items = listOf(Icons.Default.Home to "Início", Icons.Default.Tv to "Canais", Icons.Default.GridView to "Categorias", Icons.Default.FavoriteBorder to "Favoritos", Icons.Default.Settings to "Configurações")
    Column(Modifier.width(290.dp).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)), horizontalAlignment = Alignment.CenterHorizontally) {
        Logo(); Spacer(Modifier.height(28.dp)); items.forEachIndexed { index, item -> MenuItem(index, item.first, item.second, selected == index, requester, onSelected) }
    }
}

@Composable
private fun MenuItem(index: Int, icon: ImageVector, label: String, active: Boolean, requester: FocusRequester, onSelected: (Int) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.then(if (index == 0) Modifier.focusRequester(requester) else Modifier).fillMaxWidth().height(72.dp).padding(horizontal = 10.dp, vertical = 4.dp).clip(RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp)).background(if (active) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))).onFocusChanged { focused = it.isFocused }.border(if (focused) 2.dp else 0.dp, if (focused) FocusGold else Color.Transparent, RoundedCornerShape(18.dp)).focusable().clickable { onSelected(index) }, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, label, tint = if (active) Color(0xFF10151C) else White.copy(alpha = .82f), modifier = Modifier.padding(start = 28.dp).width(30.dp)); Text(label, color = if (active) Color(0xFF10151C) else White.copy(alpha = .9f), fontSize = 18.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(start = 18.dp))
    }
}

@Composable
private fun Logo() {
    Column(Modifier.fillMaxWidth().padding(start = 18.dp, top = 22.dp, end = 18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.width(48.dp).height(38.dp).border(4.dp, GoldStart, RoundedCornerShape(10.dp))); Text("HAVILA", color = White, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 7.dp)); Text("TV", color = GoldStart, fontSize = 35.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 5.dp)) }
        Text("Boa Informação. Bons Valores. Para Toda a Família.", color = Muted, fontSize = 9.sp, maxLines = 1, modifier = Modifier.padding(top = 5.dp))
    }
}

@Composable
private fun TopActions(time: String) {
    Row(Modifier.fillMaxWidth().height(66.dp).padding(horizontal = 10.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(26.dp)); Text("Pesquisar", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 38.dp)); Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(27.dp)); Text("Configurações", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 42.dp)); Text(time, color = White, fontSize = 19.sp)
    }
}

@Composable
private fun Hero() {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) { while (true) { kotlinx.coroutines.delay(4_000); currentIndex = (currentIndex + 1) % heroSlides.size } }
    val slide = heroSlides[currentIndex]
    Box(Modifier.fillMaxWidth().height(235.dp).clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))) {
        Crossfade(targetState = slide, animationSpec = tween(900), label = "hero-background") { activeSlide -> Image(painterResource(activeSlide.imageRes), activeSlide.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Bg.copy(alpha = .98f), Bg.copy(alpha = .78f), Bg.copy(alpha = .30f), Color.Transparent))))
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Bg.copy(alpha = .48f)))))
        Column(Modifier.padding(start = 42.dp, top = 30.dp)) { Text("Bem-vindo à Havila TV", color = White, fontSize = 46.sp, fontWeight = FontWeight.ExtraBold); Text("Televisão digital para toda a família", color = Muted, fontSize = 25.sp, modifier = Modifier.padding(top = 2.dp)); Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = White.copy(alpha = .88f), fontSize = 12.sp, letterSpacing = 1.5.sp, modifier = Modifier.padding(top = 15.dp)) }
        Text(slide.title, color = White.copy(alpha = .82f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.BottomStart).padding(start = 42.dp, bottom = 12.dp))
        Row(Modifier.align(Alignment.BottomEnd).padding(end = 28.dp, bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) { heroSlides.forEachIndexed { index, _ -> Box(Modifier.width(if (index == currentIndex) 22.dp else 7.dp).height(7.dp).clip(RoundedCornerShape(50)).background(if (index == currentIndex) FocusGold else White.copy(alpha = .45f))) } }
    }
}

@Composable
private fun DashboardContent(context: Context, menuRequester: FocusRequester, selectedMenu: Int) {
    when (selectedMenu) {
        0 -> HomeContent(context, menuRequester)
        1 -> ChannelContent(context, menuRequester)
        2 -> CategoryContent(context, menuRequester)
        else -> HomeContent(context, menuRequester)
    }
}

@Composable
private fun HomeContent(context: Context, menuRequester: FocusRequester) {
    val featuredChannels = remember { ChannelRepository.channels.filter { it.id in featuredMozambiqueIds && hasPlayableSource(it) } }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitle("PRINCIPAIS CANAIS DE MOÇAMBIQUE")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(featuredChannels) { channel -> FeaturedChannelCard(channel, context, menuRequester) }
        }
        Spacer(Modifier.height(18.dp)); SectionTitle("CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categoryDefinitions) { index, category -> CategoryCard(category, false, { openCategory(context, category.name) }, menuRequester) }
        }
    }
}

@Composable
private fun ChannelContent(context: Context, menuRequester: FocusRequester) {
    val channels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitle("TODOS OS CANAIS")
        LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(channels) { channel -> ChannelRowCard(channel, null, context, menuRequester) }
        }
    }
}

@Composable
private fun CategoryContent(context: Context, menuRequester: FocusRequester) {
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var selectedSubcategory by remember { mutableStateOf(categoryDefinitions[0].subcategories.first()) }
    val category = categoryDefinitions[selectedCategoryIndex]
    val channels = remember(category.name, selectedSubcategory) { channelsFor(category.name, selectedSubcategory) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitle("CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categoryDefinitions) { index, item -> CategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester) }
        }
        Spacer(Modifier.height(16.dp)); SectionTitle("SUB-CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(category.subcategories) { sub -> SubcategoryChip(sub, sub == selectedSubcategory) { selectedSubcategory = sub } }
        }
        Spacer(Modifier.height(16.dp)); Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("CANAIS"); Text("  ${category.name} / $selectedSubcategory", color = Muted, fontSize = 14.sp) }
        Spacer(Modifier.height(8.dp))
        if (channels.isEmpty()) {
            Box(Modifier.fillMaxWidth().height(82.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(Surface), contentAlignment = Alignment.CenterStart) { Text("Nenhum canal com fonte disponível nesta subcategoria.", color = Muted, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 20.dp)) }
        } else {
            LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { items(channels) { channel -> ChannelRowCard(channel, null, context, menuRequester) } }
        }
    }
}

@Composable
private fun SectionTitle(text: String) { Text(text, color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp) }

private fun openCategory(context: Context, categoryName: String) {
    // Home keeps the category cards visible; selecting a category is handled by the Categories menu.
}

@Composable
private fun CategoryCard(category: CategoryDef, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(270.dp).height(62.dp).scale(if (focused) 1.04f else 1f).clip(RoundedCornerShape(12.dp)).background(if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else category.art).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Icon(category.icon, category.name, tint = if (selected) Color(0xFF10151C) else White, modifier = Modifier.padding(start = 18.dp).width(30.dp)); Text(category.name, color = if (selected) Color(0xFF10151C) else White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp, end = 12.dp))
    }
}

@Composable
private fun SubcategoryChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val chipBackground: Brush = if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Surface, Surface))
    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(chipBackground).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(20.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.padding(horizontal = 18.dp, vertical = 9.dp)) { Text(name, color = if (selected) Color(0xFF10151C) else White, fontSize = 14.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) }
}

@Composable
private fun FeaturedChannelCard(channel: Channel, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val logoResource = localLogoResource(channel.id)
    Row(Modifier.width(235.dp).height(76.dp).clip(RoundedCornerShape(12.dp)).background(Surface).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasPlayableSource(channel)) { openChannel(context, channel) }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(88.dp).fillMaxHeight().padding(6.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) { if (logoResource != null) Image(painterResource(logoResource), channel.name, modifier = Modifier.fillMaxSize().padding(7.dp), contentScale = ContentScale.Fit) else Text(channel.name, color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center)) }
        Column(Modifier.padding(start = 10.dp).weight(1f)) { Text(channel.name, color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1); Text("AO VIVO", color = LiveRed, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp)) }
    }
}

@Composable
private fun ChannelRowCard(channel: Channel, requester: FocusRequester?, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val logoResource = localLogoResource(channel.id)
    Row((if (requester != null) Modifier.focusRequester(requester) else Modifier).fillMaxWidth().height(82.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(Surface).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasPlayableSource(channel)) { openChannel(context, channel) }.focusProperties { if (requester != null) up = requester; left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(145.dp).fillMaxHeight().padding(7.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) { if (logoResource != null) Image(painterResource(logoResource), channel.name, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit) else Text(channel.name, color = White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.Center)) }
        Column(Modifier.padding(start = 16.dp).weight(1f)) { Text(channel.name, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold); Text(channel.description, color = Muted, fontSize = 13.sp, maxLines = 1, modifier = Modifier.padding(top = 3.dp)) }
        Text("AO VIVO", color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 22.dp))
    }
}

private fun channelsFor(category: String, subcategory: String): List<Channel> = ChannelRepository.channels.filter { it.category == category && it.subcategory == subcategory && hasPlayableSource(it) }

private fun hasPlayableSource(channel: Channel): Boolean = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()

private fun openChannel(context: Context, channel: Channel) {
    context.startActivity(Intent(context, PlayerActivity::class.java).apply { putExtra("channelId", channel.id); putExtra("channelName", channel.name) })
}
