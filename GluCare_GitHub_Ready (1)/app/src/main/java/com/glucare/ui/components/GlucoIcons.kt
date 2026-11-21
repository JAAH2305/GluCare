package com.glucare.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.glucare.ui.theme.OlivePrimary
import com.glucare.ui.theme.TextSecondary

object GlucoIcons {

    val Mail: ImageVector = ImageVector.Builder(
        name = "Mail",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(4f, 6f)
        lineTo(20f, 6f)
        curveTo(21.1f, 6f, 22f, 6.9f, 22f, 8f)
        lineTo(22f, 16f)
        curveTo(22f, 17.1f, 21.1f, 18f, 20f, 18f)
        lineTo(4f, 18f)
        curveTo(2.9f, 18f, 2f, 17.1f, 2f, 16f)
        lineTo(2f, 8f)
        curveTo(2f, 6.9f, 2.9f, 6f, 4f, 6f)
        close()
        moveTo(22f, 8f)
        lineTo(12f, 13f)
        lineTo(2f, 8f)
    }.build()

    val Lock: ImageVector = ImageVector.Builder(
        name = "Lock",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(19f, 11f)
        lineTo(5f, 11f)
        curveTo(3.9f, 11f, 3f, 11.9f, 3f, 13f)
        lineTo(3f, 20f)
        curveTo(3f, 21.1f, 3.9f, 22f, 5f, 22f)
        lineTo(19f, 22f)
        curveTo(20.1f, 22f, 21f, 21.1f, 21f, 20f)
        lineTo(21f, 13f)
        curveTo(21f, 11.9f, 20.1f, 11f, 19f, 11f)
        close()
        moveTo(7f, 11f)
        lineTo(7f, 7f)
        curveTo(7f, 4.24f, 9.24f, 2f, 12f, 2f)
        curveTo(14.76f, 2f, 17f, 4.24f, 17f, 7f)
        lineTo(17f, 11f)
    }.build()

