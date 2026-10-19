package com.havila.tv

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.systemBars())
        setContent { HavilaTvHome() }
    }
}

@Composable
private fun HavilaTvHome() {
    val context = LocalContext.current
    var selectedMenu by remember { mutableStateOf(0) }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }
    var clock by remember { mutableStateOf(currentTime()) }
    val menuRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        menuRequester.requestFocus()
        while (true) {
            clock = currentTime()
            kotlinx.coroutines.delay(30_000)
        }
    }
    BackHandler {
        if (selectedMenu == 0) showExitDialog = true else selectedMenu = 0
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(Bg)) {
        val sidebarWidth = (maxWidth * 0.17f).coerceIn(230.dp, 300.dp)
        val contentPadding = (maxWidth * 0.025f).coerceIn(20.dp, 40.dp)
        val topBarHeight = (maxHeight * 0.085f).coerceIn(54.dp, 72.dp)
        val heroHeight = (maxHeight * 0.30f).coerceIn(190.dp, 270.dp)
        Row(Modifier.fillMaxSize()) {
            SideBar(selectedMenu, menuRequester, { selectedMenu = it }, sidebarWidth)
            Column(Modifier.weight(1f).fillMaxHeight()) {
                TopActions(clock, topBarHeight, contentPadding, { selectedMenu = 1 }, { selectedMenu = 4 })
                Hero(Modifier.fillMaxWidth().height(heroHeight), contentPadding)
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    DashboardContent(context, menuRequester, selectedMenu, selectedCategoryIndex) { index -> selectedCategoryIndex = index; selectedMenu = 2 }
                }
            }
        }
    }
    if (showExitDialog) AlertDialog(onDismissRequest = { showExitDialog = false }, title = { Text("Sair da Havila TV", fontWeight = FontWeight.Bold) }, text = { Text("Deseja realmente sair da aplicação?") }, confirmButton = { TextButton(onClick = { showExitDialog = false; (context as? Activity)?.finish() }) { Text("Sair") } }, dismissButton = { TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") } })
}

private fun currentTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

@Composable private fun SideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit, width: Dp) {
    val items = listOf(Icons.Default.Home to "Início", Icons.Default.Tv to "Canais", Icons.Default.GridView to "Categorias", Icons.Default.FavoriteBorder to "Favoritos", Icons.Default.Settings to "Configurações")
    Column(Modifier.width(width).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)), horizontalAlignment = Alignment.CenterHorizontally) { Logo(); Spacer(Modifier.height(28.dp)); items.forEachIndexed { index, item -> MenuItem(index, item.first, item.second, selected == index, requester, onSelected) } }
}

@Composable private fun MenuItem(index: Int, icon: ImageVector, label: String, active: Boolean, requester: FocusRequester, onSelected: (Int) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.then(if (index == 0) Modifier.focusRequester(requester) else Modifier).fillMaxWidth().height(72.dp).padding(horizontal = 10.dp, vertical = 4.dp).clip(RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp)).background(if (active) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))).onFocusChanged { focused = it.isFocused }.border(if (focused) 2.dp else 0.dp, if (focused) FocusGold else Color.Transparent, RoundedCornerShape(18.dp)).focusable().clickable { onSelected(index) }.onKeyEvent { e -> if (e.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) { onSelected(index); true } else false }, verticalAlignment = Alignment.CenterVertically) { Icon(icon, label, tint = if (active) Color(0xFF10151C) else White.copy(alpha = .82f), modifier = Modifier.padding(start = 28.dp).width(30.dp)); Text(label, color = if (active) Color(0xFF10151C) else White.copy(alpha = .9f), fontSize = 18.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(start = 18.dp)) }
}

@Composable private fun Logo() {
    Column(
        Modifier.fillMaxWidth().padding(start = 16.dp, top = 18.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier.width(38.dp).height(30.dp)
                    .border(3.dp, GoldStart, RoundedCornerShape(8.dp))
            )
            Text(
                "HAVILA",
                color = White,
                fontSize = 31.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 6.dp)
            )
            Text(
                "TV",
                color = GoldStart,
                fontSize = 29.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Text(
            "Boa Informação. Bons Valores. Para Toda a Família.",
            color = Muted,
            fontSize = 8.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Composable private fun TopActions(time: String, height: Dp, horizontalPadding: Dp, onSearch: () -> Unit, onSettings: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(height).padding(horizontal = horizontalPadding), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) { Row(Modifier.clip(RoundedCornerShape(10.dp)).clickable { onSearch() }.focusable(), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(26.dp)); Text("Pesquisar", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 38.dp)) }; Row(Modifier.clip(RoundedCornerShape(10.dp)).clickable { onSettings() }.focusable(), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(27.dp)); Text("Configurações", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 42.dp)) }; Text(time, color = White, fontSize = 19.sp) }
}

