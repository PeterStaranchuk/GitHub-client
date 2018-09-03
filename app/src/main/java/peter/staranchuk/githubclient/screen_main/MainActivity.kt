package peter.staranchuk.githubclient.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import peter.staranchuk.githubclient.R
import peter.staranchuk.githubclient.adapters.RepoAdapter
import peter.staranchuk.githubclient.databinding.ActivityMainBinding
import peter.staranchuk.githubclient.interfaces.GitHubItem
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var mainViewModelFactory : ViewModelProvider.Factory

    private val searchStartTimeout = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(ViewModelMain::class.java)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        rvRepositories.layoutManager = LinearLayoutManager(this)
        viewModel.ginHubItems.observe(this, Observer<List<GitHubItem>> { newRepositoriesList ->
            newRepositoriesList?.let {
                rvRepositories.adapter = RepoAdapter(it) { clickedItemPosition ->
                    //TODO add on item clicked handler
                }
            }
        })

        RxTextView.afterTextChangeEvents(etSearchQuery).skipInitialValue()
                .debounce(searchStartTimeout, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.refreshData(etSearchQuery.text.toString().trim()) }
    }
}
