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
import androidx.compose.foundation.focusable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.clip
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
import coil.compose.AsyncImage
import kotlinx.coroutines.android.awaitFrame
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

private const val PREFS = "havila_tv_prefs"
private const val FAVORITES_KEY = "favorite_channel_ids"

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
    var showSearch by remember { mutableStateOf(false) }
    var clock by remember { mutableStateOf(currentTime()) }
    val menuRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        awaitFrame()
        runCatching { menuRequester.requestFocus() }
        while (true) { clock = currentTime(); kotlinx.coroutines.delay(30_000) }
    }

    BackHandler {
        when {
            showSearch -> showSearch = false
            selectedMenu == 0 -> showExitDialog = true
            else -> selectedMenu = 0
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(Bg)) {
        val compact = maxWidth < 1100.dp
        val sidebarWidth = if (compact) 190.dp else (maxWidth * 0.15f).coerceIn(210.dp, 250.dp)
        val contentPadding = if (compact) 16.dp else (maxWidth * 0.018f).coerceIn(20.dp, 32.dp)
        val topBarHeight = (maxHeight * 0.07f).coerceIn(46.dp, 60.dp)
        val heroHeight = (maxHeight * 0.24f).coerceIn(145.dp, 220.dp)

        Row(Modifier.fillMaxSize()) {
            SideBar(selectedMenu, menuRequester, { selectedMenu = it }, sidebarWidth)
            Column(Modifier.weight(1f).fillMaxHeight()) {
                TopActions(clock, topBarHeight, contentPadding, { showSearch = true }, { selectedMenu = 4 })
                if (selectedMenu == 0) Hero(Modifier.fillMaxWidth().height(heroHeight), contentPadding) else Spacer(Modifier.height(6.dp))
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    DashboardContent(context, menuRequester, selectedMenu, selectedCategoryIndex) { index ->
                        selectedCategoryIndex = index
                        selectedMenu = 2
                    }
                }
            }
        }
    }

    if (showSearch) SearchDialog(context) { showSearch = false }

    if (showExitDialog) AlertDialog(
        onDismissRequest = { showExitDialog = false },
        title = { Text("Sair da Havila TV", fontWeight = FontWeight.Bold) },
        text = { Text("Deseja realmente sair da aplicação?") },
        confirmButton = { TextButton(onClick = { showExitDialog = false; (context as? Activity)?.finish() }) { Text("Sair") } },
        dismissButton = { TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") } }
    )
}

private fun currentTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

private fun Modifier.tvAction(onSelect: () -> Unit): Modifier = this.focusable().onKeyEvent { event ->
    val key = event.nativeKeyEvent
    if (key.action == android.view.KeyEvent.ACTION_DOWN && key.repeatCount == 0 &&
        (key.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || key.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) {
        onSelect(); true
    } else false
}

@Composable
private fun SideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit, width: Dp) {
    val items = listOf(
        Icons.Default.Home to "Início",
        Icons.Default.Tv to "Canais",
        Icons.Default.GridView to "Categorias",
        Icons.Default.FavoriteBorder to "Favoritos",
        Icons.Default.Settings to "Configurações"
    )
    Column(Modifier.width(width).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)), horizontalAlignment = Alignment.CenterHorizontally) {
        Logo(); Spacer(Modifier.height(16.dp))
        items.forEachIndexed { index, item -> MenuItem(index, item.first, item.second, selected == index, requester, onSelected) }
    }
}

@Composable
private fun MenuItem(index: Int, icon: ImageVector, label: String, active: Boolean, requester: FocusRequester, onSelected: (Int) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.then(if (index == 0) Modifier.focusRequester(requester) else Modifier).fillMaxWidth().height(60.dp).padding(horizontal = 6.dp, vertical = 2.dp).clip(RoundedCornerShape(0.dp, 14.dp, 14.dp, 0.dp)).background(if (active) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))).onFocusChanged { focused = it.isFocused }.border(if (focused) 2.dp else 0.dp, if (focused) FocusGold else Color.Transparent, RoundedCornerShape(14.dp)).tvAction { onSelected(index) }, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, label, tint = if (active) Color(0xFF10151C) else White.copy(alpha = .82f), modifier = Modifier.padding(start = 16.dp).width(26.dp))
        Text(label, color = if (active) Color(0xFF10151C) else White.copy(alpha = .9f), fontSize = 15.sp, maxLines = 1, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(start = 11.dp, end = 8.dp))
    }
}

