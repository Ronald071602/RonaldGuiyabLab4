package ph.edu.comteq.ronaldguiyablab4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.ronaldguiyablab4.ui.theme.RonaldGuiyabLab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RonaldGuiyabLab4Theme {
                Homepage()
            }
        }
    }
}

@Composable
fun Homepage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.louvre), // Replace with your image name
            contentDescription = "Louvre Museum",
            modifier = Modifier
                .align(Alignment.Center)
                .size(500.dp), // Adjust size as needed
            contentScale = ContentScale.Fit
        )

        // Dark overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo at the top
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your logo
                contentDescription = "Gallery Logo",
                modifier = Modifier
                    .size(140.dp)
                    .padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Main title
            Text(
                text = "Experience Art",
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Description text
            Text(
                text = "We are thrilled to invite you to join us for\nan extraordinary event that will immerse\nyou in the world of art.",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Explore Now button
            Button(
                onClick = {
                    val intent = Intent(context, ExploreActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Explore Now",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    RonaldGuiyabLab4Theme {
        Homepage()
    }
}