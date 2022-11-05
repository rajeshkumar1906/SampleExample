package com.example.settingssample.model

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.settingssample.Constants
import com.example.settingssample.repo.LoadDataRepo
import com.example.settingssample.repo.OnlineMode

class ServiceViewModel(application: Application) : AndroidViewModel(application) {
    val mutableLiveData = MutableLiveData<List<Root>>()
    val application1:Application = application
    fun getData(): LiveData<List<Root>> {
        loadData()
        return mutableLiveData
    }

    fun loadData():LiveData<List<Root>>{
        LoadDataRepo( application1.applicationContext,mutableLiveData)
        return mutableLiveData
    }

    fun updateUI(): LiveData<List<Root>>{

        return mutableLiveData
    }

    class IncomingHandler : Handler.Callback {


        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                Constants.MESSAGE_SEND_DATA -> {

                }
//                MyService.MSG_SET_INT_VALUE -> textIntValue.setText("Int Message: " + msg.arg1)
//                MyService.MSG_SET_STRING_VALUE -> {
//                    val str1: String = msg.getData().getString("str1")
//                    textStrValue.setText("Str Message: $str1")
//                }

            }

            return true
        }
    }

}