package com.diblo.hilldesk

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BatteryLoadingView(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 300)
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(120.dp, 200.dp)) {
            val bodyWidth = 120.dp.toPx()
            val bodyHeight = 200.dp.toPx()
            val cornerRadius = 16.dp.toPx()
            val tipWidth = 48.dp.toPx()
            val tipHeight = 20.dp.toPx()
            val strokeWidth = 4.dp.toPx()

            val bodyPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, tipHeight, bodyWidth, bodyHeight + tipHeight),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                )
            }

            drawPath(
                path = bodyPath,
                color = Color(0xFF334155),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            val tipPath = Path().apply {
                val tipLeft = (bodyWidth - tipWidth) / 2
                val tipTop = 0f
                val tipRight = tipLeft + tipWidth
                val tipBottom = tipHeight
                addRoundRect(
                    RoundRect(
                        rect = Rect(tipLeft, tipTop, tipRight, tipBottom),
                        topLeft = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        topRight = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                )
            }
            drawPath(
                path = tipPath,
                color = Color(0xFF334155),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            val fillPath = Path().apply {
                val fillTop = tipHeight + strokeWidth + 4.dp.toPx()
                val fillBottom = tipHeight + bodyHeight - strokeWidth - 4.dp.toPx()
                val fillLeft = strokeWidth + 4.dp.toPx()
                val fillRight = bodyWidth - strokeWidth - 4.dp.toPx()
                val currentFillBottom = fillTop + (fillBottom - fillTop) * animatedProgress.value
                addRoundRect(
                    RoundRect(
                        rect = Rect(fillLeft, currentFillBottom, fillRight, fillBottom),
                        bottomLeft = CornerRadius(cornerRadius - 4.dp.toPx(), cornerRadius - 4.dp.toPx()),
                        bottomRight = CornerRadius(cornerRadius - 4.dp.toPx(), cornerRadius - 4.dp.toPx())
                    )
                )
            }
            drawPath(
                path = fillPath,
                color = if (animatedProgress.value >= 1f) Color(0xFF22C55E) else Color(0xFF3B82F6)
            )
        }

        Text(
            text = "${(animatedProgress.value * 100).toInt()}%",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
