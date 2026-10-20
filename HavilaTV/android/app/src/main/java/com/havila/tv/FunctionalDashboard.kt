package com.havila.tv

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
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
import android.view.KeyEvent

private val FBg = Color(0xFF070B12)
private val FSurface = Color(0xFF111923)
private val FBorder = Color(0xFF283646)
private val FWhite = Color(0xFFF5F7FA)
private val FMuted = Color(0xFF9AA8B7)
private val FGoldStart = Color(0xFFF2C14E)
private val FGoldEnd = Color(0xFFD99A22)
private val FFocus = Color(0xFFFFD76A)
private val FLive = Color(0xFFFF5C5C)

object TvPreferences {
    private const val PREFS = "havila_tv_preferences"
    private const val FAVORITES = "favorites"
    private const val AUTOPLAY = "autoplay"
    private const val ONLY_PLAYABLE = "only_playable"

    private fun prefs(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getFavorites(context: Context): Set<String> = prefs(context).getStringSet(FAVORITES, emptySet())?.toSet() ?: emptySet()

    fun toggleFavorite(context: Context, channelId: String): Set<String> {
        val current = getFavorites(context).toMutableSet()
        if (!current.add(channelId)) current.remove(channelId)
        prefs(context).edit().putStringSet(FAVORITES, current).apply()
        return current.toSet()
    }

    fun clearFavorites(context: Context) {
        prefs(context).edit().remove(FAVORITES).apply()
    }

    fun autoplay(context: Context): Boolean = prefs(context).getBoolean(AUTOPLAY, true)
    fun setAutoplay(context: Context, value: Boolean) { prefs(context).edit().putBoolean(AUTOPLAY, value).apply() }

    fun onlyPlayable(context: Context): Boolean = prefs(context).getBoolean(ONLY_PLAYABLE, true)
    fun setOnlyPlayable(context: Context, value: Boolean) { prefs(context).edit().putBoolean(ONLY_PLAYABLE, value).apply() }
}

@Composable
fun HavilaTvHomeFunctional() {
    val context = LocalContext.current
    var selectedMenu by remember { mutableStateOf(0) }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var showSearch by remember { mutableStateOf(false) }
    var showClearFavorites by remember { mutableStateOf(false) }
    var favorites by remember { mutableStateOf(TvPreferences.getFavorites(context)) }
    var clock by remember { mutableStateOf(currentTime()) }
    val menuRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        menuRequester.requestFocus()
        while (true) {
            clock = currentTime()
            kotlinx.coroutines.delay(30_000)
        }
    }

    BackHandlerFunctional(showSearch, { showSearch = false }, selectedMenu) { selectedMenu = 0 }

    BoxWithConstraints(Modifier.fillMaxSize().background(FBg)) {
        val sidebarWidth = (maxWidth * 0.17f).coerceIn(230.dp, 300.dp)
        val contentPadding = (maxWidth * 0.025f).coerceIn(20.dp, 40.dp)
        val topBarHeight = (maxHeight * 0.085f).coerceIn(54.dp, 72.dp)
        val heroHeight = (maxHeight * 0.30f).coerceIn(190.dp, 270.dp)

        Row(Modifier.fillMaxSize()) {
            FunctionalSideBar(selectedMenu, menuRequester, { selectedMenu = it }, sidebarWidth)
            Column(Modifier.weight(1f).fillMaxHeight()) {
                FunctionalTopActions(clock, topBarHeight, contentPadding, { showSearch = true }, { selectedMenu = 4 })
                if (selectedMenu == 0) FunctionalHero(Modifier.fillMaxWidth().height(heroHeight), contentPadding)
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    FunctionalContent(
                        context = context,
                        selectedMenu = selectedMenu,
                        selectedCategoryIndex = selectedCategoryIndex,
                        favorites = favorites,
                        menuRequester = menuRequester,
                        onCategorySelected = { index -> selectedCategoryIndex = index; selectedMenu = 2 },
                        onToggleFavorite = { id -> favorites = TvPreferences.toggleFavorite(context, id) },
                        onClearFavorites = { showClearFavorites = true }
                    )
                }
            }
        }

