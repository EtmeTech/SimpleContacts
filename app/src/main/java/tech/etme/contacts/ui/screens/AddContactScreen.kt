package tech.etme.contacts.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.etme.contacts.model.Contact
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.theme.Accent
import tech.etme.contacts.ui.theme.AppOutline

@Composable
fun EditContactScreen(
    existing: Contact? = null,
    onBack: () -> Unit,
    onSave: (Contact) -> Unit,
    onDelete: ((Contact) -> Unit)? = null
) {
    var firstName by rememberSaveable(existing) { mutableStateOf(existing?.firstName ?: "") }
    var lastName by rememberSaveable(existing) { mutableStateOf(existing?.lastName ?: "") }
    var favorite by rememberSaveable(existing) { mutableStateOf(existing?.favorite ?: false) }

    var email by rememberSaveable(existing) { mutableStateOf(existing?.email ?: "") }
    var phone by rememberSaveable(existing) { mutableStateOf(existing?.phone ?: "") }

    var street by rememberSaveable(existing) { mutableStateOf(existing?.street ?: "") }
    var houseNumber by rememberSaveable(existing) { mutableStateOf(existing?.houseNumber ?: "") }
    var postalCode by rememberSaveable(existing) { mutableStateOf(existing?.postalCode ?: "") }
    var city by rememberSaveable(existing) { mutableStateOf(existing?.city ?: "") }
    var region by rememberSaveable(existing) { mutableStateOf(existing?.region ?: "") }
    var country by rememberSaveable(existing) { mutableStateOf(existing?.country ?: "") }

    var moreFieldsExpanded by rememberSaveable { mutableStateOf(false) }
    var middleName by rememberSaveable(existing) { mutableStateOf(existing?.middleName ?: "") }
    var nickname by rememberSaveable(existing) { mutableStateOf(existing?.nickname ?: "") }
    var company by rememberSaveable(existing) { mutableStateOf(existing?.company ?: "") }
    var birthday by rememberSaveable(existing) { mutableStateOf(existing?.birthday ?: "") }
    var phone2 by rememberSaveable(existing) { mutableStateOf(existing?.phone2 ?: "") }

    fun buildContact(): Contact = Contact(
        id = existing?.id ?: java.util.UUID.randomUUID().toString(),
        systemContactId = existing?.systemContactId,
        firstName = firstName.trim(),
        lastName = lastName.trim(),
        email = email.trim(),
        street = street.trim(),
        houseNumber = houseNumber.trim(),
        postalCode = postalCode.trim(),
        city = city.trim(),
        region = region.trim(),
        country = country.trim(),
        phone = phone.trim(),
        favorite = favorite,
        middleName = middleName.trim(),
        nickname = nickname.trim(),
        company = company.trim(),
        birthday = birthday.trim(),
        phone2 = phone2.trim()
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(AppIcons.Back, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    text = if (existing == null) "New Contact" else "Edit Contact",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { favorite = !favorite }) {
                    Icon(
                        imageVector = if (favorite) AppIcons.StarFilled else AppIcons.StarOutline,
                        contentDescription = "Favorite",
                        tint = if (favorite) Accent else MaterialTheme.colorScheme.onBackground
                    )
                }
                //changed position for better ui
                if (existing != null && onDelete != null) {
                    IconButton(onClick = { onDelete(existing) }) {
                        Icon(AppIcons.Trash, contentDescription = "Delete", tint = Accent)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FieldSection(icon = AppIcons.Person) {
                    LabeledField(firstName, { firstName = it }, "First name")
                    LabeledField(lastName, { lastName = it }, "Last name")
                }

                FieldSection(icon = AppIcons.Envelope) {
                    LabeledField(email, { email = it }, "Email")
                }

                FieldSection(icon = AppIcons.Phone) {
                    LabeledField(phone, { phone = it }, "Phone")
                }

                FieldSection(icon = AppIcons.Pin) {
                    LabeledField(street, { street = it }, "Street")
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        LabeledField(houseNumber, { houseNumber = it }, "House no.", modifier = Modifier.weight(1f))
                        LabeledField(postalCode, { postalCode = it }, "Postal code", modifier = Modifier.weight(1f))
                    }
                    LabeledField(city, { city = it }, "City")
                    LabeledField(region, { region = it }, "Region")
                    LabeledField(country, { country = it }, "Country")
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(18.dp))
                        .clickable { moreFieldsExpanded = !moreFieldsExpanded }
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "More fields",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = AppIcons.ChevronDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    AnimatedVisibility(visible = moreFieldsExpanded) {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            LabeledField(middleName, { middleName = it }, "Middle name")
                            LabeledField(nickname, { nickname = it }, "Nickname")
                            LabeledField(company, { company = it }, "Company")
                            LabeledField(birthday, { birthday = it }, "Birthday (YYYY-MM-DD)")
                            LabeledField(phone2, { phone2 = it }, "Second phone")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(96.dp))
            }
        }

        // save button position new for better access
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = { onSave(buildContact()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Color.White
                )
            ) {
                Text("SAVE", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun FieldSection(
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 14.dp, end = 12.dp)
                .width(22.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(18.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun LabeledField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Accent,
            unfocusedBorderColor = AppOutline,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = Accent,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = Accent
        )
    )
}
