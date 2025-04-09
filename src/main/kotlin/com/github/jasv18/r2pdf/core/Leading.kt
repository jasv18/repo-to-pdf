package com.github.jasv18.r2pdf.core

enum class Leading(val lineSpacing: Float) {
    HALF(0.5F),
    SINGLE(1F),
    ONE_AND_HALF(1.5F),
    DOUBLE(2F);

    companion object {
        val DEFAULT = ONE_AND_HALF
    }
}