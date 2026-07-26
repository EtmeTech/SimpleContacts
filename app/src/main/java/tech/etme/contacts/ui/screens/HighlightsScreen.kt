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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.etme.contacts.model.Contact
import tech.etme.contacts.ui.components.ContactAvatar
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.theme.Accent

@Composable
fun HighlightsScreen(
    contacts: List<Contact>,
    onOpenContact: (Contact) -> Unit
) {
    val favorites = contacts.filter { it.favorite }.sortedBy { it.fullName.lowercase() }

    var expandedContactId by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorites",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = AppIcons.StarOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No highlights yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap the star on a contact to add it here",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites, key = { it.id }) { contact ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                            .clickable {
                                expandedContactId = if (expandedContactId == contact.id) null else contact.id
                            }
                            .padding(12.dp)
                    ) {
                        // header
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ContactAvatar()
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(contact.fullName, color = MaterialTheme.colorScheme.onSurface, fontSize = 17.sp)
                                if (contact.phone.isNotBlank()) {
                                    Text(contact.phone, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                                }
                            }
                            Icon(AppIcons.StarFilled, contentDescription = null, tint = Accent, modifier = Modifier.size(18.dp))
                        }

                        // 3 button menu
                        if (expandedContactId == contact.id) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // call
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
                                IconButton(onClick = { onOpenContact(contact) }) {
                                    Icon(AppIcons.Wrench, contentDescription = "edit", tint = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(96.dp)) }
            }
        }
    }
}