@Composable private fun Logo() { Column(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) { Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) { Box(Modifier.width(28.dp).height(23.dp).border(2.dp, GoldStart, RoundedCornerShape(6.dp))); Text("HAVILA", color = White, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 4.dp)); Text("TV", color = GoldStart, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 3.dp)) }; Text("Boa Informação. Bons Valores. Para Toda a Família.", color = Muted, fontSize = 6.sp, maxLines = 1, modifier = Modifier.padding(top = 3.dp)) } }

@Composable
private fun TopActions(time: String, height: Dp, horizontalPadding: Dp, onSearch: () -> Unit, onSettings: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(height).padding(horizontal = horizontalPadding), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.tvAction(onSearch), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(22.dp)); Text("Pesquisar", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 18.dp)) }
        Row(Modifier.tvAction(onSettings), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(23.dp)); Text("Configurações", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 20.dp)) }
        Text(time, color = White, fontSize = 16.sp)
    }
}

@Composable
private fun Hero(modifier: Modifier = Modifier, horizontalPadding: Dp) {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) { while (true) { kotlinx.coroutines.delay(4_000); currentIndex = (currentIndex + 1) % heroSlides.size } }
    val slide = heroSlides[currentIndex]
    Box(modifier.clip(RoundedCornerShape(0.dp, 0.dp, 14.dp, 14.dp))) {
        Crossfade(targetState = slide, animationSpec = tween(900), label = "hero-background") { activeSlide -> Image(painter = painterResource(id = activeSlide.imageRes), contentDescription = activeSlide.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Bg.copy(alpha = .98f), Bg.copy(alpha = .78f), Bg.copy(alpha = .30f), Color.Transparent))))
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Bg.copy(alpha = .48f)))))
        Column(Modifier.padding(start = horizontalPadding, top = 20.dp, end = horizontalPadding)) { Text("Bem-vindo à Havila TV", color = White, fontSize = 32.sp, maxLines = 1, fontWeight = FontWeight.ExtraBold); Text("Televisão digital para toda a família", color = Muted, fontSize = 18.sp, maxLines = 1, modifier = Modifier.padding(top = 2.dp)); Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = White.copy(alpha = .88f), fontSize = 9.sp, maxLines = 1, modifier = Modifier.padding(top = 9.dp)) }
        Text(slide.title, color = White.copy(alpha = .82f), fontSize = 9.sp, maxLines = 1, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.BottomStart).padding(start = horizontalPadding, bottom = 8.dp))
        Row(Modifier.align(Alignment.BottomEnd).padding(end = horizontalPadding, bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) { heroSlides.forEachIndexed { index, _ -> Box(Modifier.width(if (index == currentIndex) 16.dp else 5.dp).height(5.dp).clip(RoundedCornerShape(50)).background(if (index == currentIndex) FocusGold else White.copy(alpha = .45f))) } }
    }
}

@Composable
private fun DashboardContent(context: Context, menuRequester: FocusRequester, selectedMenu: Int, selectedCategoryIndex: Int, onCategorySelected: (Int) -> Unit) {
    when (selectedMenu) { 0 -> HomeContent(context, menuRequester, onCategorySelected); 1 -> ChannelContent(context, menuRequester); 2 -> CategoryContent(context, menuRequester, selectedCategoryIndex); 3 -> FavoritesContent(context); 4 -> SettingsContent() }
}

@Composable
private fun HomeContent(context: Context, menuRequester: FocusRequester, onCategorySelected: (Int) -> Unit) {
    val featuredChannels = remember { ChannelRepository.channels.filter { it.id in featuredMozambiqueIds && hasPlayableSource(it) } }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp)) {
        SectionTitle("PRINCIPAIS CANAIS DE MOÇAMBIQUE")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 20.dp)) { items(featuredChannels, key = { it.id }) { channel -> FeaturedChannelCard(channel, context, menuRequester) } }
        Spacer(Modifier.height(12.dp)); SectionTitle("CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 20.dp)) { itemsIndexed(categoryDefinitions, key = { _, category -> category.name }) { index, category -> CategoryCard(category, false, { onCategorySelected(index) }, menuRequester) } }
    }
}

