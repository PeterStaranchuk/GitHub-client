package peter.staranchuk.githubclient.dependency_injection.modules

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import peter.staranchuk.githubclient.interfaces.ViewModelKeyAnnotation
import peter.staranchuk.githubclient.screen_main.MainActivity
import peter.staranchuk.githubclient.screen_main.ViewModelMain

@Module
internal abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKeyAnnotation.ViewModelKey(ViewModelMain::class)
    abstract fun bindMainViewModel(viewModel: ViewModelMain): ViewModel

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}