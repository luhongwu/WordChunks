/*
 * Copyright 2017 Julia Kozhukhovskaya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appchamp.wordchunks.ui.customviews

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appchamp.wordchunks.R


class StoreDialog : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): StoreDialog {
            val dialog = StoreDialog()
//            val args = Bundle()
//            dialog.arguments = args
            return dialog
        }
    }

    // Defines the listener interface with a method passing back data result.
//    interface LevelSolvedDialogListener {
//        fun onNextBtnClickedDialog()
//    }

    override fun

            onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                         savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_store, container, false)
        // Set background transparent for rounded corners in the dialog.
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        return view
    }

    interface StoreDialogListener {
        fun onRewardUser()
    }
}