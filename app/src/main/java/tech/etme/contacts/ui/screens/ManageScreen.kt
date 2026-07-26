package tech.etme.contacts.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManageScreen(
    contactCount: Int,
    onResync: () -> Unit,
    onDeleteAllLocal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "App Information",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        InfoRow(label = "Developer", value = "etme.tech")
        InfoRow(label = "Version", value = "1.0.6")
        InfoRow(
            label = "Privacy & Contacts",
            value = "This app reads and saves contacts directly on your device. No data is transmitted to external servers."
        )
        InfoRow(label = "License", value = "MIT License")


        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = "$label: $value",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}