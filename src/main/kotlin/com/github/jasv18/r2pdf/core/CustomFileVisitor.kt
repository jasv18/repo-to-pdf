package com.github.jasv18.r2pdf.core

import com.github.jasv18.r2pdf.utils.ePrintln
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class CustomFileVisitor(
    private val fileFilter: FileFilter
): SimpleFileVisitor<Path>() {

    private val fileList: MutableList<Path> = mutableListOf()

    override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
        return if (fileFilter.shouldInclude(dir)) {
            FileVisitResult.CONTINUE
        } else {
            FileVisitResult.SKIP_SUBTREE
        }
    }

    override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        if (fileFilter.shouldInclude(file)) {
            fileList.add(file)
        }
        return FileVisitResult.CONTINUE
    }

    override fun visitFileFailed(file: Path?, exc: IOException): FileVisitResult {
        ePrintln("Error: Unable to access file.\n${exc.message}")
        return FileVisitResult.CONTINUE
    }

    override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
        exc?.let { ePrintln("Error: Unable to access directory.\n${it.message}") }
        return FileVisitResult.CONTINUE
    }

    fun getListOfFiles() = fileList
}