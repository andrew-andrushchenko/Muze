package com.andrii_a.muze.ui.util

import com.andrii_a.muze.domain.models.Artist
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val Artist.lifeYearsString: String
    get() {
        val dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd MMM yyyy")
        val born = LocalDate.parse(this.bornDateString)
            .format(dateTimeFormatter).toString()
        val died = LocalDate.parse(this.diedDateString)
            .format(dateTimeFormatter).toString()

        return "$born - $died"
    }