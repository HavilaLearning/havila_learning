from pathlib import Path


def replace_once(text: str, old: str, new: str, label: str) -> str:
    if new in text:
        return text
    if old not in text:
        raise SystemExit(f'Patch target not found: {label}')
    return text.replace(old, new, 1)


main = Path('app/src/main/java/com/havila/tv/MainActivity.kt')
s = main.read_text(encoding='utf-8')

# Settings: persist user choices instead of resetting them on every screen recreation.
s = replace_once(
    s,
    '2 -> CategoryContent(context, menuRequester, selectedCategoryIndex); 3 -> FavoritesContent(context); 4 -> SettingsContent()',
    '2 -> CategoryContent(context, menuRequester, selectedCategoryIndex); 3 -> FavoritesContent(context); 4 -> SettingsContent(context)',
    'settings dashboard mapping',
)
s = replace_once(
    s,
    '''@Composable
private fun SettingsContent() {
    var autoplay by remember { mutableStateOf(true) }
    var showLiveBadge by remember { mutableStateOf(true) }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp, end = 20.dp)) {
        SectionTitle("CONFIGURAÇÕES")
        SettingsRow("Reprodução automática", "Iniciar o canal imediatamente ao selecionar.", autoplay) { autoplay = it }
        SettingsRow("Indicador AO VIVO", "Mostrar o estado LIVE nos cartões dos canais.", showLiveBadge) { showLiveBadge = it }
    }
}''',
    '''@Composable
private fun SettingsContent(context: Context) {
    val prefs = remember { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    var autoplay by remember { mutableStateOf(prefs.getBoolean("autoplay", true)) }
    var showLiveBadge by remember { mutableStateOf(prefs.getBoolean("show_live_badge", true)) }
    Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 8.dp, end = 20.dp)) {
        SectionTitle("CONFIGURAÇÕES")
        SettingsRow("Reprodução automática", "Iniciar o canal imediatamente ao selecionar.", autoplay) {
            autoplay = it
            prefs.edit().putBoolean("autoplay", it).apply()
        }
        SettingsRow("Indicador AO VIVO", "Mostrar o estado LIVE nos cartões dos canais.", showLiveBadge) {
            showLiveBadge = it
            prefs.edit().putBoolean("show_live_badge", it).apply()
        }
    }
}''',
    'settings content',
)

# Settings rows: one clear focus target for a TV remote, with DPAD/ENTER activation.
s = replace_once(
    s,
    '''private fun SettingsRow(title: String, description: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp).clip(RoundedCornerShape(12.dp)).background(Surface).padding(horizontal = 14.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) { Text(title, color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold); Text(description, color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 3.dp)) }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}''',
    '''private fun SettingsRow(title: String, description: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(
        Modifier.fillMaxWidth().padding(vertical = 6.dp).clip(RoundedCornerShape(12.dp))
            .background(if (focused) Color(0xFF172333) else Surface)
            .border(if (focused) 3.dp else 1.dp, if (focused) FocusGold else CardBorder, RoundedCornerShape(12.dp))
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .onKeyEvent { event ->
                val key = event.nativeKeyEvent
                if (key.action == android.view.KeyEvent.ACTION_DOWN && key.repeatCount == 0 &&
                    (key.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER || key.keyCode == android.view.KeyEvent.KEYCODE_ENTER)) {
                    onChecked(!checked); true
                } else false
            }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) { Text(title, color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold); Text(description, color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 3.dp)) }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}''',
    'settings row',
)

# Search/settings top actions: make focus visible and keep them usable with a remote.
s = replace_once(
    s,
    '''private fun TopActions(time: String, height: Dp, horizontalPadding: Dp, onSearch: () -> Unit, onSettings: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(height).padding(horizontal = horizontalPadding), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.tvAction(onSearch), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(22.dp)); Text("Pesquisar", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 18.dp)) }
        Row(Modifier.tvAction(onSettings), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(23.dp)); Text("Configurações", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 20.dp)) }
        Text(time, color = White, fontSize = 16.sp)
    }
}''',
    '''private fun TopActions(time: String, height: Dp, horizontalPadding: Dp, onSearch: () -> Unit, onSettings: () -> Unit) {
    var searchFocused by remember { mutableStateOf(false) }
    var settingsFocused by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth().height(height).padding(horizontal = horizontalPadding), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Row(
            Modifier.clip(RoundedCornerShape(10.dp)).background(if (searchFocused) Color(0xFF172333) else Color.Transparent)
                .border(if (searchFocused) 2.dp else 0.dp, FocusGold, RoundedCornerShape(10.dp))
                .onFocusChanged { searchFocused = it.isFocused }.tvAction(onSearch).padding(horizontal = 6.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) { Icon(Icons.Default.Search, "Pesquisar", tint = White, modifier = Modifier.width(22.dp)); Text("Pesquisar", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 12.dp)) }
        Row(
            Modifier.clip(RoundedCornerShape(10.dp)).background(if (settingsFocused) Color(0xFF172333) else Color.Transparent)
                .border(if (settingsFocused) 2.dp else 0.dp, FocusGold, RoundedCornerShape(10.dp))
                .onFocusChanged { settingsFocused = it.isFocused }.tvAction(onSettings).padding(horizontal = 6.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) { Icon(Icons.Default.Settings, "Configurações", tint = White, modifier = Modifier.width(23.dp)); Text("Configurações", color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 6.dp, end = 14.dp)) }
        Text(time, color = White, fontSize = 16.sp, modifier = Modifier.padding(start = 2.dp))
    }
}''',
    'top actions',
)

