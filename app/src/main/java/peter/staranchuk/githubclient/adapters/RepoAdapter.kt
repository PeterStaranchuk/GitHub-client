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
import peter.staranchuk.githubclient.interfaces.GitHubItem
import peter.staranchuk.githubclient.network.response.RepositoryInfo
import peter.staranchuk.githubclient.network.response.UserInfo

class RepoAdapter(private var repos: List<GitHubItem>, private val listener: (position: Int) -> Unit) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = layoutInflater?: LayoutInflater.from(parent.context)

        val binding = when (viewType) {
            GitHubItemType.REPOSITORY.code -> ItemRepositoryBinding.inflate(layoutInflater!!, parent, false)

            GitHubItemType.USER.code -> ItemUserBinding.inflate(layoutInflater!!, parent, false)

            else -> ItemDummyBinding.inflate(layoutInflater!!, parent, false)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(repos[position], listener)

    override fun getItemCount(): Int = repos.size

    override fun getItemViewType(position: Int): Int = repos[position].getCode()

    class ViewHolder(private var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfo: GitHubItem, listener: (position: Int) -> Unit) {
            when (itemViewType) {
                GitHubItemType.USER.code -> bindUserView(binding as ItemUserBinding, itemInfo as UserInfo)

                GitHubItemType.REPOSITORY.code -> bindRepoView(binding as ItemRepositoryBinding, itemInfo as RepositoryInfo)
            }

            binding.executePendingBindings()
            binding.root.setOnClickListener { _ -> listener(layoutPosition) }
        }

        private fun bindRepoView(binding: ItemRepositoryBinding, itemInfo: RepositoryInfo) {
            binding.apply {
                repositoryInfo = itemInfo
                Picasso.get().load(itemInfo.owner.avatarUrl)
                        .placeholder(R.drawable.image_loading_placeholder)
                        .into(ivAvatar)
            }
        }

        private fun bindUserView(binding: ItemUserBinding, itemInfo: UserInfo) {
            binding.apply {
                userInformation = itemInfo
                Picasso.get().load(itemInfo.avatarUrl)
                        .placeholder(R.drawable.image_loading_placeholder)
                        .into(ivAvatar)
            }
        }
    }
}