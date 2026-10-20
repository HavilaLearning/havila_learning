package com.havila.tv

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent as activitySetContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester as composeFocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.clip as composeClip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Tv
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun ComponentActivity.setContent(content: @Composable () -> Unit) {
    activitySetContent { content() }
}

fun Modifier.focusRequester(requester: FocusRequester): Modifier = composeFocusRequester(requester)

fun Modifier.clip(shape: Shape): Modifier = composeClip(this, shape)

typealias CategoryDefinition = CategoryDef

data class HeroSlide(val title: String, val imageRes: Int)

data class CategoryDef(
    val name: String,
    val subcategories: List<String>,
    val icon: ImageVector,
    val art: Brush
)

val heroSlides = listOf(
    HeroSlide("Baía de Maputo", R.drawable.mozambique_baia_maputo),
    HeroSlide("Caminhos de Ferro de Moçambique", R.drawable.mozambique_cfm),
    HeroSlide("Fortaleza de Moçambique", R.drawable.mozambique_fortaleza),
    HeroSlide("Ilha de Moçambique", R.drawable.mozambique_ilha),
    HeroSlide("Monumento de Moçambique", R.drawable.mozambique_monumento),
    HeroSlide("Ponte de Moçambique", R.drawable.mozambique_ponte),
    HeroSlide("Património de Moçambique", R.drawable.mozambique_heritage01),
    HeroSlide("Património de Moçambique", R.drawable.mozambique_heritage02),
    HeroSlide("Património de Moçambique", R.drawable.mozambique_heritage03)
)

private fun categoryArt(start: Color, end: Color): Brush = Brush.linearGradient(listOf(start, end))

val categoryDefinitions = listOf(
    CategoryDef("Notícias", listOf("News", "Weather", "Business", "Legislative", "Public", "General"), Icons.Default.Tv, categoryArt(Color(0xFF17324D), Color(0xFF245D79))),
    CategoryDef("Desporto", listOf("Sports", "Auto", "Outdoor"), Icons.Default.Tv, categoryArt(Color(0xFF183E2B), Color(0xFF28704A))),
    CategoryDef("Gospel", listOf("Religious"), Icons.Default.Home, categoryArt(Color(0xFF382A58), Color(0xFF60448A))),
    CategoryDef("Filmes e Séries", listOf("Movies", "Series", "Classic"), Icons.Default.Movie, categoryArt(Color(0xFF47262B), Color(0xFF803B43))),
    CategoryDef("Havila Kids", listOf("Kids", "Animation"), Icons.Default.School, categoryArt(Color(0xFF5A4218), Color(0xFF92702A))),
    CategoryDef("Música e Entretenimento", listOf("Music", "Entertainment", "Comedy", "Interactive", "Relax"), Icons.Default.MusicNote, categoryArt(Color(0xFF183C49), Color(0xFF2B7181))),
    CategoryDef("Família e Cultura", listOf("Family", "Culture", "Documentary", "Education", "Science", "Lifestyle", "Cooking", "Travel", "Shop"), Icons.Default.GridView, categoryArt(Color(0xFF3D3220), Color(0xFF6B5630)))
)

@Composable
fun FavoritesContent() {
    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Text("FAVORITOS", color = Color.White, fontSize = 16.sp)
        Text("Os seus canais favoritos aparecerão aqui.", color = Color(0xFF9AA8B7), fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun SettingsContent() {
    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Text("CONFIGURAÇÕES", color = Color.White, fontSize = 16.sp)
        Text("Configurações da Havila TV.", color = Color(0xFF9AA8B7), fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

/** Channels from the supplied list that are identified as Mozambique entries. */
val featuredMozambiqueIds = setOf(
    "mntv",
    "reino-infantil-mz",
    "tv-mana-mocambique",
    "tv-vitoria",
    "tvm-mozambique"
)

fun localLogoResource(channelId: String): Int? = when (channelId) {
    "dw-english" -> R.drawable.dw_logo
    "goodnews-tv" -> R.drawable.goodnews_tv_logo
    "good-news-world" -> R.drawable.goodnews_world_tv_logo
    "hope-channel-international" -> R.drawable.hope_channel_logo
    "loveworldsat" -> R.drawable.loveworld_sat_logo
    "christ-tv" -> R.drawable.christ_tv_logo
    "emanuel-tv" -> R.drawable.emanuel_tv_logo
    "gospel-channel" -> R.drawable.gospel_channel_logo
    "miramar" -> R.drawable.miramar_logo
    else -> null
}
