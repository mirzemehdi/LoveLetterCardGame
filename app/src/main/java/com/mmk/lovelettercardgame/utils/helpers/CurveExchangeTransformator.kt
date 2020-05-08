package com.mmk.lovelettercardgame.utils.helpers

import androidx.annotation.FloatRange
import kotlin.math.*

data class ExchangeCoords(
    val sourceX: Float,
    val sourceY: Float,
    val targetX: Float,
    val targetY: Float
)

interface ExchangeTransformator {
    fun transform(@FloatRange(from = 0.0, to = 1.0) fraction: Float): ExchangeCoords
}

class CurveExchangeTransformator(
    input: ExchangeCoords,
    size: Float
) : ExchangeTransformator {

    private val dVector: Float
    private val dX: Float
    private val dY: Float
    private val cXfromD: Float
    private val cYfromD: Float
    private val aAngle: Float
    private val bAngle: Float

    init {
        val cX = input.run { sourceX + targetX } / 2f
        val cY = input.run { sourceY + targetY } / 2f
        val distBCsqr = distSqr(input.targetX, input.targetY, cX, cY)

        val rDelta = size / SQRT_2
        val d = (distBCsqr - rDelta.pow(2)) / (SQRT_2 * size)

        dVector = d + rDelta
        dX = d * (input.targetY - cY) / sqrt(distBCsqr) + cX
        dY = -d * (input.targetX - cX) / sqrt(distBCsqr) + cY

        cXfromD = cX - dX
        cYfromD = cY - dY

        aAngle = getPointAngle(input.sourceX - dX, input.sourceY - dY)
        bAngle = normalizeAngleDiff(getPointAngle(input.targetX - dX, input.targetY - dY))
    }

    override fun transform(fraction: Float): ExchangeCoords {
        val angle = computeAngle(fraction)
        var x1 = dVector * cos(angle)
        var y1 = dVector * sin(angle)
        var x2 = 2 * cXfromD - x1
        var y2 = 2 * cYfromD - y1

        x1 += dX
        y1 += dY
        x2 += dX
        y2 += dY

        return ExchangeCoords(x1, y1, x2, y2)
    }

    private fun normalizeAngleDiff(bAngle: Float): Float {
        return if (abs(aAngle - bAngle) >= PI) {
            val temp = bAngle + 2 * PI.toFloat()
            if (abs(aAngle - temp) >= PI) {
                bAngle - 2 * PI.toFloat()
            } else {
                temp
            }
        } else {
            bAngle
        }
    }

    private fun computeAngle(fraction: Float) =
        bAngle * (1f - fraction) + aAngle * fraction

    private fun distSqr(x1: Float, y1: Float, x2: Float, y2: Float) =
        (x1 - x2).pow(2) + (y1 - y2).pow(2)

    private fun getPointAngle(x: Float, y: Float): Float {
        return if (y == 0f) {
            if (x > 0f) PI.toFloat() / 2f
            else - PI.toFloat() / 2f
        } else {
            atan(y / x) + (if ((x < 0)) PI.toFloat() else 0f)
        }
    }

    companion object {
        private val SQRT_2 = sqrt(2f)
    }
}