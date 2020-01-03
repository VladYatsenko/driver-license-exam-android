package com.android.testdai.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.testdai.R
import com.android.testdai.interfaces.OnResultListener

class ResultDialog : DialogFragment() {


    companion object {
        fun newInstance(result: Int, onResultListener: OnResultListener): ResultDialog {
            val fragment = ResultDialog()
            fragment.result = result
            fragment.onResultListener = onResultListener
            return fragment
        }
    }

    var result: Int = 0
    var onResultListener: OnResultListener? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val v = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_result, null)


        val mResult = v.findViewById<TextView>(R.id.result)
        val mResultImage = v.findViewById<ImageView>(R.id.resultImg)

        mResult.text = if (result < 18) "Іспит не складений" else "Іспит складений"
        mResultImage.setImageResource(if (result < 18) R.drawable.cat else R.drawable.prize)


        val mResultText = v.findViewById<View>(R.id.resultTxt) as TextView
        mResultText.text = "Правильних відповідей: $result/20"

        return context?.let {
            AlertDialog.Builder(it)
                    .setView(v)
                    .setNeutralButton(R.string.restart) { dialog, which ->
                        dialog?.cancel()
                        onResultListener?.onRestartTest()
                    }
                    .setPositiveButton(android.R.string.ok) { dialog, i -> dialog?.dismiss() }
                    .create()
        } ?: super.onCreateDialog(savedInstanceState)

    }

}