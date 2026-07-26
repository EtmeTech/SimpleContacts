package tech.etme.contacts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import tech.etme.contacts.data.SystemContactsRepository
import tech.etme.contacts.model.Contact
import tech.etme.contacts.ui.components.AppTab
import tech.etme.contacts.ui.components.BottomNavBar
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.screens.ContactListScreen
import tech.etme.contacts.ui.screens.EditContactScreen
import tech.etme.contacts.ui.screens.HighlightsScreen
import tech.etme.contacts.ui.screens.ManageScreen
import tech.etme.contacts.ui.theme.Accent
import tech.etme.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsTheme {
                ContactsApp()
            }
        }
    }
}

private sealed class Screen {
    object List : Screen()
    object New : Screen()
    data class Edit(val contact: Contact) : Screen()
}

// helper function to check if both READ_CONTACTS and WRITE_CONTACTS permissions are granted
private fun checkPermissions(context: Context): Boolean {
    val readGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED

    val writeGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED

    return readGranted && writeGranted
}

@Composable
fun ContactsApp() {
    val context = LocalContext.current
    val repository = remember { SystemContactsRepository(context) }

    var hasPermission by remember { mutableStateOf(checkPermissions(context)) }
    // Request permissions using ActivityResultContracts.RequestMultiplePermissions
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions[Manifest.permission.READ_CONTACTS] == true &&
                permissions[Manifest.permission.WRITE_CONTACTS] == true
    }

    val contacts = remember { mutableStateListOf<Contact>() }

    fun reload() {
        if (hasPermission) {
            contacts.clear()
            contacts.addAll(repository.loadAllContacts())
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) reload()
    }

    var screen by remember { mutableStateOf<Screen>(Screen.List) }
    var tab by rememberSaveable { mutableStateOf(AppTab.CONTACTS) }

    if (!hasPermission) {
        PermissionRequestScreen(
            onRequest = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                    )
                )
            }
        )
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (screen is Screen.List) {
                BottomNavBar(selected = tab, onSelect = { tab = it })
            }
        },
        floatingActionButton = {
            if (screen is Screen.List && tab == AppTab.CONTACTS) {
                FloatingActionButton(
                    onClick = { screen = Screen.New },
                    containerColor = Accent,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = AppIcons.Add, contentDescription = "Add contact")
                }
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = screen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(150)) },
            label = "screen"
        ) { current ->
            when (current) {
                is Screen.List -> when (tab) {
                    AppTab.CONTACTS -> ContactListScreen(
                        contacts = contacts,
                        onOpenContact = { contact -> screen = Screen.Edit(contact) }
                    )
                    AppTab.HIGHLIGHTS -> HighlightsScreen(
                        contacts = contacts,
                        onOpenContact = { contact -> screen = Screen.Edit(contact) }
                    )
                    AppTab.MANAGE -> ManageScreen(
                        contactCount = contacts.size,
                        onResync = { reload() },
                        onDeleteAllLocal = {
                            contacts.filter { it.systemContactId == null }.forEach { contacts.remove(it) }
                        }
                    )
                }

                is Screen.New -> EditContactScreen(
                    existing = null,
                    onBack = { screen = Screen.List },
                    onSave = { contact ->
                        repository.insertContact(contact)
                        reload()
                        screen = Screen.List
                    }
                )

                is Screen.Edit -> EditContactScreen(
                    existing = current.contact,
                    onBack = { screen = Screen.List },
                    onSave = { updated ->
                        repository.updateContact(updated)
                        reload()
                        screen = Screen.List
                    },
                    onDelete = { toDelete ->
                        repository.deleteContact(toDelete)
                        reload()
                        screen = Screen.List
                    }
                )
            }
        }
    }
}

@Composable
private fun PermissionRequestScreen(onRequest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = AppIcons.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            text = "This app reads and saves contacts using your device's Contacts app.",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Button(
            onClick = onRequest,
            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.White)
        ) {
            Text("Grant contacts access")
        }
    }
}