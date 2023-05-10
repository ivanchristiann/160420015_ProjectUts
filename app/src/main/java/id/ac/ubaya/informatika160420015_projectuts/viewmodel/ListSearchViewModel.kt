package id.ac.ubaya.informatika160420015_projectuts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.ac.ubaya.informatika160420015_projectuts.model.Kost

class ListSearchViewModel(application: Application) : AndroidViewModel(application) {
    val kostSearchLiveData = MutableLiveData<ArrayList<Kost>>()
//    var allKostList: ArrayList<Kost> = arrayListOf<Kost>()
    val kostSearchLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh(jenisKost: String) {
        loadingLD.value = true
        kostSearchLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
//        val url = "https://github.com/ivanchristiann/utsANMP/blob/main/listAllKost.php"
        val url = "http://192.168.18.177:8080/projectANMP/listAllkost.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Kost>>() {}.type
                val result = Gson().fromJson<ArrayList<Kost>>(it, sType)
                if(jenisKost != "All") {
                    var kostCustomList: ArrayList<Kost> = arrayListOf<Kost>()
                    for (i in 0 until result.count()) if (result.get(i).jenisKost == jenisKost) kostCustomList.add(result.get(i))
                    kostSearchLiveData.value = kostCustomList
                }else{
                    kostSearchLiveData.value = result
                }
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                kostSearchLoadErrorLD.value = false
                loadingLD.value = false
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}