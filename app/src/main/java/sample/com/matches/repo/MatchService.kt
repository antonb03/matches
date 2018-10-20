package sample.com.matches.repo

import retrofit2.Call
import retrofit2.http.GET
import sample.com.matches.models.Search

interface MatchService {
    @GET("/matchSample.json")
    fun getMatches():Call<Search>
}