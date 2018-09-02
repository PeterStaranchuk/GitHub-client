package peter.staranchuk.githubclient

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import peter.staranchuk.githubclient.dependency_injection.components.DaggerGitHubComponent

class GitHubApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerGitHubComponent.builder().create(this)

}