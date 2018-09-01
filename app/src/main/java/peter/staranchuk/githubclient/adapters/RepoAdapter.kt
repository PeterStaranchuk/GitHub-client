package peter.staranchuk.githubclient.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import peter.staranchuk.githubclient.databinding.ItemRepositoryBinding
import peter.staranchuk.githubclient.network.response.RepositoryInfo

class RepoAdapter(private var repos: List<RepositoryInfo>, private val listener: (position: Int) -> Unit) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(repos[position], listener)

    class ViewHolder(private var binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repoInfo: RepositoryInfo, listener: (position: Int) -> Unit) {
            binding.apply {
                repositoryInfo = repoInfo
                root.setOnClickListener { _ -> listener(layoutPosition) }
                executePendingBindings()
                Picasso.get().load(repoInfo.owner.avatarUrl).into(ivAvatar)
            }
        }
    }
}