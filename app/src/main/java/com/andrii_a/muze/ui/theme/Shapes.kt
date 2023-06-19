package com.andrii_a.muze.ui.theme

import android.graphics.Matrix
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

val BottleCapShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 200f
        val baseHeight = 200f

        val path = Path().apply {
            moveTo(94.0848f, 1.4307f)
            cubicTo(97.7976f, -0.4769f, 102.2024f, -0.4769f, 105.9152f, 1.4307f)
            lineTo(121.4436f, 9.4088f)
            cubicTo(123.0832f, 10.2512f, 124.8838f, 10.7337f, 126.7249f, 10.8239f)
            lineTo(144.162f, 11.6789f)
            cubicTo(148.3311f, 11.8833f, 152.1458f, 14.0857f, 154.4074f, 17.594f)
            lineTo(163.8663f, 32.2675f)
            cubicTo(164.865f, 33.8168f, 166.1832f, 35.135f, 167.7325f, 36.1337f)
            lineTo(182.406f, 45.5926f)
            cubicTo(185.9143f, 47.8542f, 188.1167f, 51.6689f, 188.3212f, 55.838f)
            lineTo(189.1761f, 73.2751f)
            cubicTo(189.2664f, 75.1162f, 189.7488f, 76.9168f, 190.5912f, 78.5564f)
            lineTo(198.5694f, 94.0848f)
            cubicTo(200.4769f, 97.7976f, 200.4769f, 102.2024f, 198.5694f, 105.9152f)
            lineTo(190.5912f, 121.4436f)
            cubicTo(189.7488f, 123.0832f, 189.2664f, 124.8838f, 189.1761f, 126.7249f)
            lineTo(188.3212f, 144.162f)
            cubicTo(188.1167f, 148.3311f, 185.9143f, 152.1458f, 182.406f, 154.4074f)
            lineTo(167.7325f, 163.8663f)
            cubicTo(166.1832f, 164.865f, 164.865f, 166.1832f, 163.8663f, 167.7325f)
            lineTo(154.4074f, 182.406f)
            cubicTo(152.1458f, 185.9143f, 148.3311f, 188.1167f, 144.162f, 188.3212f)
            lineTo(126.7249f, 189.1761f)
            cubicTo(124.8838f, 189.2664f, 123.0832f, 189.7488f, 121.4436f, 190.5912f)
            lineTo(105.9152f, 198.5694f)
            cubicTo(102.2024f, 200.4769f, 97.7976f, 200.4769f, 94.0848f, 198.5694f)
            lineTo(78.5564f, 190.5912f)
            cubicTo(76.9168f, 189.7488f, 75.1162f, 189.2664f, 73.2751f, 189.1761f)
            lineTo(55.838f, 188.3212f)
            cubicTo(51.6689f, 188.1167f, 47.8542f, 185.9143f, 45.5926f, 182.406f)
            lineTo(36.1337f, 167.7325f)
            cubicTo(35.135f, 166.1832f, 33.8168f, 164.865f, 32.2675f, 163.8663f)
            lineTo(17.594f, 154.4074f)
            cubicTo(14.0857f, 152.1458f, 11.8833f, 148.3311f, 11.6789f, 144.162f)
            lineTo(10.8239f, 126.7249f)
            cubicTo(10.7337f, 124.8838f, 10.2512f, 123.0832f, 9.4088f, 121.4436f)
            lineTo(1.4307f, 105.9152f)
            cubicTo(-0.4769f, 102.2024f, -0.4769f, 97.7976f, 1.4307f, 94.0848f)
            lineTo(9.4088f, 78.5564f)
            cubicTo(10.2512f, 76.9168f, 10.7337f, 75.1162f, 10.8239f, 73.2751f)
            lineTo(11.6789f, 55.838f)
            cubicTo(11.8833f, 51.6689f, 14.0857f, 47.8542f, 17.594f, 45.5926f)
            lineTo(32.2675f, 36.1337f)
            cubicTo(33.8168f, 35.135f, 35.135f, 33.8168f, 36.1337f, 32.2675f)
            lineTo(45.5926f, 17.594f)
            cubicTo(47.8542f, 14.0857f, 51.6689f, 11.8833f, 55.838f, 11.6789f)
            lineTo(73.2751f, 10.8239f)
            cubicTo(75.1162f, 10.7337f, 76.9168f, 10.2512f, 78.5564f, 9.4088f)
            lineTo(94.0848f, 1.4307f)
            close()
        }

        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}

val CloverShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 200f
        val baseHeight = 200f

        val path = Path().apply {
            moveTo(12f, 100f)
            cubicTo(12f, 76f, 0f, 77.6142f, 0f, 50f)
            cubicTo(0f, 22.3858f, 22.3858f, 0f, 50f, 0f)
            cubicTo(77.6142f, 0f, 76f, 12f, 100f, 12f)
            cubicTo(124f, 12f, 122.3858f, 0f, 150f, 0f)
            cubicTo(177.6142f, 0f, 200f, 22.3858f, 200f, 50f)
            cubicTo(200f, 77.6142f, 188f, 76f, 188f, 100f)
            cubicTo(188f, 124f, 200f, 122.3858f, 200f, 150f)
            cubicTo(200f, 177.6142f, 177.6142f, 200f, 150f, 200f)
            cubicTo(122.3858f, 200f, 124f, 188f, 100f, 188f)
            cubicTo(76f, 188f, 77.6142f, 200f, 50f, 200f)
            cubicTo(22.3858f, 200f, 0f, 177.6142f, 0f, 150f)
            cubicTo(0f, 122.3858f, 12f, 124f, 12f, 100f)
            close()
        }

        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}