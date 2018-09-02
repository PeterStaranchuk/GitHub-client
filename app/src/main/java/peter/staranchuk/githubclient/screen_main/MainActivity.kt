package peter.staranchuk.githubclient.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import peter.staranchuk.githubclient.R
import peter.staranchuk.githubclient.adapters.RepoAdapter
import peter.staranchuk.githubclient.databinding.ActivityMainBinding
import peter.staranchuk.githubclient.interfaces.GitHubItem
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchStartDelay = 300L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.rvRepositories.layoutManager = LinearLayoutManager(this)

        viewModel.repositories.observe(this, Observer<List<GitHubItem>> { newRepositoriesList ->
            newRepositoriesList?.let {
                rvRepositories.adapter = RepoAdapter(it) { clickedItemPosition ->
                    //TODO add on item clicked handler
                }
            }
        })

        RxTextView.afterTextChangeEvents(etSearchQuery).skipInitialValue()
                .debounce(searchStartDelay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.refreshData(etSearchQuery.text.toString().trim()) }
    }
}
