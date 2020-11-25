package com.luis.simplegymworkout

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class UtilsUI {
    companion object {

        fun showToast(context: Context, text: String){
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun showToast(context: Context, text: Int){
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun showAlert(context: Context,
                      message: Int, positiveOpt : Int, negativeOpt : Int,
                      callback : () -> Unit){
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveOpt) { dialog, id ->
                    callback()
                }
                .setNegativeButton(negativeOpt) { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }
}