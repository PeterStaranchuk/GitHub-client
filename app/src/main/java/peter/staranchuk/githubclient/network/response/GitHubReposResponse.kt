package peter.staranchuk.githubclient.network.response

import com.google.gson.annotations.SerializedName
import peter.staranchuk.githubclient.model.GitHubItem

data class GitHubReposResponse(@SerializedName("items") val repos : List<RepositoryInfo>)

data class RepositoryInfo(val name : String, val description : String, val owner : Owner) : GitHubItem

data class Owner(@SerializedName("avatar_url") val avatarUrl : String, val login : String)