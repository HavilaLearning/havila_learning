package com.havila.tv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed as gridItemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flag
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
import androidx.compose.ui.draw.shadow
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
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Bg = Color(0xFF070B14)
private val Surface = Color(0xFF0F1624)
private val White = Color(0xFFFFFFFF)
private val Muted = Color(0xFFA8B0BF)
private val GoldStart = Color(0xFFF2C14E)
private val GoldEnd = Color(0xFFC8901E)
private val FocusGold = Color(0xFFFFC94D)
private val LiveRed = Color(0xFFE53935)
private val CardBorder = Color(0xFF273447)

private data class CategoryUi(val name: String, val icon: ImageVector, val art: Brush)
private data class FeaturedUi(val id: String, val name: String, val subtitle: String, val repositoryName: String? = null)
private data class HeroSlide(val title: String, val imageUrl: String)

private val categories = listOf(
    CategoryUi("Notícias", Icons.Default.Public, Brush.linearGradient(listOf(Color(0xFF0D5AA7), Color(0xFF07182E)))),
    CategoryUi("Gospel", Icons.Default.Favorite, Brush.linearGradient(listOf(Color(0xFF9B4B1A), Color(0xFF22120C)))),
    CategoryUi("Desporto", Icons.Default.SportsSoccer, Brush.linearGradient(listOf(Color(0xFF164D72), Color(0xFF071D2D)))),
    CategoryUi("Filmes", Icons.Default.Movie, Brush.linearGradient(listOf(Color(0xFF8D2118), Color(0xFF210909)))),
    CategoryUi("Havila Kids", Icons.Default.ChildCare, Brush.linearGradient(listOf(Color(0xFF4B9C57), Color(0xFF183B22)))),
    CategoryUi("Música", Icons.Default.MusicNote, Brush.linearGradient(listOf(Color(0xFF7040A0), Color(0xFF20132F)))),
    CategoryUi("Moçambique", Icons.Default.Flag, Brush.linearGradient(listOf(Color(0xFF08764E), Color(0xFF102B26)))),
    CategoryUi("Família", Icons.Default.Group, Brush.linearGradient(listOf(Color(0xFF8B6A48), Color(0xFF261C15))))
)

private val featured = listOf(
    FeaturedUi("tvc", "TVC", "Notícias e Actualidade", "TVC"),
    FeaturedUi("stv", "STV", "Entretenimento", "STV"),
    FeaturedUi("miramar", "Miramar TV", "Informação e Cultura", "Miramar"),
    FeaturedUi("tvm", "TVM", "Moçambique em Primeiro", "TVM"),
    FeaturedUi("gospel", "Gospel Channel", "Fé e Esperança", "Gospel Channel"),
    FeaturedUi("supersport", "SuperSport", "Desporto ao Vivo", "SuperSport"),
    FeaturedUi("dw", "DW", "Informação internacional", "DW"),
    FeaturedUi("emmanuel", "Emmanuel TV", "Fé e Esperança", "Emmanuel TV"),
    FeaturedUi("christ", "Christ TV", "Programação cristã", "Christ TV"),
    FeaturedUi("goodnews", "Good News TV", "Televisão cristã internacional", "Good News TV"),
    FeaturedUi("hope", "Hope Channel", "Fé, família e esperança", "Hope International TV"),
    FeaturedUi("goodnews-world", "Good News World TV", "Programação baseada na fé", "Good News World TV Networks"),
    FeaturedUi("loveworldsat", "LoveworldSAT", "Programação cristã 24 horas", "LoveworldSAT"),
    FeaturedUi("france24", "France 24", "Notícias internacionais", "France 24"),
    FeaturedUi("euronews", "Euronews", "Notícias da Europa e do mundo", "Euronews"),
    FeaturedUi("rtp-africa", "RTP África", "Informação e conteúdos de Portugal e África", "RTP África")
)

