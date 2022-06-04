package com.example.settingssample

import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.settingssample.adapter.CustomListAdapter
import com.example.settingssample.model.Root
import com.example.settingssample.model.ServiceViewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(),RandomWorker.SetSyncData{
    companion object{
       lateinit var activity: MainActivity
    }

    lateinit var serviceViewModel: ServiceViewModel
    lateinit var  recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        activity = this
        serviceViewModel =
            ViewModelProvider(this).get(ServiceViewModel::class.java)

        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest: WorkRequest =  PeriodicWorkRequest.Builder(RandomWorker::class.java,1,
            TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(workRequest)

//       val workManager:WorkManager =  WorkManager.getInstance(applicationContext)
//        workManager.getWorkInfoByIdLiveData(workRequest.id)
//            .observe(this) { workInfo ->
//                if (workInfo != null) {
//                    Log.d("MainActivity", "WorkInfo received: state:<> " + workInfo.state)
//                    val message = workInfo.outputData.keyValueMap.get("data")
//                    Log.d("MainActivity", "message<>: $message")
//                }
//            }

        recyclerView.layoutManager = LinearLayoutManager(this)

        val observer: Observer<List<Root>> = Observer { t ->
            recyclerView.adapter = CustomListAdapter(t)
        }

        serviceViewModel.getData().observe(this,observer)
        serviceViewModel.mutableLiveData.observe(this)
    }

    override fun syncData(mutableLiveData: MutableLiveData<List<Root>>){
        val observer: Observer<List<Root>> = Observer { t ->
            Log.e("MainAcitivity","<>syncing"+t)
            recyclerView.adapter = CustomListAdapter(t)

        }
        Handler(Looper.getMainLooper()).post {
            mutableLiveData.observe(this,observer)
        }

    }
    class IncomingHandler : Handler.Callback {


        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
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