package androidx.compose.ui

import androidx.compose.ui.draw.clip as drawClip
import androidx.compose.ui.graphics.Shape

fun Modifier.clip(shape: Shape): Modifier = drawClip(shape)
