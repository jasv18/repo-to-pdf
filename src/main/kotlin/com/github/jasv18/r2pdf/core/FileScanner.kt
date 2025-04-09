package com.github.jasv18.r2pdf.core

import java.nio.file.Files
import java.nio.file.Path

class FileScanner (
    private val startPath: Path,
    private val includePatterns: List<String>,
    private val excludePatterns: List<String>
) {
    fun scan(): MutableList<Path> {
        val fileFilter = FileFilter(startPath, includePatterns, excludePatterns)
        val customFileVisitor = CustomFileVisitor(fileFilter)
        Files.walkFileTree(startPath, customFileVisitor)
        return customFileVisitor.getListOfFiles()
    }
}