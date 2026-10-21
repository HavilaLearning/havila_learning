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
import androidx.compose.ui.draw.clip as composeClip
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun ComponentActivity.setContent(content: @Composable () -> Unit) {
    activitySetContent { content() }
}

fun Modifier.focusRequester(requester: FocusRequester): Modifier = composeFocusRequester(requester)
fun Modifier.clip(shape: Shape): Modifier = composeClip(shape)

typealias CategoryDefinition = CategoryDef

data class HeroSlide(val title: String, val imageRes: Int)

data class CategoryDef(
    val name: String,
    val subcategories: List<String>,
    val icon: String,
    val art: Brush,
    val logoRes: Int
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
    CategoryDef("Notícias", listOf("News", "Weather", "Business", "Legislative", "Public", "General"), "📰", categoryArt(Color(0xFF17324D), Color(0xFF245D79)), R.drawable.noticias),
    CategoryDef("Desporto", listOf("Sports", "Auto", "Outdoor"), "⚽", categoryArt(Color(0xFF183E2B), Color(0xFF28704A)), R.drawable.desporto),
    CategoryDef("Gospel", listOf("Religious"), "✝", categoryArt(Color(0xFF382A58), Color(0xFF60448A)), R.drawable.gospel),
    CategoryDef("Filmes", listOf("Movies", "Series", "Classic"), "🎬", categoryArt(Color(0xFF47262B), Color(0xFF803B43)), R.drawable.filmes),
    CategoryDef("Música", listOf("Music", "Entertainment", "Comedy", "Interactive", "Relax"), "🎵", categoryArt(Color(0xFF183C49), Color(0xFF2B7181)), R.drawable.musica),
    CategoryDef("Havila Kids", listOf("Kids", "Animation"), "👧", categoryArt(Color(0xFF5A4218), Color(0xFF92702A)), R.drawable.havila_kids),
    CategoryDef("Família", listOf("Family", "Culture", "Documentary", "Education", "Science", "Lifestyle", "Cooking", "Travel", "Shop"), "👨‍👩‍👧", categoryArt(Color(0xFF3D3220), Color(0xFF6B5630)), R.drawable.familia)
)

val featuredMozambiqueIds = setOf("tvm", "stv", "miramar")

fun localLogoResource(channelId: String): Int? = when (channelId.lowercase()) {
    "dw-english" -> R.drawable.dw_logo
    "goodnews-tv" -> R.drawable.goodnews_tv_logo
    "good-news-world" -> R.drawable.goodnews_world_tv_logo
    "hope-channel-international" -> R.drawable.hope_channel_logo
    "loveworldsat" -> R.drawable.loveworld_sat_logo
    "christ-tv" -> R.drawable.christ_tv_logo
    "emanuel-tv" -> R.drawable.emanuel_tv_logo
    "gospel-channel" -> R.drawable.gospel_channel_logo
    "miramar", "miramar-tv" -> R.drawable.miramar_logo
    else -> null
}