@Composable
private fun ChannelContent(context: Context, menuRequester: FocusRequester) {
    val channels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }
    val firstChannelRequester = remember { FocusRequester() }
    LaunchedEffect(channels) { awaitFrame(); awaitFrame(); runCatching { if (channels.isNotEmpty()) firstChannelRequester.requestFocus() } }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("TODOS OS CANAIS"); Spacer(Modifier.width(10.dp)); Text("${channels.size} canais", color = Muted, fontSize = 12.sp) }
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 190.dp), modifier = Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(top = 2.dp, bottom = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) { items(channels, key = { it.id }) { channel -> ChannelGridCard(channel, context, firstChannelRequester, channel.id == channels.firstOrNull()?.id) } }
    }
}

@Composable
private fun CategoryContent(context: Context, menuRequester: FocusRequester, initialCategoryIndex: Int) {
    var selectedCategoryIndex by remember(initialCategoryIndex) { mutableStateOf(initialCategoryIndex.coerceIn(0, categoryDefinitions.lastIndex)) }
    var selectedSubcategory by remember(selectedCategoryIndex) { mutableStateOf(categoryDefinitions[selectedCategoryIndex].subcategories.first()) }
    val category = categoryDefinitions[selectedCategoryIndex]
    val channels = remember(category.name, selectedSubcategory) { channelsFor(category.name, selectedSubcategory) }
    val firstChannelRequester = remember(category.name, selectedSubcategory) { FocusRequester() }
    LaunchedEffect(category.name, selectedSubcategory, channels) { awaitFrame(); awaitFrame(); runCatching { if (channels.isNotEmpty()) firstChannelRequester.requestFocus() } }

    var backgroundIndex by remember(category.name) { mutableStateOf(0) }
    val backgrounds = categoryBackgrounds(category)
    LaunchedEffect(category.name) { while (true) { kotlinx.coroutines.delay(5_000); backgroundIndex = (backgroundIndex + 1) % backgrounds.size } }

    Box(Modifier.fillMaxSize().clip(RoundedCornerShape(14.dp))) {
        Crossfade(targetState = backgrounds[backgroundIndex], animationSpec = tween(1100), label = "category-background") { res -> Image(painter = painterResource(id = res), contentDescription = "${category.name} background", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Bg.copy(alpha = .70f), Bg.copy(alpha = .55f), Bg.copy(alpha = .82f)))))
        Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp, bottom = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SectionTitle(category.name.uppercase()); Spacer(Modifier.width(10.dp)); Text("Escolha uma área", color = White.copy(alpha = .78f), fontSize = 12.sp); Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) { backgrounds.forEachIndexed { index, _ -> Box(Modifier.width(if (index == backgroundIndex) 16.dp else 5.dp).height(5.dp).clip(RoundedCornerShape(50)).background(if (index == backgroundIndex) FocusGold else White.copy(alpha = .45f))) } }
            }
            LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 20.dp)) { itemsIndexed(categoryDefinitions, key = { _, item -> item.name }) { index, item -> CategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester) } }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("SUB-CATEGORIAS"); Spacer(Modifier.width(10.dp)); Text(category.name, color = FocusGold, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(end = 20.dp)) { items(category.subcategories, key = { it }) { sub -> SubcategoryChip(displaySubcategory(sub), sub == selectedSubcategory) { selectedSubcategory = sub } } }
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("CANAIS"); Spacer(Modifier.width(10.dp)); Text("${category.name} • ${displaySubcategory(selectedSubcategory)}", color = White.copy(alpha = .80f), fontSize = 12.sp, maxLines = 1); Spacer(Modifier.weight(1f)); Text("${channels.size} disponíveis", color = White.copy(alpha = .72f), fontSize = 11.sp) }
            Spacer(Modifier.height(2.dp))
            if (channels.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(70.dp).padding(end = 20.dp).clip(RoundedCornerShape(12.dp)).background(Surface.copy(alpha = .88f)), contentAlignment = Alignment.CenterStart) { Text("Nenhum canal com fonte disponível nesta subcategoria.", color = White.copy(alpha = .78f), fontSize = 13.sp, modifier = Modifier.padding(horizontal = 16.dp), maxLines = 2) }
            } else {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 190.dp), modifier = Modifier.fillMaxWidth().weight(1f).focusGroup(), contentPadding = PaddingValues(top = 2.dp, bottom = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) { items(channels, key = { it.id }) { channel -> ChannelGridCard(channel, context, firstChannelRequester, channel.id == channels.firstOrNull()?.id) } }
            }
        }
    }
}

