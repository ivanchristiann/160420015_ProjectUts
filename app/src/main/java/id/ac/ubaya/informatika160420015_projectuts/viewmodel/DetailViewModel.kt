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

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val kostLoadData = MutableLiveData<Kost>()
    var allKostList: ArrayList<Kost> = arrayListOf<Kost>()
    private var queue: RequestQueue? = null
    val TAG = "volleyTag"

    fun fetch(idKost: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.177:8080/projectANMP/listAllKost.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Kost>>() {}.type
                val result = Gson().fromJson<ArrayList<Kost>>(it, sType)
                for (i in 0 until result.count()) if (result.get(i).id == idKost) kostLoadData.value = result.get(i)
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}