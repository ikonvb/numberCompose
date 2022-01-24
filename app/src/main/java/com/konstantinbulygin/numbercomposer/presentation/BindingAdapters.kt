package com.konstantinbulygin.numbercomposer.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("requiredScoreAnswers")
fun bindRequiredScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredMinPercentage")
fun bindRequiredMinPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("requiredScorePercentage")
fun bindRequiredScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countQuestions == 0) {
        0
    } else {
        ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

@BindingAdapter("enoughCountOfRightAnswers")
fun bindEnoughCountOfRightAnswers(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughCountOfPercent")
fun bindEnoughCountOfPercent(progressBar: ProgressBar, enough: Boolean) {
    progressBar.progressTintList =
        ColorStateList.valueOf(getColorByState(progressBar.context, enough))
}

private fun getColorByState(context: Context, it: Boolean): Int {
    val coloResId = if (it) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, coloResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}