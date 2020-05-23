package org.d3if4133.side_project_adnia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.BuildConfig
import cz.msebera.android.httpclient.Header
import org.d3if4133.side_project_adnia.model.Corona
import org.json.JSONArray
import timber.log.Timber

class CoronaViewModel : ViewModel() {
    private val listCoronaData: MutableLiveData<ArrayList<Corona>> =
        MutableLiveData<ArrayList<Corona>>()

    fun setDataIndonesia() {
        val client = AsyncHttpClient()
        val dataItem: ArrayList<Corona> = ArrayList<Corona>()
        val URL_API = "https://api.kawalcorona.com/indonesia/"


        client.get(URL_API, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val jsonArray = JSONArray(result)
                    val corona = jsonArray.getJSONObject(0)

                    val positif = corona.getString("positif")
                    val sembuh = corona.getString("sembuh")
                    val meninggal = corona.getString("meninggal")

                    val coronaData = Corona(positif, sembuh, meninggal)

                    dataItem.add(coronaData)

                    listCoronaData.postValue(dataItem)
                } catch (e: Exception) {
                    Timber.i(e.message!!)

                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Timber.i(error!!.message!!)
            }

        })
    }

    fun getDataCorona(): LiveData<ArrayList<Corona>> {
        return listCoronaData
    }

}