@Composable private fun SectionTitle(text: String) { Text(text, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 7.dp)) }

@Composable
private fun CategoryCard(category: CategoryDefinition, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(190.dp).height(62.dp).clip(RoundedCornerShape(12.dp)).then(if (selected) Modifier.background(Brush.horizontalGradient(listOf(GoldStart, GoldEnd))) else Modifier.background(category.art)).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.tvAction(onSelect).focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = category.logoRes), contentDescription = category.name, modifier = Modifier.width(76.dp).height(48.dp).padding(start = 8.dp), contentScale = ContentScale.Fit)
        Column(Modifier.padding(start = 9.dp, end = 8.dp)) { Text(category.name, color = if (selected) Color(0xFF10151C) else White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 2); Text("${category.subcategories.size} secções", color = if (selected) Color(0xFF10151C).copy(alpha = .72f) else White.copy(alpha = .68f), fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp)) }
    }
}

@Composable private fun SubcategoryChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.height(38.dp).clip(RoundedCornerShape(19.dp)).then(if (selected) Modifier.background(Brush.horizontalGradient(listOf(GoldStart, GoldEnd))) else Modifier.background(Surface.copy(alpha = .88f))).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(19.dp)).onFocusChanged { focused = it.isFocused }.tvAction(onSelect).padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) { Text(name, color = if (selected) Color(0xFF10151C) else White, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) }
}

@Composable
private fun FeaturedChannelCard(channel: Channel, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val isFavorite = rememberFavoriteState(channel.id)
    Row(Modifier.width(215.dp).height(82.dp).clip(RoundedCornerShape(12.dp)).background(Surface.copy(alpha = .92f)).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.tvAction { if (hasPlayableSource(channel)) openChannel(context, channel) }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        ChannelLogo(channel, Modifier.width(86.dp).fillMaxHeight().padding(6.dp))
        Column(Modifier.padding(start = 8.dp, end = 2.dp).weight(1f)) { Text(channel.name, color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 2); Text("AO VIVO", color = LiveRed, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 3.dp)) }
        IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(38.dp)) { Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorito", tint = if (isFavorite) LiveRed else White.copy(alpha = .72f)) }
    }
}

@Composable
private fun ChannelGridCard(channel: Channel, context: Context, firstRequester: FocusRequester? = null, isFirst: Boolean = false) {
    var focused by remember { mutableStateOf(false) }
    val isFavorite = rememberFavoriteState(channel.id)
    Column(Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(14.dp)).background(if (focused) Color(0xFF172333).copy(alpha = .96f) else Surface.copy(alpha = .92f)).border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(14.dp)).onFocusChanged { focused = it.isFocused }.then(if (isFirst && firstRequester != null) Modifier.focusRequester(firstRequester) else Modifier).tvAction { if (hasPlayableSource(channel)) openChannel(context, channel) }.padding(7.dp)) {
        Row(Modifier.fillMaxWidth().height(88.dp), verticalAlignment = Alignment.CenterVertically) {
            ChannelLogo(channel, Modifier.weight(1f).fillMaxHeight())
            IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(40.dp)) { Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorito", tint = if (isFavorite) LiveRed else White.copy(alpha = .72f)) }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text(channel.name, color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1); Text(channel.description.ifBlank { displaySubcategory(channel.subcategory) }, color = Muted, fontSize = 9.sp, maxLines = 1, modifier = Modifier.padding(top = 2.dp)) }
            Text("LIVE", color = LiveRed, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 5.dp))
        }
    }
}

