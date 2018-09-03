package peter.staranchuk.githubclient.dependency_injection.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import peter.staranchuk.githubclient.model.GitHubModel
import peter.staranchuk.githubclient.network.ConnectivityChecker

@Module
class GitHubModule {

    @Provides
    fun provideContext(application: DaggerApplication): Context = application.applicationContext

    @Provides
    fun provideGitHubModel(): GitHubModel = GitHubModel()

    @Provides
    fun provideConnectivityChecker(context: Context): ConnectivityChecker = ConnectivityChecker(context)

}