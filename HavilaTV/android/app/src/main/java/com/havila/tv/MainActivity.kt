package com.havila.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Category(val icon: String, val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HavilaTvHome() }
    }
}

@Composable
fun HavilaTvHome() {
    val categories = listOf(
        Category("📰", "Notícias"), Category("✝", "Gospel"),
        Category("⚽", "Desporto"), Category("🎬", "Filmes"),
        Category("👧", "Kids"), Category("🎵", "Música"),
        Category("🇲🇿", "Moçambique"), Category("👨‍👩‍👧", "Família")
    )
    var selected by remember { mutableStateOf("Gospel") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF06101C)).padding(42.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("HAVILA TV", color = Color.White, fontSize = 30.sp)
            Text("⌕   ⚙", color = Color.LightGray, fontSize = 24.sp)
        }
        Text("CATEGORIAS", color = Color(0xFFE8B84B), fontSize = 16.sp)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(4.dp)) {
            items(categories) { category ->
                val focused = category.name == selected
                Text(
                    "${category.icon}  ${category.name}",
                    color = if (focused) Color.Black else Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .background(if (focused) Color(0xFFE8B84B) else Color(0xFF14283D))
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .onFocusChanged { if (it.isFocused) selected = category.name }
                        .focusable()
                )
            }
        }
        Text("CANAIS EM DESTAQUE", color = Color(0xFFE8B84B), fontSize = 16.sp)
        Text("Selecione uma categoria para começar", color = Color.White, fontSize = 26.sp)
        Text("Categoria selecionada: $selected", color = Color(0xFFB8C5D1), fontSize = 18.sp)
    }
}
