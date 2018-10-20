package sample.com.matches.views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import sample.com.matches.R
import sample.com.matches.models.Match

class MatchViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val avatar:ImageView = itemView.findViewById(R.id.match_avatar)
    val usernameTv:TextView = itemView.findViewById(R.id.match_username)
    val ageAndLocationTv:TextView = itemView.findViewById(R.id.match_age_location)
    val matchTv:TextView = itemView.findViewById(R.id.match_percent)
    val matchLayout:LinearLayout = itemView.findViewById(R.id.match_layout)

    fun bind(match: Match){
        usernameTv.text = match.username
        ageAndLocationTv.text = itemView.context.getString(
            R.string.matches_age_location, match.age,
            match.location.cityName, match.location.stateCode)
        matchTv.text = (match.match / 100).toString() + "%"
        Glide.with(itemView.context).load(match.photo.fullPaths.large).into(avatar)
        if (match.liked){
            matchLayout.setBackgroundColor(itemView.context.resources.getColor(R.color.likedColor))
        }
        else {
            matchLayout.setBackgroundColor(itemView.context.resources.getColor(android.R.color.white))
        }
    }
}