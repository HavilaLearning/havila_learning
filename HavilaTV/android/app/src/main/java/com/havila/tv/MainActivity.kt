package com.havila.tv

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Bg = Color(0xFF070B14)
private val Surface = Color(0xFF0F1624)
private val White = Color.White
private val Muted = Color(0xFFA8B0BF)
private val GoldStart = Color(0xFFF2C14E)
private val GoldEnd = Color(0xFFC8901E)
private val FocusGold = Color(0xFFFFC94D)
private val LiveRed = Color(0xFFE53935)
private val CardBorder = Color(0xFF273447)

private data class CategoryDef(val name: String, val icon: ImageVector, val subcategories: List<String>, val art: Brush)
private data class HeroSlide(val title: String, val imageRes: Int)

private val categoryDefinitions = listOf(
    CategoryDef("Notícias", Icons.Default.Public, listOf("News", "Weather", "Business", "Legislative", "Public", "General"), Brush.linearGradient(listOf(Color(0xFF0D5AA7), Color(0xFF07182E)))),
    CategoryDef("Desporto", Icons.Default.SportsSoccer, listOf("Sports", "Auto", "Outdoor"), Brush.linearGradient(listOf(Color(0xFF164D72), Color(0xFF071D2D)))),
    CategoryDef("Gospel", Icons.Default.Favorite, listOf("Religious"), Brush.linearGradient(listOf(Color(0xFF9B4B1A), Color(0xFF22120C)))),
    CategoryDef("Filmes e Séries", Icons.Default.Movie, listOf("Movies", "Series", "Classic"), Brush.linearGradient(listOf(Color(0xFF8D2118), Color(0xFF210909)))),
    CategoryDef("Havila Kids", Icons.Default.ChildCare, listOf("Kids", "Animation"), Brush.linearGradient(listOf(Color(0xFF4B9C57), Color(0xFF183B22)))),
    CategoryDef("Música e Entretenimento", Icons.Default.MusicNote, listOf("Music", "Entertainment", "Comedy", "Interactive", "Relax"), Brush.linearGradient(listOf(Color(0xFF7040A0), Color(0xFF20132F)))),
    CategoryDef("Família e Cultura", Icons.Default.Group, listOf("Family", "Culture", "Documentary", "Education", "Science", "Lifestyle", "Cooking", "Travel", "Shop"), Brush.linearGradient(listOf(Color(0xFF8B6A48), Color(0xFF261C15))))
)

private val heroSlides = listOf(
    HeroSlide("Ponte Maputo–Catembe", R.drawable.mozambique_ponte),
    HeroSlide("Fortaleza de São Sebastião", R.drawable.mozambique_fortaleza),
    HeroSlide("Ilha de Moçambique", R.drawable.mozambique_ilha),
    HeroSlide("Estátua de Samora Machel", R.drawable.mozambique_monumento),
    HeroSlide("Estação dos CFM – Maputo", R.drawable.mozambique_cfm),
    HeroSlide("Baía de Maputo", R.drawable.mozambique_baia_maputo),
    HeroSlide("Património de Moçambique", R.drawable.mozambique_heritage01),
    HeroSlide("História e património de Moçambique", R.drawable.mozambique_heritage02),
    HeroSlide("Paisagens e património de Moçambique", R.drawable.mozambique_heritage03)
)

