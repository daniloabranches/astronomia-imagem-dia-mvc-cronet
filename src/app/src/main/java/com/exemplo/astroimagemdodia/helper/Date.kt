package com.exemplo.astroimagemdodia.helper

class Date {
    companion object {
        fun formatDate(date: String): String? {
            val (year, month, day) = date.split('-')
            return String.format("%s/%s/%s", day, month, year)
        }
    }
}