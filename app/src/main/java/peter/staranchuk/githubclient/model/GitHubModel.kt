package peter.staranchuk.githubclient.model

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import peter.staranchuk.githubclient.enums.SearchFilter
import peter.staranchuk.githubclient.extentions.mergeElementwise
import peter.staranchuk.githubclient.interfaces.GitHubItem
import peter.staranchuk.githubclient.network.GitHubApi

class GitHubModel {
    private val gitHubApi = GitHubApi.Factory().getInstance()

    private fun getUsers(searchQuery: String): Observable<List<GitHubItem>> {
        return gitHubApi.getUsers(searchQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.users }
    }

    private fun getRepositories(searchQuery: String): Observable<List<GitHubItem>> {
        return gitHubApi.getRepositories(searchQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.repos }
    }

    fun getGitHubInfo(searchQuery: String, filter: SearchFilter) : Observable<List<GitHubItem>> {
        return when (filter) {
            SearchFilter.ALL -> {
                Observable.zip(getUsers(searchQuery), getRepositories(searchQuery),
                        BiFunction { users, repos ->
                            mergeElementwise(users, repos)
                        }
                )
            }

            SearchFilter.USERS, SearchFilter.REPOS -> {
                getUsers(searchQuery).map { it }
            }
        }
    }
}
