package sample.com.matches.repo

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.com.matches.models.LoadingStatus
import sample.com.matches.models.Match
import sample.com.matches.models.Search
import sample.com.matches.util.ErrorParser

class MatchesRepo : Callback<Search> {

    val BASE_URL = "https://www.okcupid.com"
    val matchesList = MutableLiveData<MutableList<Match>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val errorParser = ErrorParser()

    init {
        loadingStatus.value = LoadingStatus()
    }

    fun requestMatches(): MutableLiveData<MutableList<Match>> {
        val gson = GsonBuilder().setLenient().create()
        matchesList.value = mutableListOf()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val matchService = retrofit.create(MatchService::class.java)
        val call = matchService.getMatches()
        loadingStatus.value = LoadingStatus(true, "")
        call.enqueue(this)
        return matchesList
    }

    override fun onFailure(call: Call<Search>, t: Throwable) {
        loadingStatus.value = LoadingStatus(false, errorParser.getErrorMessage(t))
    }

    override fun onResponse(call: Call<Search>, response: Response<Search>) {
        if (response.isSuccessful) {
            val example = response.body()
            matchesList.value = example?.data
            loadingStatus.value = LoadingStatus(false, "")
        } else {
            //response not successful
            loadingStatus.value = LoadingStatus(false, response.errorBody().toString())
        }
    }
}