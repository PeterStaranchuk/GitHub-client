package peter.staranchuk.githubclient.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import peter.staranchuk.githubclient.enums.SearchFilter
import peter.staranchuk.githubclient.interfaces.GitHubItem
import peter.staranchuk.githubclient.model.GitHubModel
import peter.staranchuk.githubclient.network.ConnectivityChecker
import javax.inject.Inject

class ViewModelMain @Inject constructor(var gitHubModel: GitHubModel, var connectivityChecker: ConnectivityChecker) : ViewModel() {
    var ginHubItems = MutableLiveData<List<GitHubItem>>()
    var isLoading = ObservableField<Boolean>(false)
    var isLoadingError = ObservableField<Boolean>(false)
    var isNetworkAvailable = ObservableField<Boolean>(true)
    private var disposable: CompositeDisposable = CompositeDisposable()

    fun refreshData(searchQuery: String) {
        isLoadingError.set(false)

        if (searchQuery.isEmpty()) {
            ginHubItems.value = arrayListOf()
        } else {
            isLoading.set(true)

            if(connectivityChecker.isNetworkAvailable()) {
                isNetworkAvailable.set(true)
                disposable.add(gitHubModel.getGitHubInfo(searchQuery, SearchFilter.ALL).subscribeWith(object : DisposableObserver<List<GitHubItem>>() {
                    override fun onNext(t: List<GitHubItem>) {
                        ginHubItems.value = t
                    }

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                        isLoadingError.set(true)
                    }

                    override fun onComplete() = isLoading.set(false)
                }))
            } else {
                isNetworkAvailable.set(false)
            }
        }
    }

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.onCleared()
    }
}