private val featuredMozambiqueIds = listOf(
    "tvm", "miramar", "tvc", "stv", "mbc-mozambique", "gospel-channel", "christ-tv", "good-news-world"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
private fun HavilaTvHome() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var selectedMenu by remember { mutableStateOf(0) }
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

    BackHandler { if (selectedMenu == 0) showExitDialog = true else selectedMenu = 0 }

    Box(Modifier.fillMaxSize().background(Bg)) {
        Row(Modifier.fillMaxSize()) {
            SideBar(selectedMenu, menuRequester) { selectedMenu = it }
            Column(Modifier.fillMaxSize().padding(end = 28.dp)) {
                TopActions(clock)
                Hero()
                ChannelBrowser(context, menuRequester)
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Sair da Havila TV", fontWeight = FontWeight.Bold) },
            text = { Text("Deseja realmente sair da aplicação?") },
            confirmButton = { TextButton(onClick = { showExitDialog = false; context.finish() }) { Text("Sair") } },
            dismissButton = { TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") } }
        )
    }
}

private fun currentTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

@Composable
private fun SideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit) {
    val items = listOf(
        Icons.Default.Home to "Início",
        Icons.Default.Tv to "Canais",
        Icons.Default.GridView to "Categorias",
        Icons.Default.FavoriteBorder to "Favoritos",
        Icons.Default.Settings to "Configurações"
    )
    Column(Modifier.width(290.dp).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)), horizontalAlignment = Alignment.CenterHorizontally) {
        Logo()
        Spacer(Modifier.height(28.dp))
        items.forEachIndexed { index, item -> MenuItem(index, item.first, item.second, selected == index, requester, onSelected) }
    }
}

@Composable
private fun MenuItem(index: Int, icon: ImageVector, label: String, active: Boolean, requester: FocusRequester, onSelected: (Int) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(
        Modifier.then(if (index == 0) Modifier.focusRequester(requester) else Modifier)
            .fillMaxWidth().height(72.dp).padding(horizontal = 10.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp))
            .background(if (active) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
            .onFocusChanged { focused = it.isFocused }
            .border(if (focused) 2.dp else 0.dp, if (focused) FocusGold else Color.Transparent, RoundedCornerShape(18.dp))
            .focusable().clickable { onSelected(index) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, label, tint = if (active) Color(0xFF10151C) else White.copy(alpha = .82f), modifier = Modifier.padding(start = 28.dp).width(30.dp))
        Text(label, color = if (active) Color(0xFF10151C) else White.copy(alpha = .9f), fontSize = 18.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(start = 18.dp))
    }
}

@Composable
private fun Logo() {
    Column(Modifier.fillMaxWidth().padding(start = 18.dp, top = 22.dp, end = 18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(48.dp).height(38.dp).border(4.dp, GoldStart, RoundedCornerShape(10.dp)))
            Text("HAVILA", color = White, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 7.dp))
            Text("TV", color = GoldStart, fontSize = 35.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 5.dp))
        }
        Text("Boa Informação. Bons Valores. Para Toda a Família.", color = Muted, fontSize = 9.sp, maxLines = 1, modifier = Modifier.padding(top = 5.dp))
    }
}

@Composable
private fun TopActions(time: String) {
    Row(Modifier.fillMaxWidth().height(66.dp).padding(horizontal = 10.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(26.dp))
        Text("Pesquisar", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 38.dp))
        Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(27.dp))
        Text("Configurações", color = White, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp, end = 42.dp))
        Text(time, color = White, fontSize = 19.sp)
    }
}

@Composable
private fun Hero() {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(4_000)
            currentIndex = (currentIndex + 1) % heroSlides.size
        }
    }
    val slide = heroSlides[currentIndex]
    Box(Modifier.fillMaxWidth().height(235.dp).clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))) {
        Crossfade(targetState = slide, animationSpec = tween(900), label = "hero-background") { activeSlide ->
            Image(painterResource(activeSlide.imageRes), activeSlide.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        }
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Bg.copy(alpha = .98f), Bg.copy(alpha = .78f), Bg.copy(alpha = .30f), Color.Transparent))))
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Bg.copy(alpha = .48f)))))
        Column(Modifier.padding(start = 42.dp, top = 30.dp)) {
            Text("Bem-vindo à Havila TV", color = White, fontSize = 46.sp, fontWeight = FontWeight.ExtraBold)
            Text("Televisão digital para toda a família", color = Muted, fontSize = 25.sp, modifier = Modifier.padding(top = 2.dp))
            Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = White.copy(alpha = .88f), fontSize = 12.sp, letterSpacing = 1.5.sp, modifier = Modifier.padding(top = 15.dp))
        }
        Text(slide.title, color = White.copy(alpha = .82f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.BottomStart).padding(start = 42.dp, bottom = 12.dp))
        Row(Modifier.align(Alignment.BottomEnd).padding(end = 28.dp, bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            heroSlides.forEachIndexed { index, _ ->
                Box(Modifier.width(if (index == currentIndex) 22.dp else 7.dp).height(7.dp).clip(RoundedCornerShape(50)).background(if (index == currentIndex) FocusGold else White.copy(alpha = .45f)))
            }
        }
    }
}

