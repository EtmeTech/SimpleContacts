package tech.etme.contacts.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.theme.Accent

enum class AppTab(val label: String) {
    CONTACTS("Contacts"),
    HIGHLIGHTS("Favorites"), //updated name bc Highlights wouldn't make sense
    MANAGE("Info") //updated name bc settings haven't been added yet
}

@Composable
fun BottomNavBar(
    selected: AppTab,
    onSelect: (AppTab) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selected == AppTab.CONTACTS,
            onClick = { onSelect(AppTab.CONTACTS) },
            icon = { Icon(AppIcons.Person, contentDescription = null) },
            label = { Text(AppTab.CONTACTS.label) },
            colors = navColors()
        )
        NavigationBarItem(
            selected = selected == AppTab.HIGHLIGHTS,
            onClick = { onSelect(AppTab.HIGHLIGHTS) },
            icon = { Icon(AppIcons.Heart, contentDescription = null) },
            label = { Text(AppTab.HIGHLIGHTS.label) },
            colors = navColors()
        )
        NavigationBarItem(
            selected = selected == AppTab.MANAGE,
            onClick = { onSelect(AppTab.MANAGE) },
            icon = { Icon(AppIcons.Wrench, contentDescription = null) },
            label = { Text(AppTab.MANAGE.label) },
            colors = navColors()
        )
    }
}

@Composable
private fun navColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Accent,
    selectedTextColor = Accent,
    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
)
