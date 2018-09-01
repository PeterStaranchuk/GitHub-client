package peter.staranchuk.githubclient.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import peter.staranchuk.githubclient.model.RepoModel
import peter.staranchuk.githubclient.network.response.RepositoryInfo


class ViewModelMain : ViewModel() {
    private var repoModel = RepoModel()
    var repositories = MutableLiveData<List<RepositoryInfo>>()
    var isLoading = ObservableField<Boolean>(false)
    var isLoadingError = ObservableField<Boolean>(false)

    fun refreshData(searchQuery : String) {
        isLoadingError.set(false)

        if(searchQuery.isEmpty()) {
            repositories.value = arrayListOf()
        } else {
            isLoading.set(true)
            repoModel.getRepositories(searchQuery, { newRepositories ->
                isLoading.set(false)
                repositories.value = newRepositories
            }, {
                isLoading.set(false)
                isLoadingError.set(true)
            })
        }
    }
}