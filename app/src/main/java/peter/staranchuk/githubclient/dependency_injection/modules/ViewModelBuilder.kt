package peter.staranchuk.githubclient.dependency_injection.modules

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import peter.staranchuk.githubclient.dependency_injection.DaggerViewModelFactory

@Module
internal abstract class ViewModelBuilder {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}