@Composable
private fun ChannelBrowser(context: Context, menuRequester: FocusRequester) {
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var selectedSubcategory by remember { mutableStateOf(categoryDefinitions[0].subcategories[0]) }
    val selectedCategory = categoryDefinitions[selectedCategoryIndex]
    val channels = remember(selectedCategoryIndex, selectedSubcategory) { channelsFor(selectedCategory.name, selectedSubcategory) }
    val featuredChannels = remember { ChannelRepository.channels.filter { it.id in featuredMozambiqueIds } }
    val channelRequester = remember { FocusRequester() }

    Column(Modifier.fillMaxSize().padding(start = 30.dp, top = 12.dp)) {
        Text("CATEGORIAS", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
        Spacer(Modifier.height(8.dp))
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            itemsIndexed(categoryDefinitions) { index, category ->
                CategoryCard(category, index == selectedCategoryIndex, {
                    selectedCategoryIndex = index
                    selectedSubcategory = category.subcategories.first()
                }, menuRequester)
            }
        }

        Spacer(Modifier.height(12.dp))
        Text("PRINCIPAIS CANAIS DE MOÇAMBIQUE", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
        Spacer(Modifier.height(7.dp))
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(featuredChannels) { channel -> FeaturedChannelCard(channel, context, menuRequester) }
        }

        Spacer(Modifier.height(12.dp))
        Text("SUB-CATEGORIAS", color = White, fontSize = 17.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.1.sp)
        Spacer(Modifier.height(7.dp))
        LazyRow(Modifier.fillMaxWidth().focusGroup(), horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 24.dp)) {
            items(selectedCategory.subcategories) { subcategory ->
                SubcategoryChip(subcategory, subcategory == selectedSubcategory) { selectedSubcategory = subcategory }
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth().padding(end = 24.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("CANAIS", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
            Text("  ${selectedCategory.name} / $selectedSubcategory", color = Muted, fontSize = 14.sp)
        }
        Spacer(Modifier.height(7.dp))
        LazyColumn(Modifier.fillMaxSize().focusGroup(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(channels) { index, channel -> ChannelRowCard(channel, if (index == 0) channelRequester else null, context, menuRequester) }
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryDef, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    Row(Modifier.width(270.dp).height(62.dp).scale(if (focused) 1.04f else 1f).clip(RoundedCornerShape(12.dp))
        .background(if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else category.art)
        .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp))
        .onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }
        .focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Icon(category.icon, category.name, tint = if (selected) Color(0xFF10151C) else White, modifier = Modifier.padding(start = 18.dp).width(30.dp))
        Text(category.name, color = if (selected) Color(0xFF10151C) else White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp, end = 12.dp))
    }
}

@Composable
private fun SubcategoryChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(if (selected) Brush.horizontalGradient(listOf(GoldStart, GoldEnd)) else Surface)
        .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(20.dp))
        .onFocusChanged { focused = it.isFocused }.focusable().clickable { onSelect() }
        .padding(horizontal = 18.dp, vertical = 9.dp)) {
        Text(name, color = if (selected) Color(0xFF10151C) else White, fontSize = 14.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium)
    }
}

