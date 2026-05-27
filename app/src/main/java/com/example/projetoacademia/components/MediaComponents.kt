package com.example.projetoacademia.components

import android.net.Uri
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ImagePickerField(
    label: String,
    imageUri: String,
    onImageSelected: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImageSelected(uri.toString())
        }
    }

    Column {
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = label)
        }

        if (imageUri.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            MediaImagePreview(imageUri = imageUri)
        }
    }
}

@Composable
fun VideoPickerField(
    label: String,
    videoUri: String,
    onVideoSelected: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onVideoSelected(uri.toString())
        }
    }

    Column {
        Button(
            onClick = { launcher.launch("video/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = label)
        }

        if (videoUri.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Vídeo selecionado",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun MediaImagePreview(
    imageUri: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageURI(Uri.parse(imageUri))
            }
        },
        update = { imageView ->
            imageView.setImageURI(Uri.parse(imageUri))
        },
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(18.dp))
    )
}