@Composable private fun ChannelLogo(channel: Channel, modifier: Modifier = Modifier) {
    var failed by remember(channel.id, channel.logoUrl) { mutableStateOf(false) }
    val local = localLogoResource(channel.id)
    Box(modifier.clip(RoundedCornerShape(9.dp)).background(Color(0xFF151E2C)), contentAlignment = Alignment.Center) {
        when {
            local != null -> Image(painter = painterResource(id = local), contentDescription = channel.name, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit)
            !channel.logoUrl.isNullOrBlank() && !failed -> AsyncImage(model = channel.logoUrl, contentDescription = channel.name, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit, onError = { failed = true })
            else -> Text(channelInitials(channel.name), color = White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun SearchDialog(context: Context, onDismiss: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val allChannels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }
    val results = remember(query, allChannels) {
        if (query.isBlank()) allChannels else allChannels.filter { it.name.contains(query.trim(), true) || it.category.contains(query.trim(), true) || it.subcategory.contains(query.trim(), true) }
    }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Pesquisar canais", fontWeight = FontWeight.Bold) }, text = {
        Column(Modifier.fillMaxWidth().heightIn(max = 520.dp)) {
            OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = { Text("Nome do canal") }, leadingIcon = { Icon(Icons.Default.Search, null) })
            Spacer(Modifier.height(10.dp)); Text("${results.size} resultados", color = Muted, fontSize = 11.sp); Spacer(Modifier.height(6.dp))
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 180.dp), modifier = Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { items(results, key = { it.id }) { channel -> SearchResultCard(channel) { openChannel(context, channel); onDismiss() } } }
        }
    }, confirmButton = { TextButton(onClick = onDismiss) { Text("Fechar") } })
}

@Composable private fun SearchResultCard(channel: Channel, onOpen: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth().height(62.dp).clip(RoundedCornerShape(10.dp)).background(Surface).border(if (focused) 2.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(10.dp)).onFocusChanged { focused = it.isFocused }.tvAction(onOpen), verticalAlignment = Alignment.CenterVertically) {
        ChannelLogo(channel, Modifier.width(60.dp).fillMaxHeight().padding(5.dp)); Text(channel.name, color = White, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 2, modifier = Modifier.padding(horizontal = 7.dp))
    }
}

@Composable
private fun FavoritesContent(context: Context) {
    val version = FavoritesStore.version
    val ids = remember(version) { favoriteIds(context) }
    val channels = remember(version) { ChannelRepository.channels.filter { it.id in ids && hasPlayableSource(it) } }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) { SectionTitle("FAVORITOS"); Spacer(Modifier.width(10.dp)); Text("${channels.size} canais", color = Muted, fontSize = 12.sp) }
        if (channels.isEmpty()) {
            Text("Ainda não adicionou canais aos favoritos.", color = Muted, fontSize = 13.sp, modifier = Modifier.padding(top = 12.dp))
        } else {
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 190.dp), modifier = Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(top = 2.dp, bottom = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) { items(channels, key = { it.id }) { channel -> ChannelGridCard(channel, context) } }
        }
    }
}

@Composable
private fun SettingsContent() {
    var autoplay by remember { mutableStateOf(true) }
    var showLiveBadge by remember { mutableStateOf(true) }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp, end = 20.dp)) {
        SectionTitle("CONFIGURAÇÕES")
        SettingsRow("Reprodução automática", "Iniciar o canal imediatamente ao selecionar.", autoplay) { autoplay = it }
        SettingsRow("Indicador AO VIVO", "Mostrar o estado LIVE nos cartões dos canais.", showLiveBadge) { showLiveBadge = it }
    }
}

@Composable
private fun SettingsRow(title: String, description: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp).clip(RoundedCornerShape(12.dp)).background(Surface).padding(horizontal = 14.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) { Text(title, color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold); Text(description, color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 3.dp)) }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}

private object FavoritesStore {
    var version by mutableIntStateOf(0)
    fun toggle(context: Context, channelId: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val current = prefs.getStringSet(FAVORITES_KEY, emptySet())?.toMutableSet() ?: mutableSetOf()
        if (!current.add(channelId)) current.remove(channelId)
        prefs.edit().putStringSet(FAVORITES_KEY, current).apply()
        version++
    }
}

