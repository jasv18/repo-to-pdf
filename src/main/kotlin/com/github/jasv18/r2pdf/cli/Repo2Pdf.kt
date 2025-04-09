package com.github.jasv18.r2pdf.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.jasv18.r2pdf.core.CustomFileVisitor
import com.github.jasv18.r2pdf.core.FileFilter
import com.github.jasv18.r2pdf.core.FileScanner
import com.github.jasv18.r2pdf.core.PdfGenerator
import java.nio.file.Files
import java.nio.file.Paths

class Repo2Pdf: CliktCommand(name = "Convert repositories to pdf/text files.") {
    private val source: String by argument(help = "Repository origin (required): local repository path" +
            "(e.g. ./my_project) or remote git URL (e.g. https://github.com/user/repo.git)\n")
    private val exclude: List<String> by option("-e", "--excludes")
        .multiple(default = emptyList())
        .help("Glob pattern to exclude files or directories (e.g. **/test/**, *.tmp) " +
                "Regular expressions are not supported.\n")
    private val include: List<String> by option("-i", "--includes")
        .multiple(default = listOf("**"))
        .help("Glob pattern to include files or directories (e.g. **/*.js, *.md). " +
                "Regular expressions are not supported.\n")
    override fun run() {
        val startPath = Paths.get(source)
        val fileScanner = FileScanner(startPath, include, exclude)
        val filesToProcess = fileScanner.scan()
        val document = PdfGenerator()
        filesToProcess.forEach { document.addFile(startPath, it) }
        document.save(Paths.get("test_report.pdf"))
        document.close()
    }
}