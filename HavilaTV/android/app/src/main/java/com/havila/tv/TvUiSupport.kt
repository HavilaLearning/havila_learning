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
    val logoRes: Int,
    val backgroundRes: List<Int>
) {
    // Kept only for source compatibility with older UI code; it renders no artwork.
    val art: Brush get() = Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
}

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

/**
 * Approved Havila TV category artwork.
 * Each category uses its own approved image logo and five approved image backgrounds.
 * No category vector logo and no generated gradient artwork is used as visible art.
 */
val categoryDefinitions = listOf(
    CategoryDef("Notícias", listOf("News", "Weather", "Business", "Legislative", "Public", "General"), "📰", R.drawable.noticias, listOf(R.drawable.noticias_01, R.drawable.noticias_02, R.drawable.noticias_03, R.drawable.noticias_04, R.drawable.noticias_05)),
    CategoryDef("Desporto", listOf("Sports", "Auto", "Outdoor"), "⚽", R.drawable.desporto, listOf(R.drawable.desporto_01, R.drawable.desporto_02, R.drawable.desporto_03, R.drawable.desporto_04, R.drawable.desporto_05)),
    CategoryDef("Gospel", listOf("Religious"), "✝", R.drawable.gospel, listOf(R.drawable.gospel_01, R.drawable.gospel_02, R.drawable.gospel_03, R.drawable.gospel_04, R.drawable.gospel_05)),
    CategoryDef("Filmes", listOf("Movies", "Series", "Classic"), "🎬", R.drawable.filmes, listOf(R.drawable.filmes_01, R.drawable.filmes_02, R.drawable.filmes_03, R.drawable.filmes_04, R.drawable.filmes_05)),
    CategoryDef("Música", listOf("Music", "Entertainment", "Comedy", "Interactive", "Relax"), "🎵", R.drawable.musica, listOf(R.drawable.musica_01, R.drawable.musica_02, R.drawable.musica_03, R.drawable.musica_04, R.drawable.musica_05)),
    CategoryDef("Havila Kids", listOf("Kids", "Animation"), "👧", R.drawable.havila_kids, listOf(R.drawable.havila_kids_01, R.drawable.havila_kids_02, R.drawable.havila_kids_03, R.drawable.havila_kids_04, R.drawable.havila_kids_05)),
    CategoryDef("Família", listOf("Family", "Culture", "Documentary", "Education", "Science", "Lifestyle", "Cooking", "Travel", "Shop"), "👨‍👩‍👧", R.drawable.familia, listOf(R.drawable.familia_01, R.drawable.familia_02, R.drawable.familia_03, R.drawable.familia_04, R.drawable.familia_05))
)

fun categoryBackgrounds(category: CategoryDefinition): List<Int> = category.backgroundRes

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