@Composable private fun Hero(modifier: Modifier = Modifier, horizontalPadding: Dp) {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) { while (true) { kotlinx.coroutines.delay(4_000); currentIndex = (currentIndex + 1) % heroSlides.size } }
    val slide = heroSlides[currentIndex]
    Box(modifier.clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))) { Crossfade(targetState = slide, animationSpec = tween(900), label = "hero-background") { activeSlide -> Image(painter = painterResource(id = activeSlide.imageRes), contentDescription = activeSlide.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }; Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Bg.copy(alpha = .98f), Bg.copy(alpha = .78f), Bg.copy(alpha = .30f), Color.Transparent)))); Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Bg.copy(alpha = .48f))))); Column(Modifier.padding(start = horizontalPadding, top = 30.dp)) { Text("Bem-vindo à Havila TV", color = White, fontSize = 46.sp, fontWeight = FontWeight.ExtraBold); Text("Televisão digital para toda a família", color = Muted, fontSize = 25.sp, modifier = Modifier.padding(top = 2.dp)); Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = White.copy(alpha = .88f), fontSize = 12.sp, letterSpacing = 1.5.sp, modifier = Modifier.padding(top = 15.dp)) }; Text(slide.title, color = White.copy(alpha = .82f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.BottomStart).padding(start = horizontalPadding, bottom = 12.dp)); Row(Modifier.align(Alignment.BottomEnd).padding(end = horizontalPadding, bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) { heroSlides.forEachIndexed { index, _ -> Box(Modifier.width(if (index == currentIndex) 22.dp else 7.dp).height(7.dp).clip(RoundedCornerShape(50)).background(if (index == currentIndex) FocusGold else White.copy(alpha = .45f))) } } }
}

@Composable private fun DashboardContent(context: Context, menuRequester: FocusRequester, selectedMenu: Int, selectedCategoryIndex: Int, onCategorySelected: (Int) -> Unit) {
    when (selectedMenu) { 0 -> HomeContent(context, menuRequester, onCategorySelected); 1 -> ChannelContent(context, menuRequester); 2 -> CategoryContent(context, menuRequester, selectedCategoryIndex); 3 -> FavoritesContent(); 4 -> SettingsContent() }
}

@Composable private fun HomeContent(context: Context, menuRequester: FocusRequester, onCategorySelected: (Int) -> Unit) {
    val featuredChannels = remember { ChannelRepository.channels.filter { it.id in featuredMozambiqueIds && hasPlayableSource(it) } }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) { SectionTitle("PRINCIPAIS CANAIS DE MOÇAMBIQUE"); LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) { items(featuredChannels) { channel -> FeaturedChannelCard(channel, context, menuRequester) } }; Spacer(Modifier.height(18.dp)); SectionTitle("CATEGORIAS"); LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) { itemsIndexed(categoryDefinitions) { index, category -> CategoryCard(category, false, { onCategorySelected(index) }, menuRequester) } } }
}

@Composable private fun ChannelContent(context: Context, menuRequester: FocusRequester) {
    val channels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) { SectionTitle("TODOS OS CANAIS"); LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { items(channels) { channel -> ChannelRowCard(channel, context, menuRequester) } } }
}

@Composable private fun CategoryContent(context: Context, menuRequester: FocusRequester, initialCategoryIndex: Int) {
    var selectedCategoryIndex by remember(initialCategoryIndex) { mutableStateOf(initialCategoryIndex.coerceIn(0, categoryDefinitions.lastIndex)) }
    var selectedSubcategory by remember(selectedCategoryIndex) { mutableStateOf(categoryDefinitions[selectedCategoryIndex].subcategories.first()) }
    val category = categoryDefinitions[selectedCategoryIndex]
    val channels = remember(category.name, selectedSubcategory) { channelsFor(category.name, selectedSubcategory) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) { SectionTitle("CATEGORIAS"); LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) { itemsIndexed(categoryDefinitions) { index, item -> CategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester) } }; Spacer(Modifier.height(16.dp)); SectionTitle("SUB-CATEGORIAS"); LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 24.dp)) { items(category.subcategories) { sub -> SubcategoryChip(sub, sub == selectedSubcategory) { selectedSubcategory = sub } } }; Spacer(Modifier.height(16.dp)); Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("CANAIS"); Text("  ${category.name} / $selectedSubcategory", color = Muted, fontSize = 14.sp) }; Spacer(Modifier.height(8.dp)); if (channels.isEmpty()) Box(Modifier.fillMaxWidth().height(82.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(Surface), contentAlignment = Alignment.CenterStart) { Text("Nenhum canal com fonte disponível nesta subcategoria.", color = Muted, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 20.dp)) } else LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { items(channels) { channel -> ChannelRowCard(channel, context, menuRequester) } } }
}

