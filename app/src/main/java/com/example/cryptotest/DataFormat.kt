package com.example.cryptotest

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object DataFormat {
    private const val DEFAULT_AMOUNT_MAX = 6 // 默认数量小数位
    private const val DEFAULT_AMOUNT_MIN = 0
    private const val DEFAULT_AMOUNT_SIZE = 3

    @JvmOverloads
    fun formatAmount(
        amount: String,
        min: Int = DEFAULT_AMOUNT_MIN,
        max: Int = DEFAULT_AMOUNT_MAX,
        groupSize: Int = DEFAULT_AMOUNT_SIZE
    ): String {
        return formatAmount(amount, min, max, groupSize, RoundingMode.HALF_EVEN)
    }

    fun formatAmount(
        amount: String,
        min: Int,
        max: Int,
        groupSize: Int,
        mode: RoundingMode?
    ): String {
        val amountDecimal: BigDecimal
        try {
            val maxNum = max.coerceAtLeast(min)
            amountDecimal = BigDecimal(amount)
            val format = NumberFormat.getInstance(Locale.ENGLISH) as DecimalFormat
            format.maximumFractionDigits = maxNum
            format.minimumFractionDigits = min
            format.groupingSize = groupSize
            if (mode != null) {
                format.roundingMode = mode
            }
            return format.format(amountDecimal)
        } catch (_: Exception) {
        }
        return amount
    }
}