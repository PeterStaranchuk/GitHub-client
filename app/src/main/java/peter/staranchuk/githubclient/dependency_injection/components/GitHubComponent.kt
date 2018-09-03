package peter.staranchuk.githubclient.dependency_injection.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import peter.staranchuk.githubclient.GitHubApplication
import peter.staranchuk.githubclient.dependency_injection.modules.GitHubModule
import peter.staranchuk.githubclient.dependency_injection.modules.MainActivityModule
import peter.staranchuk.githubclient.dependency_injection.modules.ViewModelBuilder
import javax.inject.Singleton

@Component(modules = [ViewModelBuilder::class, MainActivityModule::class, GitHubModule::class, AndroidSupportInjectionModule::class])
interface GitHubComponent : AndroidInjector<GitHubApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: DaggerApplication): Builder

        fun build(): GitHubComponent
    }

    fun inject(app: DaggerApplication)

}