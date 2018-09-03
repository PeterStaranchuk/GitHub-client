package peter.staranchuk.githubclient

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector
import peter.staranchuk.githubclient.dependency_injection.components.DaggerGitHubComponent

class GitHubApplication : DaggerApplication(), HasActivityInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerGitHubComponent.builder().context(this).build()

}