// Fotografias públicas do Wikimedia Commons. O carregamento é feito em rede para manter o APK leve.
// A ordem foi definida para começar por uma ponte e percorrer património, história e paisagens de Moçambique.
private val heroSlides = listOf(
    HeroSlide("Ponte Maputo–Catembe", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Maputo-Katembe_Bridge_%2834275941386%29.jpg?width=1600"),
    HeroSlide("Fortaleza de São Sebastião", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Fortaleza_de_S%C3%A3o_Sebasti%C3%A3o-01.jpg?width=1600"),
    HeroSlide("Ilha de Moçambique", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Ilha_de_Mocambique.jpg?width=1600"),
    HeroSlide("Estátua de Samora Machel", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Statue_of_Samora_Machel.jpg?width=1600"),
    HeroSlide("Estação dos CFM – Maputo", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Maputo_CFC_Station.jpg?width=1600"),
    HeroSlide("Praça da Independência – Maputo", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Pra%C3%A7a_da_Independ%C3%AAncia%2C_Maputo_2025.jpg?width=1600"),
    HeroSlide("Arquipélago de Bazaruto", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Bazaruto_Archipelago_National_Park%2C_Mozambique.jpg?width=1600"),
    HeroSlide("Ponte da Ilha de Moçambique", "https://commons.wikimedia.org/wiki/Special:Redirect/file/Mozambique_Island_Bridge.jpg?width=1600")
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
    val categoryRequester = remember { FocusRequester() }
    val channelRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        categoryRequester.requestFocus()
        while (true) {
            clock = currentTime()
            kotlinx.coroutines.delay(30_000)
        }
    }

    BackHandler {
        if (selectedMenu == 0) showExitDialog = true else {
            selectedMenu = 0
            categoryRequester.requestFocus()
        }
    }

    Box(Modifier.fillMaxSize().background(Bg)) {
        Row(Modifier.fillMaxSize()) {
            SideBar(selectedMenu, menuRequester, { selectedMenu = it }, { showExitDialog = true })
            Column(Modifier.fillMaxSize().padding(end = 28.dp)) {
                TopActions(clock)
                Hero()
                CategorySection(categoryRequester, channelRequester, menuRequester)
                FeaturedChannels(channelRequester, context, menuRequester)
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Sair da Havila TV", fontWeight = FontWeight.Bold) },
            text = { Text("Deseja realmente sair da aplicação?") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    if (context is ComponentActivity) context.finish()
                }) { Text("Sair") }
            },
            dismissButton = { TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") } }
        )
    }
}

private fun currentTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

@Composable
private fun SideBar(selected: Int, requester: FocusRequester, onSelected: (Int) -> Unit, onExit: () -> Unit) {
    val items = listOf(
        Icons.Default.Home to "Início",
        Icons.Default.Tv to "Canais",
        Icons.Default.GridView to "Categorias",
        Icons.Default.FavoriteBorder to "Favoritos",
        Icons.Default.Settings to "Configurações"
    )
    Column(
        Modifier.width(290.dp).fillMaxHeight().background(Color(0xFF050B14)).border(1.dp, Color(0xFF1E2A39)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(Modifier.height(28.dp))
        items.forEachIndexed { index, item ->
            MenuItem(index, item.first, item.second, selected == index, requester, onSelected)
        }
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
            .focusable()
            .clickable { onSelected(index) },
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

    Box(Modifier.fillMaxWidth().height(285.dp).clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))) {
        Crossfade(targetState = slide, animationSpec = tween(durationMillis = 900), label = "hero-background") { activeSlide ->
            AsyncImage(
                model = activeSlide.imageUrl,
                contentDescription = activeSlide.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Gradiente escuro à esquerda para preservar a identidade Havila e a leitura do texto.
        Box(
            Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    listOf(Bg.copy(alpha = .98f), Bg.copy(alpha = .78f), Bg.copy(alpha = .32f), Color.Transparent)
                )
            )
        )
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Bg.copy(alpha = .45f)))
            )
        )

        Column(Modifier.padding(start = 42.dp, top = 34.dp)) {
            Text("Bem-vindo à Havila TV", color = White, fontSize = 52.sp, fontWeight = FontWeight.ExtraBold)
            Text("Televisão digital para toda a família", color = Muted, fontSize = 28.sp, modifier = Modifier.padding(top = 2.dp))
            Text("NOTÍCIAS • EDUCAÇÃO • FÉ • CULTURA • DESPORTO • ENTRETENIMENTO", color = White.copy(alpha = .88f), fontSize = 13.sp, letterSpacing = 1.7.sp, modifier = Modifier.padding(top = 18.dp))
        }

        Text(
            slide.title,
            color = White.copy(alpha = .82f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 42.dp, bottom = 14.dp)
        )

        Row(
            Modifier.align(Alignment.BottomEnd).padding(end = 28.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            heroSlides.forEachIndexed { index, _ ->
                Box(
                    Modifier.width(if (index == currentIndex) 22.dp else 7.dp)
                        .height(7.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (index == currentIndex) FocusGold else White.copy(alpha = .45f))
                )
            }
        }
    }
}

@Composable
private fun CategorySection(categoryRequester: FocusRequester, channelRequester: FocusRequester, menuRequester: FocusRequester) {
    Column(Modifier.padding(start = 30.dp, top = 14.dp)) {
        Text("CATEGORIAS", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
        Spacer(Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().height(348.dp).focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(end = 24.dp, bottom = 8.dp)
        ) {
            gridItemsIndexed(categories) { index, category ->
                CategoryCard(category, if (index == 0) categoryRequester else null, channelRequester, menuRequester, index % 4 == 0)
            }
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryUi, requester: FocusRequester?, channelRequester: FocusRequester, menuRequester: FocusRequester, firstColumn: Boolean) {
    var focused by remember { mutableStateOf(false) }
    Box(
        (if (requester != null) Modifier.focusRequester(requester) else Modifier)
            .width(360.dp).height(160.dp)
            .scale(if (focused) 1.05f else 1f)
            .shadow(if (focused) 18.dp else 0.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)).background(category.art)
            .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp))
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable { }
            .focusProperties {
                down = channelRequester
                if (firstColumn) left = menuRequester
            }
    ) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = .78f)))))
        Row(Modifier.align(Alignment.BottomStart).padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(category.icon, category.name, tint = White, modifier = Modifier.width(38.dp).height(38.dp))
            Text(category.name, color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 14.dp))
        }
    }
}

