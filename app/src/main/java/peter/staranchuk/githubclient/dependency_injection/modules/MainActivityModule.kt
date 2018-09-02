package peter.staranchuk.githubclient.dependency_injection.modules

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import peter.staranchuk.githubclient.dependency_injection.model_factories.MainViewModelFactory
import peter.staranchuk.githubclient.model.GitHubModel
import peter.staranchuk.githubclient.screen_main.MainActivity

@Module
internal abstract class MainActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        internal fun provideMainViewModelFactory(gitHubModel: GitHubModel): MainViewModelFactory = MainViewModelFactory(gitHubModel)
    }

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}