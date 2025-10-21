package ph.edu.comteq.ronaldguiyablab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class ExhibitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var artwork by remember { mutableStateOf<Artwork?>(null) }

            LaunchedEffect(Unit) {
                artwork = loadFirstArtworkFromJson()
            }

            artwork?.let {
                ExhibitScreen(
                    title = it.title,
                    years = it.years,
                    bornAt = it.bornAt,
                    comment = it.comment
                )
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFFC107))
            }
        }
    }

    private suspend fun loadFirstArtworkFromJson(): Artwork? = withContext(Dispatchers.IO) {
        try {
            val inputStream = assets.open("artworks.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonText = reader.use { it.readText() }
            val jsonArray = JSONArray(jsonText)

            if (jsonArray.length() > 0) {
                val obj = jsonArray.getJSONObject(0)
                Artwork(
                    title = obj.getString("title"),
                    years = obj.getString("years"),
                    bornAt = obj.getString("born_at"),
                    comment = obj.getString("comment")
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

data class Artwork(
    val title: String,
    val years: String,
    val bornAt: String,
    val comment: String
)

@Composable
fun ExhibitScreen(title: String, years: String, bornAt: String, comment: String) {
    val imgRes = when (title) {
        "Mona Lisa" -> R.drawable.mona_lisa
        "Lady Ermine" -> R.drawable.lady_ermine
        "Litta Madonna" -> R.drawable.litta_madonna
        else -> R.drawable.mona_lisa
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                    )
                )
        )

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC107))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$years, $bornAt",
                        fontSize = 14.sp,
                        color = Color(0xFF555555)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Comment section with quote beside comment
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.quote),
                    contentDescription = "Quote icon",
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 6.dp)
                )
                Text(
                    text = comment,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            // Arrow icon below
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrows),
                    contentDescription = "Arrow icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(33.dp)
                        .background(
                            color = Color(0xFF1C1C1C),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExhibitPagePreview() {
    ExhibitScreen(
        title = "Lady Ermine",
        years = "c. 1489–91",
        bornAt = "Milan, Italy",
        comment = "It is a captivating image of exquisite elegance and reveals the artistic genius of Leonardo's incomparable creative mind."
    )
}
