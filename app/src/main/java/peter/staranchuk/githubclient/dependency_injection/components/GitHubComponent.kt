package peter.staranchuk.githubclient.dependency_injection.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import peter.staranchuk.githubclient.GitHubApplication
import peter.staranchuk.githubclient.dependency_injection.modules.GitHubModule
import peter.staranchuk.githubclient.dependency_injection.modules.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class, GitHubModule::class, AndroidSupportInjectionModule::class])
interface GitHubComponent : AndroidInjector<GitHubApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GitHubApplication>()
}