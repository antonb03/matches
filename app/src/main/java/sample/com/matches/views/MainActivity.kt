package sample.com.matches.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout

import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import sample.com.matches.viewmodels.MatchViewModel
import sample.com.matches.R
import sample.com.matches.adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var tabLayout: TabLayout
    lateinit var progress: ProgressBar
    lateinit var matchViewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        progress = findViewById(R.id.matches_progress)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        matchViewModel.getLoadingStatus().observe(this, Observer {
            progress.visibility = if (it?.loading == true) View.VISIBLE else View.GONE
            if (!it?.errorMsg.isNullOrBlank()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it?.errorMsg ?: "Unknown Error")
                builder.setCancelable(false)
                builder.setPositiveButton("Retry") { dialog, which ->
                    matchViewModel.loadData()
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }

        })
    }
}
