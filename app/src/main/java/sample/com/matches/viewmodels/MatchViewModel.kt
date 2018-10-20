package sample.com.matches.viewmodels

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import sample.com.matches.models.LoadingStatus
import sample.com.matches.models.Match
import sample.com.matches.repo.MatchesRepo

class MatchViewModel : ViewModel() {

    var matches = MutableLiveData<MutableList<Match>>()
    var likes = MediatorLiveData<MutableList<Match>>()
    var repo = MatchesRepo()

    init {
        loadData()
        likes.addSource(matches) {
            getLikeList(it)
        }
    }

    fun getDisplayList(position:Int?): MutableLiveData<MutableList<Match>> {
        if (position != null) {
            if (position == 0) {
                return matches
            } else {
                return likes
            }
        }
        return MutableLiveData()
    }

    fun getLikeList(originalList: MutableList<Match>?) {
        if (originalList != null) {
            var result = mutableListOf<Match>()
            for (item in originalList) {
                if (item.liked) {
                    result.add(item)
                }
            }
            result.sortWith(compareByDescending { it.match })
            result = result.take(6).toMutableList()
            likes.value = result
        } else {
            likes.value = mutableListOf()
        }
    }

    fun likeUnlike(match: Match, position: Int) {
        val currentList = matches.value ?: mutableListOf()
        for (item in currentList) {
            if (item.userid.equals(match.userid)) {
                item.liked = !item.liked
            }
        }
        matches.value = currentList
    }

    fun getLoadingStatus():MutableLiveData<LoadingStatus>{
        return repo.loadingStatus
    }

    fun loadData(){
        matches = repo.requestMatches()
    }
}