@Composable private fun FavoritesContent() { Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) { SectionTitle("FAVORITOS"); Spacer(Modifier.height(18.dp)); Text("Os canais favoritos poderão ser adicionados a partir da lista de canais.", color = Muted, fontSize = 16.sp) } }
@Composable private fun SettingsContent() { Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) { SectionTitle("CONFIGURAÇÕES"); Spacer(Modifier.height(18.dp)); Text("Configurações da Havila TV", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold); Text("A reprodução utiliza as fontes de transmissão definidas no catálogo.", color = Muted, fontSize = 15.sp, modifier = Modifier.padding(top = 8.dp)) } }
@Composable private fun SectionTitle(text: String) { Text(text, color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp) }

@Composable private fun CategoryCard(category: CategoryDef, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(270.dp).height(62.dp).scale(if (focused) 1.04f else 1f).clip(RoundedCornerShape(12.dp)).background(if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else category.art).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.onKeyEvent { e -> if (e.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) { onSelect(); true } else false }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) { Icon(category.icon, category.name, tint = if (selected) Color(0xFF10151C) else White, modifier = Modifier.padding(start = 18.dp).width(30.dp)); Text(category.name, color = if (selected) Color(0xFF10151C) else White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp, end = 12.dp)) }
}

@Composable private fun SubcategoryChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val chipBackground = if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Surface, Surface))
    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(chipBackground).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(20.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.onKeyEvent { e -> if (e.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) { onSelect(); true } else false }.padding(horizontal = 18.dp, vertical = 9.dp)) { Text(name, color = if (selected) Color(0xFF10151C) else White, fontSize = 14.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) }
}

@Composable private fun FeaturedChannelCard(channel: Channel, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val logoResource = localLogoResource(channel.id)
    Row(Modifier.width(235.dp).height(76.dp).clip(RoundedCornerShape(12.dp)).background(Surface).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasPlayableSource(channel)) { openChannel(context, channel) }.onKeyEvent { e -> if (e.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) { if (hasPlayableSource(channel)) openChannel(context, channel); true } else false }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) { Box(Modifier.width(88.dp).fillMaxHeight().padding(6.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) { if (logoResource != null) Image(painter = painterResource(id = logoResource), contentDescription = channel.name, modifier = Modifier.fillMaxSize().padding(7.dp), contentScale = ContentScale.Fit) else Text(channel.name, color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center)) }; Column(Modifier.padding(start = 10.dp).weight(1f)) { Text(channel.name, color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1); Text("AO VIVO", color = LiveRed, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp)) } }
}

@Composable private fun ChannelRowCard(channel: Channel, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val logoResource = localLogoResource(channel.id)
    Row(Modifier.fillMaxWidth().height(82.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(Surface).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasPlayableSource(channel)) { openChannel(context, channel) }.onKeyEvent { e -> if (e.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) { if (hasPlayableSource(channel)) openChannel(context, channel); true } else false }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) { Box(Modifier.width(145.dp).fillMaxHeight().padding(7.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) { if (logoResource != null) Image(painter = painterResource(id = logoResource), contentDescription = channel.name, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit) else Text(channel.name, color = White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.Center)) }; Column(Modifier.padding(start = 16.dp).weight(1f)) { Text(channel.name, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold); Text(channel.description, color = Muted, fontSize = 13.sp, maxLines = 1, modifier = Modifier.padding(top = 3.dp)) }; Text("AO VIVO", color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 22.dp)) }
}

private fun channelsFor(category: String, subcategory: String): List<Channel> = ChannelRepository.channels.filter { it.category == category && it.subcategory == subcategory && hasPlayableSource(it) }
private fun hasPlayableSource(channel: Channel): Boolean = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()
private fun openChannel(context: Context, channel: Channel) { context.startActivity(Intent(context, PlayerActivity::class.java).apply { putExtra("channelId", channel.id); putExtra("channelName", channel.name) }) }
