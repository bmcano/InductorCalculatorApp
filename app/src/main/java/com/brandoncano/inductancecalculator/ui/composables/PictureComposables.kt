package com.brandoncano.inductancecalculator.ui.composables

import android.graphics.Picture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

/**
 * Note: This is used when we want to be able to convert composables into a sharable image
 */

@Composable
fun DrawContent(picture: Picture, content: @Composable (ColumnScope.() -> Unit)) {
    Column(
        modifier = Modifier.drawWithCache {
            val width = this.size.width.toInt()
            val height = this.size.height.toInt()
            onDrawWithContent {
                val pictureCanvas = androidx.compose.ui.graphics.Canvas(
                    picture.beginRecording(width, height)
                )
                draw(this, this.layoutDirection, pictureCanvas, this.size) {
                    this@onDrawWithContent.drawContent()
                }
                picture.endRecording()
                drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
            }
        },
        content = content
    )
}
