package peter.staranchuk.githubclient.network.response

import com.google.gson.annotations.SerializedName

data class GitHubReposResponse(@SerializedName("items") val repos : List<RepositoryInfo>)

data class RepositoryInfo(val name : String, val description : String, val owner : Owner)

data class Owner(@SerializedName("avatar_url") val avatarUrl : String, val login : String)