        if (showSearch) {
            SearchOverlay(
                context = context,
                favorites = favorites,
                onClose = { showSearch = false },
                onOpen = { channel -> showSearch = false; openChannel(context, channel) },
                onToggleFavorite = { id -> favorites = TvPreferences.toggleFavorite(context, id) }
            )
        }
    }

    if (showClearFavorites) {
        AlertDialog(
            onDismissRequest = { showClearFavorites = false },
            title = { Text("Limpar favoritos", fontWeight = FontWeight.Bold) },
            text = { Text("Deseja remover todos os canais dos favoritos?") },
            confirmButton = {
                TextButton(onClick = {
                    TvPreferences.clearFavorites(context)
                    favorites = emptySet()
                    showClearFavorites = false
                }) { Text("Limpar") }
            },
            dismissButton = { TextButton(onClick = { showClearFavorites = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun BackHandlerFunctional(showSearch: Boolean, closeSearch: () -> Unit, selectedMenu: Int, goHome: () -> Unit) {
    androidx.activity.compose.BackHandler {
        if (showSearch) closeSearch() else if (selectedMenu != 0) goHome() else { /* Home remains active; system back is handled by the existing activity. */ }
    }
}

@Composable
private fun FunctionalSideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit, width: Dp) {
    val items = listOf(
        Icons.Default.Home to "Início",
        Icons.Default.Tv to "Canais",
        Icons.Default.GridView to "Categorias",
        Icons.Default.FavoriteBorder to "Favoritos",
        Icons.Default.Settings to "Configurações"
    )
    Column(
        Modifier.width(width).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FunctionalLogo()
        Spacer(Modifier.height(28.dp))
        items.forEachIndexed { index, item ->
            FunctionalMenuItem(index, item.first, item.second, selected == index, requester, onSelected)
        }
    }
}

@Composable
private fun FunctionalMenuItem(index: Int, icon: ImageVector, label: String, active: Boolean, requester: FocusRequester, onSelected: (Int) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(
        Modifier
            .then(if (index == 0) Modifier.focusRequester(requester) else Modifier)
            .fillMaxWidth().height(72.dp).padding(horizontal = 10.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp))
            .background(if (active) Brush.horizontalGradient(listOf(FGoldStart, FGoldEnd)) else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
            .onFocusChanged { focused = it.isFocused }
            .border(if (focused) 2.dp else 0.dp, if (focused) FFocus else Color.Transparent, RoundedCornerShape(18.dp))
            .focusable()
            .clickable { onSelected(index) }
            .onKeyEvent { event ->
                if (event.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    onSelected(index); true
                } else false
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, label, tint = if (active) Color(0xFF10151C) else FWhite.copy(alpha = .82f), modifier = Modifier.padding(start = 28.dp).width(30.dp))
        Text(label, color = if (active) Color(0xFF10151C) else FWhite.copy(alpha = .9f), fontSize = 18.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(start = 18.dp))
    }
}

@Composable
private fun FunctionalLogo() {
    Column(Modifier.fillMaxWidth().padding(start = 16.dp, top = 18.dp, end = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(Modifier.width(38.dp).height(30.dp).border(3.dp, FGoldStart, RoundedCornerShape(8.dp)))
            Text("HAVILA", color = FWhite, fontSize = 31.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 6.dp))
            Text("TV", color = FGoldStart, fontSize = 29.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 5.dp))
        }
        Text("Boa Informação. Bons Valores. Para Toda a Família.", color = FMuted, fontSize = 8.sp, maxLines = 1, modifier = Modifier.padding(top = 5.dp))
    }
}

@Composable
private fun FunctionalTopActions(time: String, height: Dp, horizontalPadding: Dp, onSearch: () -> Unit, onSettings: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(height).padding(horizontal = horizontalPadding), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        FunctionalAction("Pesquisar", Icons.Default.Search, onSearch)
        Spacer(Modifier.width(18.dp))
        FunctionalAction("Configurações", Icons.Default.Settings, onSettings)
        Spacer(Modifier.width(22.dp))
        Text(time, color = FWhite, fontSize = 19.sp)
    }
}

@Composable
private fun FunctionalAction(label: String, icon: ImageVector, action: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(
        Modifier.clip(RoundedCornerShape(10.dp)).background(if (focused) FSurface else Color.Transparent)
            .border(if (focused) 2.dp else 0.dp, if (focused) FFocus else Color.Transparent, RoundedCornerShape(10.dp))
            .onFocusChanged { focused = it.isFocused }.focusable().clickable { action() }
            .onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { action(); true } else false },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, label, tint = FWhite, modifier = Modifier.width(26.dp))
        Text(label, color = FWhite, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 10.dp))
    }
}

