package ph.edu.comteq.ronaldguiyablab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
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
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import ph.edu.comteq.ronaldguiyablab4.ui.theme.RonaldGuiyabLab4Theme

class ExhibitActivity : ComponentActivity() {

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_YEARS = "extra_years"
        const val EXTRA_BORN_AT = "extra_born_at"
        const val EXTRA_COMMENT = "extra_comment"
        const val EXTRA_SELECTED_TITLE = "extra_selected_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extrasArtwork = extractArtworkFromExtras()
        val selectedTitle = intent.getStringExtra(EXTRA_SELECTED_TITLE)

        setContent {
            RonaldGuiyabLab4Theme {
                var artworks by remember { mutableStateOf<List<Artwork>>(emptyList()) }
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    artworks = loadArtworksFromJson()
                    isLoading = false
                }

                val displayList = if (artworks.isEmpty() && extrasArtwork != null) {
                    listOf(extrasArtwork)
                } else {
                    artworks
                }

                when {
                    isLoading -> LoadingState()
                    displayList.isEmpty() -> EmptyState()
                    else -> ExhibitPagerScreen(
                        artworks = displayList,
                        initialTitle = selectedTitle ?: extrasArtwork?.title
                    )
                }
            }
        }
    }

    private fun extractArtworkFromExtras(): Artwork? {
        val passedImage = intent.getIntExtra(EXTRA_IMAGE, -1)
        val passedTitle = intent.getStringExtra(EXTRA_TITLE)
        val passedYears = intent.getStringExtra(EXTRA_YEARS)
        val passedBornAt = intent.getStringExtra(EXTRA_BORN_AT)
        val passedComment = intent.getStringExtra(EXTRA_COMMENT)

        return if (
            passedImage != -1 &&
            !passedTitle.isNullOrBlank() &&
            !passedYears.isNullOrBlank() &&
            !passedBornAt.isNullOrBlank() &&
            !passedComment.isNullOrBlank()
        ) {
            Artwork(
                title = passedTitle,
                years = passedYears,
                bornAt = passedBornAt,
                comment = passedComment,
                imageRes = passedImage
            )
        } else {
            null
        }
    }

    private suspend fun loadArtworksFromJson(): List<Artwork> = withContext(Dispatchers.IO) {
        try {
            val inputStream = assets.open("artworks.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonText = reader.use { it.readText() }
            val jsonArray = JSONArray(jsonText)

            buildList {
                for (index in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(index)
                    val title = obj.getString("title")
                    add(
                        Artwork(
                            title = title,
                            years = obj.getString("years"),
                            bornAt = obj.getString("born_at"),
                            comment = obj.getString("comment"),
                            imageRes = artworkImageForTitle(title)
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

data class Artwork(
    val title: String,
    val years: String,
    val bornAt: String,
    val comment: String,
    val imageRes: Int
)

private fun artworkImageForTitle(title: String): Int = when (title) {
    "Mona Lisa" -> R.drawable.mona_lisa
    "Lady Ermine" -> R.drawable.lady_ermine
    "Litta Madonna" -> R.drawable.litta_madonna
    else -> R.drawable.mona_lisa
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFFFFC107))
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No artworks available",
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExhibitPagerScreen(
    artworks: List<Artwork>,
    initialTitle: String?
) {
    if (artworks.isEmpty()) return

    val safeStartPage = remember(artworks, initialTitle) {
        val desired = initialTitle?.let { title ->
            artworks.indexOfFirst { it.title.equals(title, ignoreCase = true) }
        } ?: 0
        desired.coerceIn(0, artworks.lastIndex.coerceAtLeast(0))
    }

    val pagerState = rememberPagerState(initialPage = safeStartPage) {
        artworks.size
    }

    val reactivePage by remember {
        derivedStateOf {
            val offsetPage = pagerState.currentPage + pagerState.currentPageOffsetFraction
            offsetPage.roundToInt().coerceIn(0, pagerState.pageCount - 1)
        }
    }

    val currentArtwork by remember {
        derivedStateOf { artworks[reactivePage] }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            pageSpacing = 20.dp,
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) { page ->
            PetalArtworkCard(artwork = artworks[page])
        }

        Spacer(modifier = Modifier.height(24.dp))

        CommentSection(
            artwork = currentArtwork,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PetalArtworkCard(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.58f)
                .clip(ArchFrameShape)
        ) {
            Image(
                painter = painterResource(id = artwork.imageRes),
                contentDescription = artwork.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7B928))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = artwork.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF312312)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${artwork.years}, ${artwork.bornAt}",
                        fontSize = 14.sp,
                        color = Color(0xFF6C4A1D)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrows),
                        contentDescription = "More details",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

private val ArchFrameShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height
    moveTo(0f, height)
    lineTo(0f, height * 0.35f)
    quadraticBezierTo(
        width / 2f,
        0f,
        width,
        height * 0.35f
    )
    lineTo(width, height)
    close()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CommentSection(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                painter = painterResource(id = R.drawable.quote),
                contentDescription = "Quote icon",
                tint = Color(0xFFB0B0B0),
                modifier = Modifier
                    .size(26.dp)
                    .padding(end = 8.dp)
            )

            AnimatedContent(
                targetState = artwork.comment,
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith
                        fadeOut(animationSpec = tween(250))
                },
                label = "comment-change"
            ) { comment ->
                Text(
                    text = comment,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrows),
                contentDescription = "Arrow icon",
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color(0xFF1C1C1C),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExhibitPagePreview() {
    val sample = listOf(
        Artwork(
            title = "Lady Ermine",
            years = "c. 1489–91",
            bornAt = "Milan, Italy",
            comment = "It is a captivating image of exquisite elegance and reveals Leonardo's genius.",
            imageRes = R.drawable.lady_ermine
        ),
        Artwork(
            title = "Mona Lisa",
            years = "c. 1503-19",
            bornAt = "Florence, Italy",
            comment = "The most visited, written about, and parodied work of art in the world.",
            imageRes = R.drawable.mona_lisa
        )
    )

    ExhibitPagerScreen(
        artworks = sample,
        initialTitle = "Lady Ermine"
    )
}
