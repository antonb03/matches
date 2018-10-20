package sample.com.matches.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sample.com.matches.views.MatchViewHolder
import sample.com.matches.viewmodels.MatchViewModel
import sample.com.matches.R

class MatchesListAdapter(var matchViewModel: MatchViewModel, var tabPosition:Int):RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatchViewHolder {
        val view:View = LayoutInflater.from(p0.context).inflate(R.layout.match_view, p0, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int = matchViewModel.getDisplayList(tabPosition).value?.size ?: 0

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matchViewModel.getDisplayList(tabPosition).value?.get(position)
        if (match != null) {
            holder.bind(match)
            holder.matchLayout.setOnClickListener {
                matchViewModel.likeUnlike(match, position)
                if (tabPosition == 1){
                    notifyItemRemoved(position)
                }
            }
        }
    }
}