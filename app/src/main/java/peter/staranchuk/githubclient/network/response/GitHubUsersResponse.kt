package peter.staranchuk.githubclient.network.response

import com.google.gson.annotations.SerializedName
import peter.staranchuk.githubclient.enums.GitHubItemType
import peter.staranchuk.githubclient.interfaces.GitHubItem

data class GitHubUsersResponse(@SerializedName("items") val users : List<UserInfo>)

data class UserInfo(val login : String, val score : Double, @SerializedName("avatar_url") val avatarUrl : String)  : GitHubItem {
    override fun getCode(): Int = GitHubItemType.USER.code
}