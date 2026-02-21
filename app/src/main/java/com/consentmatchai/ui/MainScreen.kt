package com.consentmatchai.ui
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
@Composable
fun MainScreen(context: Context) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var consent by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? -> imageUri = uri }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ConsentMatch AI", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ricerca inversa consenziente tramite Google Immagini.")
            }
        }
        Button(onClick = { launcher.launch("image/*") }) { Text("Seleziona immagine") }
        imageUri?.let { Image(painter = rememberAsyncImagePainter(it), contentDescription = null, modifier = Modifier.size(200.dp)) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = consent, onCheckedChange = { consent = it })
            Text("Confermo di avere il consenso")
        }
        Button(
            enabled = consent && imageUri != null,
            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://images.google.com/") }) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Avvia Ricerca Inversa") }
    }
}
