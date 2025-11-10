package com.example.lab_week_09.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// UI Element for displaying a title
@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(
        text = text,
        color = MaterialTheme.colorScheme.onBackground
    )
}

// Title text using Material3 typography
@Composable
fun TitleText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 22.sp // ✅ perbesar sedikit agar jelas di layar
        ),
        color = color
    )
}

// UI Element for displaying an item in list
@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(
        text = text,
        color = MaterialTheme.colorScheme.onBackground
    )
}

// Body text using larger style for readability
@Composable
fun ItemText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp // ✅ lebih besar dari bodySmall sebelumnya
        ),
        color = color
    )
}

// UI Element for displaying a primary button
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    CustomTextButton(
        text = text,
        textColor = Color.White,
        onClick = onClick
    )
}

// Custom button style with padding and larger text
@Composable
fun CustomTextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = textColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp // ✅ lebih besar agar tombol terlihat tegas
            )
        )
    }
}
