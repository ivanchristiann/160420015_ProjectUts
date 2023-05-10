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

class ListFavoriteViewModel(application: Application) :AndroidViewModel(application) {
    val favoriteLiveData = MutableLiveData<ArrayList<Kost>>()
    val favoriteLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        favoriteLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
//        val url = "https://github.com/ivanchristiann/utsANMP/blob/main/favoritKost.php"
        val url = "http://192.168.18.177:8080/projectANMP/favoritKost.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Kost>>() {}.type
                val result = Gson().fromJson<ArrayList<Kost>>(it, sType)
                favoriteLiveData.value = result
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                favoriteLoadErrorLD.value = false
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