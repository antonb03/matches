package sample.com.matches.views


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sample.com.matches.*
import sample.com.matches.adapters.MatchesListAdapter
import sample.com.matches.util.Constants
import sample.com.matches.viewmodels.MatchViewModel
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : Fragment() {
    lateinit var rootView:View
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: MatchesListAdapter
    lateinit var viewManager:GridLayoutManager
    lateinit var matchViewModel: MatchViewModel
    var position:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchViewModel = activity?.run {
            ViewModelProviders.of(this).get(MatchViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false)

        if (position == -1){
            position = savedInstanceState?.getInt(Constants.FRM_POSITION) ?: 0
        }
        viewAdapter = MatchesListAdapter(matchViewModel, position)
        viewManager = GridLayoutManager(context, 2)

        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view).apply{
            adapter = viewAdapter
            layoutManager = viewManager
        }

        matchViewModel.getDisplayList(position).observe(this, Observer {
            viewAdapter.notifyDataSetChanged()
        })
        matchViewModel.itemRemoved.observe(this, Observer {
            if(position == 1 && it != null){
                viewAdapter.notifyItemRemoved(it)
                viewAdapter.notifyItemRangeChanged(0, matchViewModel.getLikeSize())
            }
        })
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.FRM_POSITION, position)
    }

}
