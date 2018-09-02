package peter.staranchuk.githubclient.adapters

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import peter.staranchuk.githubclient.R
import peter.staranchuk.githubclient.databinding.ItemDummyBinding
import peter.staranchuk.githubclient.databinding.ItemRepositoryBinding
import peter.staranchuk.githubclient.databinding.ItemUserBinding
import peter.staranchuk.githubclient.enums.GitHubItemType
import peter.staranchuk.githubclient.model.GitHubItem
import peter.staranchuk.githubclient.network.response.RepositoryInfo
import peter.staranchuk.githubclient.network.response.UserInfo

class RepoAdapter(private var repos: List<GitHubItem>, private val listener: (position: Int) -> Unit) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            GitHubItemType.REPOSITORY.code -> {
                val binding = ItemRepositoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

            GitHubItemType.USER.code -> {
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

            else -> {
                val binding = ItemDummyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(repos[position], listener)

    override fun getItemCount(): Int = repos.size

    class ViewHolder(private var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfo: GitHubItem, listener: (position: Int) -> Unit) {
            when(itemViewType) {
                GitHubItemType.USER.code -> {
                    (binding as ItemUserBinding).apply {
                        userInformation = itemInfo as UserInfo
                        Picasso.get().load(itemInfo.avatarUrl).placeholder(R.drawable.image_loading_placeholder).into(ivAvatar)
                    }
                }

                GitHubItemType.REPOSITORY.code -> {
                    (binding as ItemRepositoryBinding).apply {
                        repositoryInfo = itemInfo as RepositoryInfo
                        Picasso.get().load(itemInfo.owner.avatarUrl).placeholder(R.drawable.image_loading_placeholder).into(ivAvatar)
                    }
                }
            }

            binding.executePendingBindings()
            binding.root.setOnClickListener { _ -> listener(layoutPosition) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (repos[position] is RepositoryInfo) {
            return GitHubItemType.REPOSITORY.code
        }

        if (repos[position] is UserInfo) {
            return GitHubItemType.USER.code
        }

        return super.getItemViewType(position)
    }
}