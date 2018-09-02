package peter.staranchuk.githubclient.dependency_injection.model_factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import peter.staranchuk.githubclient.model.GitHubModel
import peter.staranchuk.githubclient.screen_main.ViewModelMain

class MainViewModelFactory(private val repository: GitHubModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMain::class.java)) {
            return ViewModelMain(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}