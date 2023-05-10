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
import id.ac.ubaya.informatika160420015_projectuts.model.Rating

class DetailRatingViewModel(application: Application) : AndroidViewModel(application) {
    val detailRatingLiveData = MutableLiveData<ArrayList<Rating>>()
    val detailRatingLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        detailRatingLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
//        val url = "https://github.com/ivanchristiann/utsANMP/blob/main/detailRatingkost.json"
        val url = "http://192.168.18.177:8080/projectANMP/detailRatingkost.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Rating>>() {}.type
                val result = Gson().fromJson<ArrayList<Rating>>(it, sType)
                detailRatingLiveData.value = result
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                detailRatingLoadErrorLD.value = false
                loadingLD.value = false
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}