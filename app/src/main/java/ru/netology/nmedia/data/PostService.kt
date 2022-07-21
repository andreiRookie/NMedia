package ru.netology.nmedia.data

import java.math.RoundingMode
import java.text.DecimalFormat

object PostService {


    fun countToString(count: Int): String {

        return when (count) {
            in 1000..9999 ->
                if ("${roundDecimal(count.toDouble() / 1000)}K".endsWith(".0K"))
                    "${count / 1000}K"
                else "${roundDecimal(count.toDouble() / 1000)}K"

            in 10_000..999_999 -> "${count / 1000}K"

            in 1_000_000..Int.MAX_VALUE ->
                if ("${roundDecimal(count.toDouble() / 1000000)}M".endsWith(".0M"))
                    "${(count / 1000000)}M"
                else "${roundDecimal(count.toDouble() / 1000000)}M"

            else -> "$count"
        }
    }

    private fun roundDecimal(number: Double): Double {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
}