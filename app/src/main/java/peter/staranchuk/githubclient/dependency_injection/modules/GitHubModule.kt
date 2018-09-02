package peter.staranchuk.githubclient.dependency_injection.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import peter.staranchuk.githubclient.model.GitHubModel

@Module
class GitHubModule {

    @Provides
    fun provideContext(application: DaggerApplication) : Context = application.applicationContext

    @Provides
    fun provideGitHubModel() : GitHubModel = GitHubModel()

}