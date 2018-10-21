package sample.com.matches.viewmodels

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import sample.com.matches.models.LoadingStatus
import sample.com.matches.models.Match
import sample.com.matches.repo.MatchesRepo

class MatchViewModel : ViewModel() {

    var matches = MutableLiveData<MutableList<Match>>()
    var likes = MutableLiveData<MutableList<Match>>()
    var repo = MatchesRepo()
    var itemRemoved = MutableLiveData<Int>()

    init {
        loadData()
    }

    fun getDisplayList(position: Int?): MutableLiveData<MutableList<Match>> {
        if (position != null) {
            if (position == 0) {
                return matches
            } else {
                return likes
            }
        }
        return MutableLiveData()
    }

    fun getLikeList(originalList: MutableList<Match>?, updateAdapter: Boolean) {
        //get likes from original list
        if (originalList != null) {
            var result = mutableListOf<Match>()
            for (item in originalList) {
                if (item.liked) {
                    result.add(item)
                }
            }
            result.sortWith(compareByDescending { it.match })
            result = result.take(6).toMutableList()
            if (updateAdapter) {
                likes.value = result
            } else {
                likes.value?.clear()
                likes.value?.addAll(result)
            }
        } else {
            likes.value = mutableListOf()
        }
    }

    fun likeUnlike(match: Match, position: Int, tabPosition: Int) {
        var updateAdapter: Boolean
        if (tabPosition == 1) {
            //if clicked on Matches% tab update itemRemoved to add animation
            itemRemoved.value = position
            likes.value?.removeAt(position)
            updateAdapter = false
        } else {
            updateAdapter = true
        }
        updateMatchList(match)
        getLikeList(matches.value, updateAdapter)

    }

    fun getLikeSize(): Int {
        return likes.value?.size ?: 0
    }

    fun updateMatchList(match: Match) {
        val currentList = matches.value ?: mutableListOf()
        for (item in currentList) {
            if (item.userid.equals(match.userid)) {
                item.liked = !item.liked
                break
            }
        }
        matches.value = currentList
    }

    fun getLoadingStatus(): MutableLiveData<LoadingStatus> {
        return repo.loadingStatus
    }

    fun loadData() {
        matches = repo.requestMatches()
    }
}