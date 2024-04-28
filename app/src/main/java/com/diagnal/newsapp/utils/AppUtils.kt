package com.diagnal.newsapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.diagnal.newsapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AppUtils() {
    //Show Alert for Soring on listing page
    companion object{
        fun showAlert(context: Context, onSortingOptionSelected: (String) -> Unit){
            val builder = MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog).create()
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.filter_dialog,null)
            val  btnCancel = view.findViewById<Button>(R.id.btnCancel)
            val  tvOldToNew = view.findViewById<TextView>(R.id.tvOldToNew)
            val  tvNewToOld = view.findViewById<TextView>(R.id.tvNewToOld)
            builder.setView(view)
            // Set the background view with blur effect as the background of the AlertDialog
            btnCancel.setOnClickListener {
                builder.dismiss()
            }

            tvOldToNew.setOnClickListener {
                // Call the sorting option callback with the selected old_to_new option
                onSortingOptionSelected("old_to_new")
                builder.dismiss()
            }

            tvNewToOld.setOnClickListener {
                // Call the sorting option callback with the selected new_to_old option
                onSortingOptionSelected("new_to_old")
                builder.dismiss()
            }

            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }
}