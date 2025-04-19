package io.github.hoaithu842.spotlight.ui.designsystem

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotsCollision(dotSize: Dp = 24.dp) {
    val maxOffset = 30f
    val delayUnit = 500 // it's better to use longer delay for this animation

    @Composable
    fun Dot(offset: Float) =
        Spacer(
            Modifier
                .size(dotSize)
                .offset(x = offset.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
        )

    val infiniteTransition = rememberInfiniteTransition()

    val offsetLeft by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = delayUnit * 3
                        0f at 0 with LinearEasing
                        -maxOffset at delayUnit / 2 with LinearEasing
                        0f at delayUnit
                    },
            ),
    )
    val offsetRight by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = delayUnit * 3
                        0f at delayUnit with LinearEasing
                        maxOffset at delayUnit + delayUnit / 2 with LinearEasing
                        0f at delayUnit * 2
                    },
            ),
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = maxOffset.dp),
    ) {
        val spaceSize = 2.dp

        Dot(offsetLeft)
        Spacer(Modifier.width(spaceSize))
        Dot(0f)
        Spacer(Modifier.width(spaceSize))
        Dot(offsetRight)
    }
}
