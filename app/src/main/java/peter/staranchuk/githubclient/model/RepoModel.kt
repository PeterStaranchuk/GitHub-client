package peter.staranchuk.githubclient.model

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import peter.staranchuk.githubclient.network.GitHubApi
import peter.staranchuk.githubclient.network.response.RepositoryInfo

class RepoModel {

    fun getRepositories(searchQuery: String, success: (repositories: List<RepositoryInfo>) -> Unit, failure: (message: String?) -> Unit) {
        GitHubApi.Factory().getInstance().getRepositories(searchQuery)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                    success(response.repos)
                }, {
                    failure(it.message)
                })
    }
}