package peter.staranchuk.githubclient.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import peter.staranchuk.githubclient.enums.SearchFilter
import peter.staranchuk.githubclient.interfaces.GitHubItem
import peter.staranchuk.githubclient.model.RepoModel

class ViewModelMain : ViewModel() {
    private var repoModel = RepoModel()
    var repositories = MutableLiveData<List<GitHubItem>>()
    var isLoading = ObservableField<Boolean>(false)
    var isLoadingError = ObservableField<Boolean>(false)

    fun refreshData(searchQuery : String) {
        isLoadingError.set(false)

        if(searchQuery.isEmpty()) {
            repositories.value = arrayListOf()
        } else {
            isLoading.set(true)

            repoModel.getGitHubInfo(searchQuery, SearchFilter.ALL, {
                repositories.value = it
            }, {
                isLoadingError.set(true)
            }, {
                isLoading.set(false)
            })

        }
    }
}