package tech.etme.contacts.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tech.etme.contacts.model.Contact
import tech.etme.contacts.ui.components.ContactAvatar
import tech.etme.contacts.ui.components.SearchField
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.theme.Accent

private val FULL_ALPHABET = ('A'..'Z').map { it.toString() } + "#"

private sealed class ListRow {
    data class Header(val letter: String) : ListRow()
    data class Item(val contact: Contact) : ListRow()
}

@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onOpenContact: (Contact) -> Unit
) {
    var query by remember { androidx.compose.runtime.mutableStateOf("") }
    var expandedContactId by remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    val filtered = remember(contacts, query) {
        if (query.isBlank()) contacts
        else contacts.filter { it.fullName.contains(query, ignoreCase = true) }
    }
    val grouped = remember(filtered) {
        filtered.sortedBy { it.fullName.lowercase() }
            .groupBy { it.initialLetter }
            .toSortedMap()
    }
    val rows = remember(grouped) {
        buildList {
            grouped.forEach { (letter, items) ->
                add(ListRow.Header(letter))
                items.forEach { add(ListRow.Item(it)) }
            }
        }
    }
    val availableLetters = grouped.keys
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Contacts",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )

        SearchField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (rows.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = AppIcons.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (query.isBlank()) "No contacts yet" else "No matches",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(rows, key = { _, row ->
                        when (row) {
                            is ListRow.Header -> "header_${row.letter}"
                            is ListRow.Item -> row.contact.id
                        }
                    }) { _, row ->
                        when (row) {
                            is ListRow.Header -> LetterHeader(row.letter)
                            is ListRow.Item -> ContactRow(
                                contact = row.contact,
                                isExpanded = expandedContactId == row.contact.id,
                                onToggleExpand = {
                                    expandedContactId = if (expandedContactId == row.contact.id) null else row.contact.id
                                },
                                onEdit = { onOpenContact(row.contact) }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(96.dp)) }
                }

                AlphabetIndex(
                    availableLetters = availableLetters,
                    onLetterClick = { letter ->
                        val target = availableLetters.firstOrNull { it >= letter } ?: availableLetters.lastOrNull()
                        val index = rows.indexOfFirst { it is ListRow.Header && it.letter == target }
                        if (index >= 0) {
                            scope.launch { listState.animateScrollToItem(index) }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LetterHeader(letter: String) {
    Text(
        text = letter,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 4.dp)
    )
}

@Composable
private fun ContactRow(
    contact: Contact,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onEdit: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .clickable(onClick = onToggleExpand)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactAvatar()
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.fullName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp
                )
                if (contact.phone.isNotBlank()) {
                    Text(
                        text = contact.phone,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
            if (contact.favorite) {
                Icon(
                    imageVector = AppIcons.StarFilled,
                    contentDescription = "Favorite",
                    tint = Accent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // calling
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phone}"))
                    context.startActivity(intent)
                }) {
                    Icon(AppIcons.Phone, contentDescription = "call", tint = MaterialTheme.colorScheme.onSurface)
                }

                // email
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                    context.startActivity(intent)
                }) {
                    Icon(AppIcons.Envelope, contentDescription = "send email", tint = MaterialTheme.colorScheme.onSurface)
                }

                // edit
                IconButton(onClick = onEdit) {
                    Icon(AppIcons.Wrench, contentDescription = "edit", tint = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Composable
private fun AlphabetIndex(availableLetters: Set<String>, onLetterClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .width(20.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FULL_ALPHABET.forEach { letter ->
            val active = letter in availableLetters
            Text(
                text = letter,
                color = if (active) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onLetterClick(letter) }
                    .padding(vertical = 1.dp)
            )
        }
    }
}