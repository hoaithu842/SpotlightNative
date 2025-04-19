package io.github.hoaithu842.spotlight.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PremiumScreen() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp) // for sticky bottom bar space
        ) {
            HeaderSection()
            BenefitListSection()
            PlanListSection()
        }
    }
}

@Composable
fun HeaderSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Curious about Premium?\nYou're in luck",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Cyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Limited time offer",
                color = Color.Cyan,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You can't upgrade to Premium in the app. We know, it's not ideal.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun BenefitListSection() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Why join Premium?", color = Color.White, fontWeight = FontWeight.Bold)

        val benefits = listOf(
            "Ad-free music listening" to Icons.Default.Clear,
            "Download to listen offline" to Icons.Default.Check,
            "Play songs in any order" to Icons.Default.Refresh,
            "High audio quality" to Icons.Default.Lock,
            "Listen with friends in real time" to Icons.Default.Face,
            "Organize listening queue" to Icons.Default.List
        )

        Spacer(modifier = Modifier.height(8.dp))

        benefits.forEach { (text, icon) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, color = Color.White)
            }
        }
    }
}

@Composable
fun PlanListSection() {
    val plans = listOf(
        PlanUi(
            "4 months offer", "Individual", listOf(
                "1 Premium account", "Cancel anytime", "Subscribe or one-time payment"
            )
        ),
        PlanUi(
            "3 months offer", "Student", listOf(
                "1 verified Premium account",
                "Discount for eligible students",
                "Cancel anytime",
                "Subscribe or one-time payment"
            )
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Available plans", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        plans.forEach { plan ->
            PlanCard(plan)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun PlanCard(plan: PlanUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFEC9FA6), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(plan.offerLabel, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(plan.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        plan.features.forEach {
            Text("• $it", color = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "You can't upgrade to Premium in the app. We know, it's not ideal.",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

data class PlanUi(
    val offerLabel: String,
    val title: String,
    val features: List<String>,
)
