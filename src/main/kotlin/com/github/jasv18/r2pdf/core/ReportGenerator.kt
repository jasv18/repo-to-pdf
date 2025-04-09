package com.github.jasv18.r2pdf.core

import java.nio.file.Path

interface ReportGenerator {
    /**
     * Add files in the report.
     * @param baseDir Base directory to be used for calculating relative paths in reports.
     * @param file File to add to the report (relative or absolute path).
     * */
    fun addFile(baseDir: Path, file: Path)
    /**
     * Save the document to the specified output.
     * @param output Output path where the document will be saved.
     * */
    fun save(output: Path)
    /**
     * Close the processed document.
     * */
    fun close()
}