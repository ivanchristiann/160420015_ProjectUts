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
import id.ac.ubaya.informatika160420015_projectuts.model.User

class DetailUserProfileViewModel(application: Application) : AndroidViewModel(application)  {
    val userLoadData = MutableLiveData<User>()
    private var queue: RequestQueue? = null
    val TAG = "volleyTag"

    fun fetch() {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.177:8080/projectANMP/userProfile.php"
//        val url = "https://github.com/ivanchristiann/utsANMP/blob/main/userProfile.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson<User>(it, User::class.java)
                userLoadData.value = result
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}