@Composable
private fun FunctionalHero(modifier: Modifier, horizontalPadding: Dp) {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) { while (true) { kotlinx.coroutines.delay(4_000); currentIndex = (currentIndex + 1) % heroSlides.size } }
    val slide = heroSlides[currentIndex]
    Box(modifier.clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))) {
        androidx.compose.animation.Crossfade(targetState = slide, animationSpec = androidx.compose.animation.core.tween(900), label = "hero") { activeSlide ->
            Image(painterResource(activeSlide.imageRes), activeSlide.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        }
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(FBg.copy(alpha = .98f), FBg.copy(alpha = .78f), FBg.copy(alpha = .30f), Color.Transparent))))
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, FBg.copy(alpha = .48f)))))
        Column(Modifier.padding(start = horizontalPadding, top = 30.dp)) {
            Text("Bem-vindo à Havila TV", color = FWhite, fontSize = 46.sp, fontWeight = FontWeight.ExtraBold)
            Text("Televisão digital para toda a família", color = FMuted, fontSize = 25.sp, modifier = Modifier.padding(top = 2.dp))
            Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = FWhite.copy(alpha = .88f), fontSize = 12.sp, letterSpacing = 1.5.sp, modifier = Modifier.padding(top = 15.dp))
        }
        Text(slide.title, color = FWhite.copy(alpha = .82f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.BottomStart).padding(start = horizontalPadding, bottom = 12.dp))
    }
}

@Composable
private fun FunctionalContent(
    context: Context,
    selectedMenu: Int,
    selectedCategoryIndex: Int,
    favorites: Set<String>,
    menuRequester: FocusRequester,
    onCategorySelected: (Int) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onClearFavorites: () -> Unit
) {
    when (selectedMenu) {
        0 -> FunctionalHome(context, menuRequester, favorites, onCategorySelected, onToggleFavorite)
        1 -> FunctionalChannels(context, menuRequester, favorites, onToggleFavorite)
        2 -> FunctionalCategories(context, menuRequester, selectedCategoryIndex, favorites, onToggleFavorite)
        3 -> FunctionalFavorites(context, menuRequester, favorites, onToggleFavorite)
        4 -> FunctionalSettings(context, menuRequester, onClearFavorites)
    }
}

@Composable
private fun FunctionalHome(context: Context, menuRequester: FocusRequester, favorites: Set<String>, onCategorySelected: (Int) -> Unit, onToggleFavorite: (String) -> Unit) {
    val featured = remember { ChannelRepository.channels.filter { it.id in featuredMozambiqueIds } }
    val visible = featured.filter { !TvPreferences.onlyPlayable(context) || hasPlayableSource(it) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitleFunctional("PRINCIPAIS CANAIS DE MOÇAMBIQUE")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(visible) { channel -> FunctionalChannelCard(channel, context, menuRequester, favorites.contains(channel.id), onToggleFavorite) }
        }
        Spacer(Modifier.height(18.dp))
        SectionTitleFunctional("CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categoryDefinitions) { index, category -> FunctionalCategoryCard(category, false, { onCategorySelected(index) }, menuRequester) }
        }
    }
}

@Composable
private fun FunctionalChannels(context: Context, menuRequester: FocusRequester, favorites: Set<String>, onToggleFavorite: (String) -> Unit) {
    val channels = remember { ChannelRepository.channels }
    val visible = channels.filter { !TvPreferences.onlyPlayable(context) || hasPlayableSource(it) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitleFunctional("TODOS OS CANAIS")
        Text("Pressione OK no canal para abrir. Use o coração para guardar nos favoritos.", color = FMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 5.dp, bottom = 10.dp))
        LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(visible) { channel -> FunctionalChannelRow(channel, context, menuRequester, favorites.contains(channel.id), onToggleFavorite) }
        }
    }
}

