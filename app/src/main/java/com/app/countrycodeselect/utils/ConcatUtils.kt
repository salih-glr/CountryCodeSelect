package com.app.countrycodeselect.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.ActivityResult

object ConcatUtils {


    fun creatIntent(): Intent {
        return Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }

    }

    fun getPersonNameNumber(
        context: Context,
        activityResult: ActivityResult
    ):Pair<String,String>?{
        if (activityResult.data?.data == null)return null
        val concatUri: Uri =activityResult.data!!.data!!
        val projection= arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        val cursor= context.contentResolver.query(
            concatUri,projection,null,null,null
        )
        if (cursor != null && cursor.moveToFirst()){
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val number=cursor.getString(numberIndex).filterNot { it.isWhitespace() }
            val name = cursor.getString(nameIndex)
            cursor.close()
            return Pair(name,number)
        }
        return Pair("","")

    }
}