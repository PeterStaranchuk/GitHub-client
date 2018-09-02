package peter.staranchuk.githubclient.network.response

import com.google.gson.annotations.SerializedName
import peter.staranchuk.githubclient.enums.GitHubItemType
import peter.staranchuk.githubclient.interfaces.GitHubItem

data class GitHubReposResponse(@SerializedName("items") val repos: List<RepositoryInfo>)

data class RepositoryInfo(val name: String, val description: String, val owner: Owner) : GitHubItem {
    override fun getCode(): Int = GitHubItemType.REPOSITORY.code
}

data class Owner(@SerializedName("avatar_url") val avatarUrl: String, val login: String)