# Favorites: ensure the heart is a direct TV focus target.
s = replace_once(
    s,
    'IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(38.dp))',
    'IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(38.dp).focusable())',
    'featured favorite button',
)
s = replace_once(
    s,
    'IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(40.dp))',
    'IconButton(onClick = { FavoritesStore.toggle(context, channel.id) }, modifier = Modifier.size(40.dp).focusable())',
    'grid favorite button',
)

# Search: automatically focus the query field when the dialog opens.
s = replace_once(
    s,
    '''private fun SearchDialog(context: Context, onDismiss: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val allChannels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }''',
    '''private fun SearchDialog(context: Context, onDismiss: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val searchRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { awaitFrame(); runCatching { searchRequester.requestFocus() } }
    val allChannels = remember { ChannelRepository.channels.filter(::hasPlayableSource) }''',
    'search dialog focus',
)
s = replace_once(
    s,
    'OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = { Text("Nome do canal") }, leadingIcon = { Icon(Icons.Default.Search, null) })',
    'OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth().focusRequester(searchRequester), singleLine = true, label = { Text("Nome do canal") }, leadingIcon = { Icon(Icons.Default.Search, null) })',
    'search field requester',
)

# Category logos: add a logo resource without changing the Home card API.
main.write_text(s, encoding='utf-8')

support = Path('app/src/main/java/com/havila/tv/TvUiSupport.kt')
t = support.read_text(encoding='utf-8')
t = replace_once(
    t,
    '''data class CategoryDef(
    val name: String,
    val subcategories: List<String>,
    val icon: String,
    val art: Brush
)''',
    '''data class CategoryDef(
    val name: String,
    val subcategories: List<String>,
    val icon: String,
    val art: Brush,
    val logoRes: Int
)''',
    'category logo field',
)

categories = {
    'Notícias': 'R.drawable.noticias',
    'Desporto': 'R.drawable.desporto',
    'Gospel': 'R.drawable.gospel',
    'Filmes': 'R.drawable.filmes',
    'Música': 'R.drawable.musica',
    'Havila Kids': 'R.drawable.havila_kids',
    'Família': 'R.drawable.familia',
}
for name, res in categories.items():
    marker = f'CategoryDef("{name}",'
    start = t.find(marker)
    if start < 0:
        raise SystemExit(f'Category definition not found: {name}')
    end = t.find('\n', start)
    line = t[start:end]
    if 'logoRes =' not in line and not line.rstrip().endswith(')'):
        raise SystemExit(f'Unexpected category definition format: {name}')
    if res not in line:
        # Insert the resource as the final constructor argument.
        if line.endswith('),'):
            line2 = line[:-2] + f', {res}),'
        elif line.endswith(')'):
            line2 = line[:-1] + f', {res})'
        else:
            raise SystemExit(f'Cannot patch category definition: {name}')
        t = t[:start] + line2 + t[end:]

support.write_text(t, encoding='utf-8')

# CategoryCard gets an opt-in image mode. Home keeps its existing appearance.
main = Path('app/src/main/java/com/havila/tv/MainActivity.kt')
s = main.read_text(encoding='utf-8')
s = replace_once(
    s,
    'private fun CategoryCard(category: CategoryDefinition, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester) {',
    'private fun CategoryCard(category: CategoryDefinition, selected: Boolean, onSelect: () -> Unit, menuRequester: FocusRequester, useImage: Boolean = false) {',
    'category card optional image mode',
)
s = replace_once(
    s,
    'Text(category.icon, color = if (selected) Color(0xFF10151C) else White, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 13.dp))',
    'if (useImage) Image(painter = painterResource(id = category.logoRes), contentDescription = category.name, contentScale = ContentScale.Fit, modifier = Modifier.width(78.dp).fillMaxHeight().padding(4.dp)) else Text(category.icon, color = if (selected) Color(0xFF10151C) else White, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 13.dp))',
    'category card logo rendering',
)
s = replace_once(
    s,
    'itemsIndexed(categoryDefinitions, key = { _, item -> item.name }) { index, item -> CategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester) }',
    'itemsIndexed(categoryDefinitions, key = { _, item -> item.name }) { index, item -> CategoryCard(item, index == selectedCategoryIndex, { selectedCategoryIndex = index; selectedSubcategory = item.subcategories.first() }, menuRequester, useImage = true) }',
    'category screen logo mode',
)
main.write_text(s, encoding='utf-8')

print('Havila TV UI fixes applied.')