@Composable
private fun FeaturedChannelCard(channel: Channel, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val hasSource = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()
    val logoResource = localLogoResource(channel.id)
    Row(Modifier.width(235.dp).height(76.dp).clip(RoundedCornerShape(12.dp)).background(Surface)
        .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp))
        .onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasSource) {
            context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                putExtra("channelId", channel.id)
                putExtra("channelName", channel.name)
            })
        }.focusProperties { left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(88.dp).fillMaxHeight().padding(6.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) {
            if (logoResource != null) Image(painterResource(logoResource), channel.name, modifier = Modifier.fillMaxSize().padding(7.dp), contentScale = ContentScale.Fit)
            else Text(channel.name, color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
        }
        Column(Modifier.padding(start = 10.dp).weight(1f)) {
            Text(channel.name, color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(if (hasSource) "AO VIVO" else "URL A AGUARDAR", color = if (hasSource) LiveRed else Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

private fun channelsFor(category: String, subcategory: String): List<Channel> {
    return ChannelRepository.channels.filter { channel ->
        if (channel.category != category) return@filter false
        when (channel.id) {
            "tvc", "dw", "rtp-africa", "africanews", "france24", "euronews" -> category == "Notícias" && subcategory == "News"
            "mntv" -> category == "Notícias" && subcategory == "Business"
            "tvm" -> category == "Notícias" && subcategory == "Public"
            "miramar", "mbc-mozambique" -> category == "Notícias" && subcategory == "General"
            "supersport" -> category == "Desporto" && subcategory == "Sports"
            "gospel-channel", "emmanuel-tv", "worldlove-tv", "christ-tv", "good-news-tv", "good-news-world", "hope-international-tv", "loveworldsat", "hope-channel-africa", "faith-tv", "tbn", "tv-mana" -> category == "Gospel" && subcategory == "Religious"
            "reino-infantil" -> category == "Havila Kids" && subcategory == "Kids"
            "stv", "tv-vitoria" -> category == "Música e Entretenimento" && subcategory == "Entertainment"
            else -> false
        }
    }
}

@Composable
private fun ChannelRowCard(channel: Channel, requester: FocusRequester?, context: Context, menuRequester: FocusRequester) {
    var focused by remember { mutableStateOf(false) }
    val hasSource = channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()
    val logoResource = localLogoResource(channel.id)
    Row((if (requester != null) Modifier.focusRequester(requester) else Modifier).fillMaxWidth().height(82.dp).padding(end = 24.dp)
        .clip(RoundedCornerShape(12.dp)).background(Surface)
        .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp))
        .onFocusChanged { focused = it.isFocused }.focusable().clickable(enabled = hasSource) {
            context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                putExtra("channelId", channel.id)
                putExtra("channelName", channel.name)
            })
        }.focusProperties { if (requester != null) up = requester; left = menuRequester }, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(145.dp).fillMaxHeight().padding(7.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF151E2C))) {
            if (logoResource != null) Image(painterResource(logoResource), channel.name, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit)
            else Text(channel.name, color = White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.Center))
        }
        Column(Modifier.padding(start = 16.dp).weight(1f)) {
            Text(channel.name, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(channel.description, color = Muted, fontSize = 13.sp, maxLines = 1, modifier = Modifier.padding(top = 3.dp))
        }
        Text(if (hasSource) "AO VIVO" else "URL A AGUARDAR", color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 14.dp).background(color = if (hasSource) LiveRed else Color(0xFF566170), shape = RoundedCornerShape(7.dp)).padding(horizontal = 9.dp, vertical = 5.dp))
    }
}

private fun localLogoResource(id: String): Int? = when (id) {
    "stv" -> R.drawable.stv_logo
    "miramar" -> R.drawable.miramar_logo
    "tvm" -> R.drawable.tvm_logo
    "gospel-channel" -> R.drawable.gospel_channel_logo
    "supersport" -> R.drawable.supersport_logo
    "dw" -> R.drawable.dw_logo
    "emmanuel-tv" -> R.drawable.emanuel_tv_logo
    "christ-tv" -> R.drawable.christ_tv_logo
    "good-news-tv" -> R.drawable.goodnews_tv_logo
    "hope-international-tv", "hope-channel-africa" -> R.drawable.hope_channel_logo
    "loveworldsat", "worldlove-tv" -> R.drawable.loveworld_sat_logo
    "good-news-world" -> R.drawable.goodnews_world_tv_logo
    else -> null
}
