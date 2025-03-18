package com.example.cryptotest

import java.math.BigDecimal
import java.math.RoundingMode

object NumberUtils {
    fun add(v1: String?, v2: String?): String {
        return try {
            val b1 = BigDecimal(v1)
            val b2 = BigDecimal(v2)
            b1.add(b2).toPlainString().replace(",", ".")
        } catch (e: Exception) {
            "0"
        }
    }

    fun multiply(v1: String?, v2: String?): String {
        return try {
            val b1 = BigDecimal(v1)
            val b2 = BigDecimal(v2)
            b1.multiply(b2).toPlainString().replace(",", ".")
        } catch (e: Exception) {
            "0"
        }
    }

    fun divide(v1: String?, v2: String?): String {
        return try {
            val b1 = BigDecimal(v1)
            val b2 = BigDecimal(v2)
            b1.divide(b2, 10, RoundingMode.HALF_EVEN).toPlainString().replace(",", ".")
        } catch (e: Exception) {
            "0"
        }
    }
}