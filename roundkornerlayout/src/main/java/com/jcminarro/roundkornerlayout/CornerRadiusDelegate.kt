package com.jcminarro.roundkornerlayout

import android.content.res.TypedArray
import android.os.Build
import android.view.View
import android.view.ViewGroup

class CornerRadiusDelegate(private val view: ViewGroup) {
    private val isRtl: Boolean = isRtl()

    internal fun getCornerRadius(array: TypedArray): CornersHolder {
        val cornerRadius =
            array.getDimension(R.styleable.RoundKornerRelativeLayout_corner_radius, 0f)
        var topLeftCornerRadius = array.getDimension(
            R.styleable.RoundKornerRelativeLayout_top_left_corner_radius,
            cornerRadius
        )
        var topRightCornerRadius = array.getDimension(
            R.styleable.RoundKornerRelativeLayout_top_right_corner_radius,
            cornerRadius
        )
        var bottomRightCornerRadius = array.getDimension(
            R.styleable.RoundKornerRelativeLayout_bottom_right_corner_radius,
            cornerRadius
        )
        var bottomLeftCornerRadius = array.getDimension(
            R.styleable.RoundKornerRelativeLayout_bottom_left_corner_radius,
            cornerRadius
        )

        if (isRtlSupported()) {
            val topStartCornerRadius = array.getDimension(
                R.styleable.RoundKornerFrameLayout_top_start_corner_radius,
                DEFAULT_CORNER_VALUE
            )
            val topEndCornerRadius = array.getDimension(
                R.styleable.RoundKornerFrameLayout_top_end_corner_radius,
                DEFAULT_CORNER_VALUE
            )
            val bottomStartCornerRadius =
                array.getDimension(
                    R.styleable.RoundKornerFrameLayout_bottom_start_corner_radius,
                    DEFAULT_CORNER_VALUE
                )
            val bottomEndCornerRadius =
                array.getDimension(
                    R.styleable.RoundKornerFrameLayout_bottom_end_corner_radius,
                    DEFAULT_CORNER_VALUE
                )

            val isLtl = !isRtl
            val topLeftRadius = if (isLtl) topStartCornerRadius else topEndCornerRadius
            val topRightRadius = if (isLtl) topEndCornerRadius else topStartCornerRadius
            val bottomLeftRadius = if (isLtl) bottomStartCornerRadius else bottomEndCornerRadius
            val bottomRightRadius = if (isLtl) bottomEndCornerRadius else bottomStartCornerRadius

            topLeftCornerRadius = resolveCornerRadius(
                topLeftRadius,
                topLeftCornerRadius
            )

            topRightCornerRadius = resolveCornerRadius(
                topRightRadius,
                topRightCornerRadius
            )

            bottomLeftCornerRadius = resolveCornerRadius(
                bottomLeftRadius,
                bottomLeftCornerRadius
            )

            bottomRightCornerRadius = resolveCornerRadius(
                bottomRightRadius,
                bottomRightCornerRadius
            )
        }

        return CornersHolder(
            topLeftCornerRadius,
            topRightCornerRadius,
            bottomRightCornerRadius,
            bottomLeftCornerRadius
        )
    }

    internal fun fillCornerRounder(
        canvasRounder: CanvasRounder,
        cornerRadius: Float,
        cornerType: CornerType
    ) {
        when (cornerType) {
            CornerType.ALL -> {
                canvasRounder.cornerRadius = cornerRadius
            }
            CornerType.TOP_LEFT -> {
                canvasRounder.topLeftCornerRadius = cornerRadius
            }
            CornerType.TOP_RIGHT -> {
                canvasRounder.topRightCornerRadius = cornerRadius
            }
            CornerType.BOTTOM_RIGHT -> {
                canvasRounder.bottomRightCornerRadius = cornerRadius
            }
            CornerType.BOTTOM_LEFT -> {
                canvasRounder.bottomLeftCornerRadius = cornerRadius
            }
            CornerType.TOP_START -> {
                if (isRtl) {
                    canvasRounder.topRightCornerRadius = cornerRadius
                } else {
                    canvasRounder.topLeftCornerRadius = cornerRadius
                }
            }
            CornerType.TOP_END -> {
                if (isRtl) {
                    canvasRounder.topLeftCornerRadius = cornerRadius
                } else {
                    canvasRounder.topRightCornerRadius = cornerRadius
                }
            }
            CornerType.BOTTOM_END -> {
                if (isRtl) {
                    canvasRounder.bottomLeftCornerRadius = cornerRadius
                } else {
                    canvasRounder.bottomRightCornerRadius = cornerRadius
                }
            }
            CornerType.BOTTOM_START -> {
                if (isRtl) {
                    canvasRounder.bottomRightCornerRadius = cornerRadius
                } else {
                    canvasRounder.bottomLeftCornerRadius = cornerRadius
                }
            }
        }
    }

    private fun resolveCornerRadius(relativeRadius: Float, radius: Float): Float {
        return if (relativeRadius == DEFAULT_CORNER_VALUE) {
            radius
        } else {
            relativeRadius
        }
    }

    private fun isRtl(): Boolean {
        return if (isRtlSupported()) {
            val config = view.context.resources.configuration
            config.layoutDirection == View.LAYOUT_DIRECTION_RTL
        } else {
            return false
        }
    }

    private fun isRtlSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    companion object {
        const val DEFAULT_CORNER_VALUE = -1f
    }
}