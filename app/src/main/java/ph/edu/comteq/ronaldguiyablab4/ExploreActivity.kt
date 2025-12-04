package ph.edu.comteq.ronaldguiyablab4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ExploreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExplorePage()
        }
    }
}

@Composable
fun ExplorePage() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Explore",
                fontSize = 28.sp,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Upcoming Event + Tickets
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Upcoming Event", color = Color.White, fontSize = 16.sp)

                Text(
                    text = "Tickets >",
                    color = Color(0xFFD4AF37),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        val intent = Intent(context, TicketingActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Event Image
            Image(
                painter = painterResource(id = R.drawable.renaissance),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Event Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("10 OCT", color = Color.White, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Renaissance Exhibition", color = Color.White, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("9:00 AM - 6:00 PM", color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Indulge in the rich tapestry of Renaissance art",
                        color = Color(0xFFD4AF37)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("+33 (0)1 23 45 67 89", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visit Gallery Button
            Button(
                onClick = {
                    val intent = Intent(context, ArtistsActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37)
                )
            ) {
                Text(text = "Visit Gallery", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExplorePagePreview() {
    ExplorePage()
}
