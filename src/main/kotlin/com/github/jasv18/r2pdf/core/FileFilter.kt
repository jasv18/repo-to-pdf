package com.github.jasv18.r2pdf.core

import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.PathMatcher

class FileFilter(
    private val startPath: Path,
    private val includePatterns: List<String>,
    private val excludePatterns: List<String>
) {

    private val fs = FileSystems.getDefault()
    private val includeMatchers: List<PathMatcher> by lazy {
        includePatterns.map { fs.getPathMatcher("glob:$it") }
    }
    private val excludeMatchers: List<PathMatcher> by lazy {
        excludePatterns.map { fs.getPathMatcher("glob:$it") }
    }

    fun shouldInclude(path: Path): Boolean {
        val relativePath = startPath.relativize(path)
        return includeMatchers.any { it.matches(relativePath) } && excludeMatchers.none { it.matches(relativePath) }
    }

    init {
        require(includePatterns.isNotEmpty()) { "There must be at least one pattern included" }
    }
}