@Composable
private fun FunctionalCategories(context: Context, menuRequester: FocusRequester, initialCategoryIndex: Int, favorites: Set<String>, onToggleFavorite: (String) -> Unit) {
    var selectedCategoryIndex by remember(initialCategoryIndex) { mutableStateOf(initialCategoryIndex.coerceIn(0, categoryDefinitions.lastIndex)) }
    var selectedSubcategory by remember(selectedCategoryIndex) { mutableStateOf(categoryDefinitions[selectedCategoryIndex].subcategories.first()) }
    val category = categoryDefinitions[selectedCategoryIndex]
    val channels = remember(category.name, selectedSubcategory) { channelsFor(category.name, selectedSubcategory) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitleFunctional("CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categoryDefinitions) { index, item -> FunctionalCategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester) }
        }
        Spacer(Modifier.height(16.dp))
        SectionTitleFunctional("SUB-CATEGORIAS")
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(category.subcategories) { sub -> FunctionalChip(sub, sub == selectedSubcategory) { selectedSubcategory = sub } }
        }
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) { SectionTitleFunctional("CANAIS"); Text("  ${category.name} / $selectedSubcategory", color = FMuted, fontSize = 14.sp) }
        Spacer(Modifier.height(8.dp))
        if (channels.isEmpty()) {
            Box(Modifier.fillMaxWidth().height(82.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(FSurface), contentAlignment = Alignment.CenterStart) {
                Text("Nenhum canal com fonte disponível nesta subcategoria.", color = FMuted, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 20.dp))
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(channels) { channel -> FunctionalChannelRow(channel, context, menuRequester, favorites.contains(channel.id), onToggleFavorite) }
            }
        }
    }
}

@Composable
private fun FunctionalFavorites(context: Context, menuRequester: FocusRequester, favorites: Set<String>, onToggleFavorite: (String) -> Unit) {
    val channels = remember(favorites) { ChannelRepository.channels.filter { it.id in favorites } }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitleFunctional("FAVORITOS")
        Spacer(Modifier.height(8.dp))
        if (channels.isEmpty()) {
            Text("Ainda não tem canais favoritos.", color = FMuted, fontSize = 17.sp)
            Text("Abra Canais, selecione um canal e pressione o coração.", color = FMuted, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
        } else {
            Text("${channels.size} canal(is) guardado(s)", color = FMuted, fontSize = 14.sp, modifier = Modifier.padding(bottom = 10.dp))
            LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(channels) { channel -> FunctionalChannelRow(channel, context, menuRequester, true, onToggleFavorite) }
            }
        }
    }
}

@Composable
private fun FunctionalSettings(context: Context, menuRequester: FocusRequester, onClearFavorites: () -> Unit) {
    var autoplay by remember { mutableStateOf(TvPreferences.autoplay(context)) }
    var onlyPlayable by remember { mutableStateOf(TvPreferences.onlyPlayable(context)) }
    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        SectionTitleFunctional("CONFIGURAÇÕES")
        Spacer(Modifier.height(18.dp))
        SettingRow("Iniciar reprodução automaticamente", "O player começa a transmitir assim que o canal estiver pronto.", autoplay) {
            autoplay = it; TvPreferences.setAutoplay(context, it)
        }
        Spacer(Modifier.height(12.dp))
        SettingRow("Mostrar apenas canais disponíveis", "Oculta canais sem uma fonte de transmissão reproduzível.", onlyPlayable) {
            onlyPlayable = it; TvPreferences.setOnlyPlayable(context, it)
        }
        Spacer(Modifier.height(22.dp))
        var focused by remember { mutableStateOf(false) }
        Row(
            Modifier.clip(RoundedCornerShape(12.dp)).background(FSurface).border(if (focused) 3.dp else 1.dp, if (focused) FFocus else FBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onClearFavorites() }
                .onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { onClearFavorites(); true } else false }
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FavoriteBorder, "Limpar favoritos", tint = FGoldStart, modifier = Modifier.width(28.dp))
            Text("Limpar todos os favoritos", color = FWhite, fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 14.dp))
        }
        Spacer(Modifier.height(18.dp))
        Text("As preferências ficam guardadas neste dispositivo.", color = FMuted, fontSize = 13.sp)
    }
}