@Composable
private fun FeaturedChannels(requester: FocusRequester, context: android.content.Context, menuRequester: FocusRequester) {
    Column(Modifier.padding(start = 30.dp, top = 8.dp)) {
        Text("CANAIS EM DESTAQUE", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
        Spacer(Modifier.height(8.dp))
        LazyRow(
            Modifier.fillMaxWidth().focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(end = 32.dp)
        ) {
            itemsIndexed(featured) { index, item ->
                val repositoryChannel = item.repositoryName?.let { name ->
                    ChannelRepository.channels.firstOrNull { it.name.equals(name, ignoreCase = true) }
                }
                val hasSource = repositoryChannel != null && (
                    repositoryChannel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true ||
                        repositoryChannel.playlistUrls.isNotEmpty()
                    )
                FeaturedChannelCard(
                    item = item,
                    logoResource = localLogoResource(item.id),
                    isPlayable = hasSource,
                    requester = if (index == 0) requester else null,
                    menuRequester = menuRequester
                ) {
                    if (repositoryChannel != null && hasSource) {
                        context.startActivity(Intent(context, PlayerActivity::class.java).apply {
                            putExtra("channelId", repositoryChannel.id)
                            putExtra("channelName", item.name)
                        })
                    }
                }
            }
        }
    }
}

private fun localLogoResource(id: String): Int? = when (id) {
    "stv" -> R.drawable.stv_logo
    "miramar" -> R.drawable.miramar_logo
    "tvm" -> R.drawable.tvm_logo
    "gospel" -> R.drawable.gospel_channel_logo
    "supersport" -> R.drawable.supersport_logo
    "dw" -> R.drawable.dw_logo
    "emmanuel" -> R.drawable.emanuel_tv_logo
    "christ" -> R.drawable.christ_tv_logo
    "goodnews" -> R.drawable.goodnews_tv_logo
    "hope" -> R.drawable.hope_channel_logo
    "loveworldsat" -> R.drawable.loveworld_sat_logo
    "goodnews-world" -> R.drawable.goodnews_world_tv_logo
    else -> null
}

@Composable
private fun FeaturedChannelCard(
    item: FeaturedUi,
    logoResource: Int?,
    isPlayable: Boolean,
    requester: FocusRequester?,
    menuRequester: FocusRequester,
    onOpen: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    Column(
        (if (requester != null) Modifier.focusRequester(requester) else Modifier)
            .width(265.dp).height(165.dp)
            .scale(if (focused) 1.05f else 1f)
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable(enabled = isPlayable) { onOpen() }
            .focusProperties { if (requester != null) up = requester; left = menuRequester }
    ) {
        Box(
            Modifier.fillMaxWidth().height(105.dp)
                .shadow(if (focused) 18.dp else 0.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Surface)
                .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(10.dp))
        ) {
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = .55f)))))
            if (logoResource != null) {
                Image(
                    painter = painterResource(logoResource),
                    contentDescription = item.name,
                    modifier = Modifier.align(Alignment.Center).width(150.dp).height(78.dp)
                )
            } else {
                Text(item.name, color = White, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.Center))
            }
            Text(
                if (isPlayable) "AO VIVO" else "DISPONÍVEL",
                color = White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(horizontal = 9.dp, vertical = 5.dp)
                    .background(if (isPlayable) LiveRed else Color(0xFF566170), RoundedCornerShape(7.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
        Text(item.name, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 6.dp))
        Text(item.subtitle, color = Muted, fontSize = 13.sp, maxLines = 1)
    }
}
