package tech.etme.contacts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tech.etme.contacts.ui.icons.AppIcons
import tech.etme.contacts.ui.theme.AvatarPink
import tech.etme.contacts.ui.theme.AvatarPinkOnColor

@Composable
fun ContactAvatar(size: Dp = 46.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(AvatarPink, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = AppIcons.Person,
            contentDescription = null,
            tint = AvatarPinkOnColor,
            modifier = Modifier.size(size / 1.8f)
        )
    }
}