@Composable
private fun SettingRow(title: String, description: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(end = 40.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, color = FWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(description, color = FMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
        }
        Switch(checked = value, onCheckedChange = onChange)
    }
}

@Composable
private fun FunctionalCategoryCard(category: CategoryDef, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(270.dp).height(62.dp).scale(if (focused) 1.04f else 1f).clip(RoundedCornerShape(12.dp)).background(if (selected) Brush.horizontalGradient(listOf(FGoldStart, FGoldEnd)) else category.art).border(if (focused) 3.dp else 1.dp, if (focused) FFocus else FBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { onSelect(); true } else false }, verticalAlignment = Alignment.CenterVertically) {
        Icon(category.icon, category.name, tint = if (selected) Color(0xFF10151C) else FWhite, modifier = Modifier.padding(start = 18.dp).width(30.dp))
        Text(category.name, color = if (selected) Color(0xFF10151C) else FWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp, end = 12.dp))
    }
}

@Composable
private fun FunctionalChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(if (selected) Brush.horizontalGradient(listOf(FGoldStart, FGoldEnd)) else Brush.linearGradient(listOf(FSurface, FSurface))).border(if (focused) 3.dp else 1.dp, if (focused) FFocus else FBorder, RoundedCornerShape(20.dp)).onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }.onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { onSelect(); true } else false }.padding(horizontal = 18.dp, vertical = 9.dp)) {
        Text(name, color = if (selected) Color(0xFF10151C) else FWhite, fontSize = 14.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium)
    }
}

@Composable
private fun FunctionalChannelCard(channel: Channel, context: Context, menuRequester: FocusRequester, isFavorite: Boolean, onToggleFavorite: (String) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(275.dp).height(82.dp).clip(RoundedCornerShape(12.dp)).background(FSurface).border(if (focused) 3.dp else 1.dp, if (focused) FFocus else FBorder, RoundedCornerShape(12.dp)).onFocusChanged { focused = it.isFocused }.focusGroup(), verticalAlignment = Alignment.CenterVertically) {
        ChannelOpenTarget(channel, context, menuRequester, Modifier.weight(1f).fillMaxHeight())
        FavoriteButton(channel.id, isFavorite, onToggleFavorite)
    }
}

@Composable
private fun FunctionalChannelRow(channel: Channel, context: Context, menuRequester: FocusRequester, isFavorite: Boolean, onToggleFavorite: (String) -> Unit) {
    Row(Modifier.fillMaxWidth().height(86.dp).padding(end = 24.dp).clip(RoundedCornerShape(12.dp)).background(FSurface).border(1.dp, FBorder, RoundedCornerShape(12.dp)).focusGroup(), verticalAlignment = Alignment.CenterVertically) {
        ChannelOpenTarget(channel, context, menuRequester, Modifier.weight(1f).fillMaxHeight())
        FavoriteButton(channel.id, isFavorite, onToggleFavorite)
    }
}

