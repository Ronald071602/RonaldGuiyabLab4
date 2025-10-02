package ph.edu.comteq.ronaldguiyablab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryPage()
        }
    }
}

@Composable
fun GalleryPage() {
    Text(text = "Gallery Screen", color = Color.Black)
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GalleryPagePreview() {
    GalleryPage()
}
