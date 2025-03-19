package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksComponentTextVisualTransfHighlighting(var selectedtext: String, var selectedTextBefore: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            highLightSelectedText(text.text),
            OffsetMapping.Identity)
    }

    private fun highLightSelectedText(text:String): AnnotatedString {
        val startIndex = selectedTextBefore.length;
        val endIndex = startIndex + selectedtext.length
        return buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    background = Color.Yellow,
                    textDecoration = TextDecoration.None,
                ),
                start = startIndex, end = endIndex
            )
        }
    }
}