@Composable
private fun ChannelOpenTarget(channel: Channel, context: Context, menuRequester: FocusRequester, modifier: Modifier) {
    var focused by remember { mutableStateOf(false) }
    val logoResource = localLogoResource(channel.id)
    Row(modifier.onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasPlayableSource(channel)) { openChannel(context, channel) }.onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { if (hasPlayableSource(channel)) openChannel(context, channel); true } else false }.border(if (focused) 3.dp else 0.dp, if (focused) FFocus else Color.Transparent, RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(130.dp).fillMaxHeight().padding(7.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) {
            if (logoResource != null) Image(painterResource(logoResource), channel.name, Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit) else Text(channel.name, color = FWhite, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.Center))
        }
        Column(Modifier.padding(start = 16.dp).weight(1f)) {
            Text(channel.name, color = FWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(channel.description, color = FMuted, fontSize = 13.sp, maxLines = 1, modifier = Modifier.padding(top = 3.dp))
        }
        Text(if (hasPlayableSource(channel)) "AO VIVO" else "INDISPONÍVEL", color = if (hasPlayableSource(channel)) FLive else FMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
private fun FavoriteButton(channelId: String, isFavorite: Boolean, onToggleFavorite: (String) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(
        Modifier.width(58.dp).height(58.dp).padding(end = 10.dp).clip(RoundedCornerShape(12.dp))
            .background(if (focused) FSurface else Color.Transparent)
            .border(if (focused) 3.dp else 0.dp, if (focused) FFocus else Color.Transparent, RoundedCornerShape(12.dp))
            .onFocusChanged { focused = it.isFocused }.focusable().clickable { onToggleFavorite(channelId) }
            .onKeyEvent { e -> if (e.nativeKeyEvent.action == KeyEvent.ACTION_DOWN && (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) { onToggleFavorite(channelId); true } else false },
        contentAlignment = Alignment.Center
    ) {
        Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorito", tint = if (isFavorite) FGoldStart else FWhite, modifier = Modifier.size(28.dp))
    }
}

@Composable
private fun SearchOverlay(context: Context, favorites: Set<String>, onClose: () -> Unit, onOpen: (Channel) -> Unit, onToggleFavorite: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val requester = remember { FocusRequester() }
    val allChannels = remember { ChannelRepository.channels }
    val results = remember(query, allChannels) {
        val q = query.trim()
        if (q.isEmpty()) emptyList() else allChannels.filter { channel ->
            channel.name.contains(q, ignoreCase = true) || channel.category.contains(q, ignoreCase = true) || channel.subcategory.contains(q, ignoreCase = true) || channel.aliases.any { it.contains(q, ignoreCase = true) }
        }.take(30)
    }
    LaunchedEffect(Unit) { requester.requestFocus() }

    Box(Modifier.fillMaxSize().background(Color(0xF2070B12))) {
        Column(Modifier.fillMaxSize().padding(50.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Pesquisar canais", color = FWhite, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.weight(1f))
                SearchCloseButton(onClose)
            }
            Spacer(Modifier.height(24.dp))
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = FWhite, fontSize = 22.sp),
                modifier = Modifier.fillMaxWidth().height(62.dp).focusRequester(requester).background(FSurface, RoundedCornerShape(12.dp)).border(2.dp, FGoldStart, RoundedCornerShape(12.dp)).padding(horizontal = 18.dp, vertical = 17.dp),
                decorationBox = { inner ->
                    if (query.isEmpty()) Text("Escreva o nome do canal, categoria ou subcategoria...", color = FMuted, fontSize = 18.sp)
                    inner()
                }
            )
            Spacer(Modifier.height(18.dp))
            if (query.isEmpty()) {
                Text("A pesquisa procura em todos os canais do catálogo.", color = FMuted, fontSize = 15.sp)
            } else if (results.isEmpty()) {
                Text("Nenhum canal encontrado para \"$query\".", color = FMuted, fontSize = 17.sp)
            } else {
                Text("${results.size} resultado(s)", color = FMuted, fontSize = 14.sp, modifier = Modifier.padding(bottom = 10.dp))
                LazyColumn(Modifier.fillMaxSize().focusGroup(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 20.dp)) {
                    items(results) { channel -> FunctionalChannelRow(channel, context, requester, favorites.contains(channel.id), onToggleFavorite).let { /* UI emitted by composable */ } }
                }
            }
        }
    }
}

@Composable
private fun SearchCloseButton(onClose: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    TextButton(onClick = onClose, modifier = Modifier.onFocusChanged { focused = it.isFocused }.border(if (focused) 2.dp else 0.dp, if (focused) FFocus else Color.Transparent, RoundedCornerShape(10.dp))) { Text("Fechar", color = FWhite, fontSize = 17.sp) }
}

@Composable
private fun SectionTitleFunctional(text: String) { Text(text, color = FWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp) }

private fun currentTime(): String = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())

private fun channelsFor(category: String, subcategory: String): List<Channel> = ChannelRepository.channels.filter { it.category == category && it.subcategory == subcategory && hasPlayableSource(it) }
private fun hasPlayableSource(channel: Channel): Boolean = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()
private fun openChannel(context: Context, channel: Channel) { context.startActivity(android.content.Intent(context, PlayerActivity::class.java).apply { putExtra("channelId", channel.id); putExtra("channelName", channel.name) }) }
