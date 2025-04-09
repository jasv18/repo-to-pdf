package com.github.jasv18.r2pdf.core

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import java.lang.IllegalArgumentException
import java.nio.file.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.isDirectory

class PdfGenerator(
    private val pageSize: PDRectangle = DEFAULT_PAGE_SIZE,
    private val fontSize: Float = DEFAULT_FONT_SIZE,
    private val leading: Leading = DEFAULT_LEADING,
    private val margin: Margin = DEFAULT_MARGIN,
    private val fontName: Standard14Fonts.FontName = DEFAULT_FONT_NAME
): ReportGenerator {
    companion object {
        const val DEFAULT_FONT_SIZE: Float = 12F
        val DEFAULT_LEADING: Leading = Leading.DEFAULT
        val DEFAULT_PAGE_SIZE: PDRectangle = PDRectangle.LETTER
        val DEFAULT_MARGIN: Margin = Margin(50F, 50F, 50F, 50F)
        val DEFAULT_FONT_NAME: Standard14Fonts.FontName = Standard14Fonts.FontName.COURIER
    }
    private val pdDocument = PDDocument()
    private val font = PDType1Font(fontName)
    private val columnWidth = pageSize.width - margin.left - margin.right
    private val lineHeight = fontSize * leading.lineSpacing
    private val usefulPageSpace = pageSize.height - margin.top - margin.bottom

    /**
     * Calculates the text width in user space units
     * @param fontType The type of font (PDType1Font)
     * @param fontSize The font size in points.
     * @param text The text to measure
     * @return The text width in user space units
     * */
    private fun calculateTextWidth(fontType: PDType1Font, fontSize: Float, text: String): Float =
        try { (fontType.getStringWidth(text) * fontSize) / 1000F } catch (e: IllegalArgumentException) { 0F }

    /**
     * Calculates the number of chars per width
     * @param fontType The type of font (PDType1Font)
     * @param fontSize The font size in points.
     * @param text The text to measure.
     * @param columnWidth The column width in user space units.
     * @param safetyMargin The safety margin to ovoid text overflow and add continuation symbols.
     * @return The number of characters that fit in the column width, or null if it cannot be calculated.
     * Returns null if the text width is zero or if the resulting value is less than 0.
     * */
    private fun calculateCharsPerWidth(
        fontType: PDType1Font,
        fontSize: Float,
        text: String,
        columnWidth: Float,
        safetyMargin: Int = 2
    ): Int? {
        if (text.isBlank()) {
            return null
        }

        val textWith = calculateTextWidth(fontType, fontSize, text)
        if (textWith == 0F) {
            return null
        }
        val charsPerWidth = ((columnWidth * text.length) / textWith).toInt() - safetyMargin
        return if (charsPerWidth < 0) { null } else { charsPerWidth }
    }

    override fun addFile(baseDir: Path, file: Path) {
        val parentDirectory = if (baseDir.isDirectory()) baseDir else baseDir.parent
        val relativePath = parentDirectory.relativize(file.parent)
        val pdPage = PDPage(pageSize)
        var remainingPageSpace = usefulPageSpace
        file.bufferedReader().use { reader ->
            pdDocument.addPage(pdPage)
            var cs = PDPageContentStream(pdDocument, pdPage)
            cs.setFont(font, fontSize)
            cs.setLeading(lineHeight)
            cs.beginText()
            cs.newLineAtOffset(margin.left,pageSize.height - margin.top)
            cs.showText("Context Path: ..\\${parentDirectory.last()}")
            cs.newLine()
            cs.showText("Location: ${if (relativePath.toString().isEmpty()) "." else relativePath}")
            cs.newLine()
            cs.showText("File: ${file.fileName}")
            cs.newLine()
            cs.newLine()
            remainingPageSpace -= lineHeight.times(3)
            reader.useLines { lines ->
                for (line in lines) {
                    val stringLine = line.trimEnd()
                    if (stringLine.isBlank()) {
                        continue
                    }
                    val result = calculateCharsPerWidth(font, fontSize, stringLine, columnWidth) ?: continue
                    val chunkedString = stringLine.chunked( result )
                    val lastIndexValueOfChunked = chunkedString.lastIndex
                    chunkedString.forEachIndexed { index, s ->
                        if (remainingPageSpace < lineHeight) {
                            remainingPageSpace = usefulPageSpace
                            cs.endText()
                            cs.close()
                            val newPdPage = PDPage(pageSize)
                            pdDocument.addPage(newPdPage)
                            cs = PDPageContentStream(pdDocument, newPdPage)
                            cs.setFont(font, fontSize)
                            cs.setLeading(lineHeight)
                            cs.beginText()
                            cs.newLineAtOffset(margin.left,pageSize.height - margin.top)
                        }
                        val textToAdd = when {
                            index == lastIndexValueOfChunked -> s
                            else -> "$s \\"
                        }
                        cs.showText(textToAdd)
                        cs.newLine()
                        remainingPageSpace -= lineHeight
                    }
                }
            }
            cs.endText()
            cs.close()
        }
    }

    override fun save(output: Path) {
        pdDocument.save(output.toFile())
    }

    override fun close() {
        pdDocument.close()
    }
}