    val Person: ImageVector = ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(20f, 21f)
        curveTo(20f, 16.58f, 16.42f, 13f, 12f, 13f)
        curveTo(7.58f, 13f, 4f, 16.58f, 4f, 21f)
        moveTo(12f, 11f)
        curveTo(14.21f, 11f, 16f, 9.21f, 16f, 7f)
        curveTo(16f, 4.79f, 14.21f, 3f, 12f, 3f)
        curveTo(9.79f, 3f, 8f, 4.79f, 8f, 7f)
        curveTo(8f, 9.21f, 9.79f, 11f, 12f, 11f)
        close()
    }.build()

    val Eye: ImageVector = ImageVector.Builder(
        name = "Eye",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(1f, 12f)
        curveTo(1f, 12f, 5f, 4f, 12f, 4f)
        curveTo(19f, 4f, 23f, 12f, 23f, 12f)
        curveTo(23f, 12f, 19f, 20f, 12f, 20f)
        curveTo(5f, 20f, 1f, 12f, 1f, 12f)
        close()
        moveTo(12f, 15f)
        curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
        curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
        curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
        curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
        close()
    }.build()

    val EyeOff: ImageVector = ImageVector.Builder(
        name = "EyeOff",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(17.94f, 17.94f)
        curveTo(16.23f, 19.24f, 14.19f, 20f, 12f, 20f)
        curveTo(5f, 20f, 1f, 12f, 1f, 12f)
        curveTo(2.24f, 9.68f, 4.12f, 7.68f, 6.42f, 6.42f)
        moveTo(9.9f, 4.24f)
        curveTo(10.58f, 4.08f, 11.28f, 4f, 12f, 4f)
        curveTo(19f, 4f, 23f, 12f, 23f, 12f)
        curveTo(22.12f, 13.67f, 20.89f, 15.22f, 19.38f, 16.42f)
        moveTo(1f, 1f)
        lineTo(23f, 23f)
    }.build()

    val Shield: ImageVector = ImageVector.Builder(
        name = "Shield",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(12f, 22f)
        curveTo(12f, 22f, 20f, 18f, 20f, 12f)
        lineTo(20f, 5f)
        lineTo(12f, 2f)
        lineTo(4f, 5f)
        lineTo(4f, 12f)
        curveTo(4f, 18f, 12f, 22f, 12f, 22f)
        close()
    }.build()

    val ArrowBack: ImageVector = ImageVector.Builder(
        name = "ArrowBack",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(TextSecondary),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(19f, 12f)
        lineTo(5f, 12f)
        moveTo(12f, 19f)
        lineTo(5f, 12f)
        lineTo(12f, 5f)
    }.build()

    val Leaf: ImageVector = ImageVector.Builder(
        name = "Leaf",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(OlivePrimary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(11f, 20f)
        curveTo(16.52f, 20f, 21f, 15.52f, 21f, 10f)
        curveTo(21f, 4.48f, 11f, 3f, 11f, 3f)
        curveTo(11f, 3f, 3f, 4.48f, 3f, 10f)
        curveTo(3f, 15.52f, 7.48f, 20f, 11f, 20f)
        close()
        moveTo(11f, 20f)
        lineTo(11f, 10f)
        moveTo(11f, 10f)
        curveTo(13.5f, 8.5f, 16f, 8f, 18f, 8f)
    }.build()

    val WaterDrop: ImageVector = ImageVector.Builder(
        name = "WaterDrop",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(OlivePrimary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(12f, 2.69f)
        curveTo(12f, 2.69f, 5f, 10.36f, 5f, 15f)
        curveTo(5f, 18.87f, 8.13f, 22f, 12f, 22f)
        curveTo(15.87f, 22f, 19f, 18.87f, 19f, 15f)
        curveTo(19f, 10.36f, 12f, 2.69f, 12f, 2.69f)
        close()
    }.build()

    val Heart: ImageVector = ImageVector.Builder(
        name = "Heart",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(OlivePrimary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(20.84f, 4.61f)
        curveTo(20.33f, 4.1f, 19.72f, 3.69f, 19.06f, 3.42f)
        curveTo(18.39f, 3.14f, 17.68f, 3f, 16.95f, 3f)
        curveTo(16.22f, 3f, 15.51f, 3.14f, 14.84f, 3.42f)
        curveTo(14.18f, 3.69f, 13.57f, 4.1f, 13.06f, 4.61f)
        lineTo(12f, 5.67f)
        lineTo(10.94f, 4.61f)
        curveTo(9.92f, 3.59f, 8.54f, 3.01f, 7.09f, 3.01f)
        curveTo(5.64f, 3.01f, 4.26f, 3.59f, 3.24f, 4.61f)
        curveTo(2.22f, 5.63f, 1.65f, 7.01f, 1.65f, 8.46f)
        curveTo(1.65f, 9.91f, 2.22f, 11.29f, 3.24f, 12.31f)
        lineTo(12f, 21.07f)
        lineTo(20.76f, 12.31f)
        curveTo(21.27f, 11.8f, 21.68f, 11.19f, 21.95f, 10.53f)
        curveTo(22.23f, 9.86f, 22.37f, 9.15f, 22.37f, 8.42f)
        curveTo(22.37f, 7.69f, 22.23f, 6.98f, 21.95f, 6.31f)
        curveTo(21.68f, 5.65f, 21.27f, 5.04f, 20.84f, 4.61f)
        close()
    }.build()

    val Sparkle: ImageVector = ImageVector.Builder(
        name = "Sparkle",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = androidx.compose.ui.graphics.SolidColor(OlivePrimary),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(12f, 3f)
        curveTo(12f, 8f, 16f, 12f, 21f, 12f)
        curveTo(16f, 12f, 12f, 16f, 12f, 21f)
        curveTo(12f, 16f, 8f, 12f, 3f, 12f)
        curveTo(8f, 12f, 12f, 8f, 12f, 3f)
        close()
    }.build()
}

@Composable
fun CheckmarkBadge(
    modifier: Modifier = Modifier,
    color: Color = OlivePrimary
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        drawCircle(color = color, radius = radius)

        val checkPath = Path().apply {
            val w = size.width
            val h = size.height
            moveTo(w * 0.3f, h * 0.5f)
            lineTo(w * 0.45f, h * 0.65f)
            lineTo(w * 0.7f, h * 0.38f)
        }
        drawPath(
            path = checkPath,
            color = Color.White,
            style = Stroke(
                width = size.width * 0.12f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
