package ph.edu.comteq.ronaldguiyablab4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ArtistsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ArtistsPage() }
    }
}

data class Artist(
    val name: String,
    val bornAt: String,
    val diedAt: String,
    val avatar: Int,
    val artworks: List<ArtworkDetail>
)

data class ArtworkDetail(
    val imageRes: Int,
    val title: String,
    val years: String,
    val bornAt: String,
    val comment: String
)

@Composable
fun ArtistsPage() {
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    // Track selected tab
    var selectedTab by remember { mutableStateOf("Artists") }

    val artists = listOf(
        Artist(
            name = "Leonardo da Vinci",
            bornAt = "1452",
            diedAt = "1519",
            avatar = R.drawable.leonardo_da_vinci,
            artworks = listOf(
                ArtworkDetail(
                    imageRes = R.drawable.mona_lisa,
                    title = "Mona Lisa",
                    years = "c. 1503-19",
                    bornAt = "Florence, Italy",
                    comment = "The best known, the most visited, the most written about, the most parodied work of art in the world."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.lady_ermine,
                    title = "Lady Ermine",
                    years = "c. 1489-91",
                    bornAt = "Milan, Italy",
                    comment = "A captivating image of exquisite elegance that showcases Leonardo's incomparable creative mind."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.litta_madonna,
                    title = "Litta Madonna",
                    years = "c. 1490",
                    bornAt = "Italy",
                    comment = "Highlights the Renaissance devotion to motherhood with serene intimacy."
                )
            )
        ),
        Artist(
            name = "Michelangelo",
            bornAt = "1475",
            diedAt = "1564",
            avatar = R.drawable.michelangelo,
            artworks = listOf(
                ArtworkDetail(
                    imageRes = R.drawable.david,
                    title = "David",
                    years = "1501-04",
                    bornAt = "Florence, Italy",
                    comment = "Michelangelo's marble masterpiece capturing heroic resolve before facing Goliath."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.torment_of_saint_anthony,
                    title = "The Torment of Saint Anthony",
                    years = "c. 1487",
                    bornAt = "Florence, Italy",
                    comment = "An early painting filled with vivid creatures swirling around the steadfast saint."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.delphic_sibyl,
                    title = "Delphic Sibyl",
                    years = "1508-12",
                    bornAt = "Rome, Italy",
                    comment = "A dynamic fresco figure from the Sistine Chapel ceiling foretelling divine wisdom."
                )
            )
        ),
        Artist(
            name = "Gustav Klimt",
            bornAt = "1862",
            diedAt = "1918",
            avatar = R.drawable.gustav_klimt,
            artworks = listOf(
                ArtworkDetail(
                    imageRes = R.drawable.adele_bloch_bauer,
                    title = "Portrait of Adele Bloch-Bauer I",
                    years = "1907",
                    bornAt = "Vienna, Austria",
                    comment = "Gold leaf opulence that exemplifies Klimt's Vienna Secession glamour."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.lady_with_fan,
                    title = "Lady with Fan",
                    years = "1918",
                    bornAt = "Vienna, Austria",
                    comment = "A late work mixing Japanese motifs with Klimt's ornamental patterns."
                ),
                ArtworkDetail(
                    imageRes = R.drawable.the_kiss,
                    title = "The Kiss",
                    years = "1907-08",
                    bornAt = "Vienna, Austria",
                    comment = "Iconic embrace wrapped in mosaic shapes representing unity of love."
                )
            )
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Light overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80F5F0E1))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 25.dp)
        ) {
            // Title Texts
            Text(
                text = "Explore the art of",
                fontSize = 22.sp,
                fontFamily = PlayfairDisplay,
                color = Color(0xFF5C5042)
            )
            Text(
                text = "Renaissance",
                fontFamily = PlayfairDisplay,
                fontSize = 34.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Search Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White, RoundedCornerShape(30.dp))
                    .padding(horizontal = 15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Type to search...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.scan_icon),
                    contentDescription = "Scan",
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs Row with clickable highlight
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Artists",
                    color = if (selectedTab == "Artists") Color(0xFFD4AF37) else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = if (selectedTab == "Artists") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { selectedTab = "Artists" }
                )
                Text(
                    text = "Artworks",
                    color = if (selectedTab == "Artworks") Color(0xFFD4AF37) else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = if (selectedTab == "Artworks") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { selectedTab = "Artworks" }
                )
            }

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))


            if (selectedTab == "Artists") {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(artists) { artist ->
                        ArtistItem(
                            artist = artist,
                            onArtworkClick = { art ->
                                val intent = Intent(context, ExhibitActivity::class.java).apply {
                                    putExtra(ExhibitActivity.EXTRA_IMAGE, art.imageRes)
                                    putExtra(ExhibitActivity.EXTRA_TITLE, art.title)
                                    putExtra(ExhibitActivity.EXTRA_YEARS, art.years)
                                    putExtra(ExhibitActivity.EXTRA_BORN_AT, art.bornAt)
                                    putExtra(ExhibitActivity.EXTRA_COMMENT, art.comment)
                                    putExtra(ExhibitActivity.EXTRA_SELECTED_TITLE, art.title)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Artworks tab selected",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistItem(
    artist: Artist,
    onArtworkClick: (ArtworkDetail) -> Unit
) {
    Column {
        // Artist Name and Avatar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = artist.avatar),
                contentDescription = artist.name,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    fontFamily = Optima,
                    text = artist.name,
                    color = Color(0xFF3E2C1C),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${artist.bornAt} - ${artist.diedAt}",
                    fontFamily = Optima,
                    color = Color(0xFF8C7C6B),
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(artist.artworks) { art ->
                Image(
                    painter = painterResource(id = art.imageRes),
                    contentDescription = art.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 110.dp, height = 110.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onArtworkClick(art) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtistsPagePreview() {
    ArtistsPage()
}
