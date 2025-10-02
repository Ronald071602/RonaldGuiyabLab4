package ph.edu.comteq.ronaldguiyablab4

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.Duration

class TicketingActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketingScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketingScreen(onBackClick: () -> Unit = {}) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now()
            .plus(Duration.ofDays(2)).toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= Instant.now()
                    .plus(Duration.ofDays(1)).toEpochMilli()
            }
        }
    )

    var generalAdmissionQty by remember { mutableStateOf(0) }
    var freeTicketQty by remember { mutableStateOf(0) }
    val pricePerTicket = 500
    val totalPrice = generalAdmissionQty * pricePerTicket

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFD29F1B))
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total: P$totalPrice",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlayfairDisplay,
                    color = Color.Black
                )
                Button(
                    onClick = { /* TODO: checkout */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        "Checkout",
                        fontSize = 20.sp,
                        fontFamily = PlayfairDisplay,
                        color = Color(0xFFD29F1B)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Museum",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                )
                Text(
                    "Official\nTicketing Service",
                    fontSize = 32.sp,
                    fontFamily = PlayfairDisplay,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
            }

            // Date Picker
            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                state = datePickerState,
                title = null,
                showModeToggle = false,
                headline = {
                    Text(
                        "1. Date to Visit",
                        fontSize = 22.sp,
                        fontFamily = PlayfairDisplay,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD29F1B)
                    )
                },
                colors = DatePickerDefaults.colors(
                    headlineContentColor = Color(0xFFD29F1B),
                    weekdayContentColor = Color(0xFFD29F1B),
                    containerColor = Color.Transparent,
                    dayContentColor = Color.White,
                    todayContentColor = Color(0xFFD29F1B),
                    todayDateBorderColor = Color(0xFFD29F1B),
                    selectedDayContainerColor = Color(0xFFD29F1B),
                    selectedDayContentColor = Color.Black,
                    disabledDayContentColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tickets Section
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(
                    "2. Number of Tickets",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlayfairDisplay,
                    color = Color(0xFFD29F1B),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // General Admission
                TicketRow(
                    title = "General Admission",
                    priceText = "P$pricePerTicket",
                    quantity = generalAdmissionQty,
                    onIncrease = { generalAdmissionQty++ },
                    onDecrease = { if (generalAdmissionQty > 0) generalAdmissionQty-- }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Free Tickets Description
                Text(
                    "Under 18s, Under 26s residents of the EEA,\nMuseum members, Professionals",
                    fontSize = 14.sp,
                    fontFamily = PlayfairDisplay,
                    color = Color.White,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // FREE Section
                FreeTicketRow(
                    freeTicketQty = freeTicketQty,
                    onIncrease = { freeTicketQty++ },
                    onDecrease = { if (freeTicketQty > 0) freeTicketQty-- }
                )
            }
        }
    }
}

@Composable
fun TicketRow(
    title: String,
    priceText: String,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                title,
                fontFamily = PlayfairDisplay,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                priceText,
                fontSize = 22.sp,
                fontFamily = PlayfairDisplay,
                color = Color(0xFFD29F1B),
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { onDecrease() },
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Text("−", fontSize = 24.sp, color = Color.White)
            }

            Text(
                "$quantity",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.widthIn(min = 30.dp),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { onIncrease() },
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Text("+", fontSize = 24.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun FreeTicketRow(
    freeTicketQty: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "FREE",
            fontSize = 24.sp,
            fontFamily = PlayfairDisplay,
            color = Color(0xFFD29F1B),
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { onDecrease() },
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Text("−", fontSize = 24.sp, color = Color.White)
            }

            Text(
                "$freeTicketQty",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.widthIn(min = 30.dp),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { onIncrease() },
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Text("+", fontSize = 24.sp, color = Color.White)
            }
        }
    }
}