@Composable
private fun rememberFavoriteState(channelId: String): Boolean {
    val context = LocalContext.current
    val version = FavoritesStore.version
    return remember(version, channelId) { isFavorite(context, channelId) }
}

private fun favoriteIds(context: Context): Set<String> = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getStringSet(FAVORITES_KEY, emptySet())?.toSet() ?: emptySet()
private fun isFavorite(context: Context, channelId: String): Boolean = channelId in favoriteIds(context)

private fun channelInitials(name: String): String = name.split(Regex("\\s+")).filter { it.isNotBlank() }.take(3).joinToString("") { it.first().uppercase() }.take(4)

private fun displaySubcategory(value: String): String = when (value) {
    "News" -> "Notícias"; "Weather" -> "Meteorologia"; "Business" -> "Negócios"; "Legislative" -> "Legislativo"; "Public" -> "Serviço Público"; "General" -> "Geral"; "Sports" -> "Desporto"; "Auto" -> "Automobilismo"; "Outdoor" -> "Ar Livre"; "Religious" -> "Religioso"; "Movies" -> "Filmes"; "Series" -> "Séries"; "Classic" -> "Clássicos"; "Kids" -> "Infantil"; "Animation" -> "Animação"; "Music" -> "Música"; "Entertainment" -> "Entretenimento"; "Comedy" -> "Comédia"; "Interactive" -> "Interativo"; "Relax" -> "Relaxamento"; "Family" -> "Família"; "Culture" -> "Cultura"; "Documentary" -> "Documentários"; "Education" -> "Educação"; "Science" -> "Ciência"; "Lifestyle" -> "Estilo de vida"; "Cooking" -> "Culinária"; "Travel" -> "Viagens"; "Shop" -> "Compras"; else -> value
}

private fun channelsFor(category: String, subcategory: String): List<Channel> = ChannelRepository.channels.filter { it.category == category && it.subcategory == subcategory && hasPlayableSource(it) }
private fun hasPlayableSource(channel: Channel): Boolean = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()

private fun openChannel(context: Context, channel: Channel) {
    val intent = Intent(context, PlayerActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        putExtra("channelId", channel.id)
        putExtra("channelName", channel.name)
        channel.streamUrl?.trim()?.takeIf { it.isNotEmpty() }?.let { putExtra("streamUrl", it) }
    }
    context.startActivity(intent)
}

/*
 * Five rotating slots are provided for every category. The current branch has
 * nine bundled Mozambique images; these are used as the safe compile-time
 * fallback until the five category-specific image files are committed.
 */
private fun categoryBackgrounds(category: CategoryDefinition): List<Int> = when (category.name) {
    "Notícias" -> listOf(R.drawable.noticias_01, R.drawable.noticias_02, R.drawable.noticias_03, R.drawable.noticias_04, R.drawable.noticias_05)
    "Gospel" -> listOf(R.drawable.gospel_01, R.drawable.gospel_02, R.drawable.gospel_03, R.drawable.gospel_04, R.drawable.gospel_05)
    "Desporto" -> listOf(R.drawable.desporto_01, R.drawable.desporto_02, R.drawable.desporto_03, R.drawable.desporto_04, R.drawable.desporto_05)
    "Filmes" -> listOf(R.drawable.filmes_01, R.drawable.filmes_02, R.drawable.filmes_03, R.drawable.filmes_04, R.drawable.filmes_05)
    "Havila Kids" -> listOf(R.drawable.havila_kids_01, R.drawable.havila_kids_02, R.drawable.havila_kids_03, R.drawable.havila_kids_04, R.drawable.havila_kids_05)
    "Música" -> listOf(R.drawable.musica_01, R.drawable.musica_02, R.drawable.musica_03, R.drawable.musica_04, R.drawable.musica_05)
    "Família" -> listOf(R.drawable.familia_01, R.drawable.familia_02, R.drawable.familia_03, R.drawable.familia_04, R.drawable.familia_05)
    else -> listOf(R.drawable.mozambique_baia_maputo)
}
