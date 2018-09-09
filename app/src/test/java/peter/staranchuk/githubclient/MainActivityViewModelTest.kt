package peter.staranchuk.githubclient

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import peter.staranchuk.githubclient.interfaces.GitHubItem
import peter.staranchuk.githubclient.model.GitHubModel
import peter.staranchuk.githubclient.network.ConnectivityChecker
import peter.staranchuk.githubclient.screen_main.ViewModelMain
import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observable
import org.junit.rules.TestRule
import org.junit.Rule
import peter.staranchuk.githubclient.enums.SearchFilter
import peter.staranchuk.githubclient.network.response.RepositoryInfo
import peter.staranchuk.githubclient.network.response.UserInfo

class MainActivityViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var gitHubModel: GitHubModel

    @Mock
    lateinit var connectivityChecker: ConnectivityChecker

    lateinit var viewModelMain: ViewModelMain

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModelMain = Mockito.spy(ViewModelMain(gitHubModel, connectivityChecker))
    }

    @Test
    fun should_disable_login_error_when_search_started_and_query_empty() {
        val emptyQuery = ""
        viewModelMain.refreshData(emptyQuery)
        assertThat(viewModelMain.isLoadingError.get(), equalTo(false))
    }

    @Test
    fun should_disable_login_error_when_search_started_and_query_not_empty() {
        val notEmptyQuery = "qwerty"
        viewModelMain.refreshData(notEmptyQuery)
        assertThat(viewModelMain.isLoadingError.get(), equalTo(false))
    }

    @Test
    fun should_erase_items_list_when_query_is_empty() {
        val emptyQuery = ""
        `when`(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        viewModelMain.refreshData(emptyQuery)
        assertThat(viewModelMain.ginHubItems.value, equalTo(arrayListOf()))
    }

    @Test
    fun should_retrieve_item_list_if_query_is_not_empty() {
        `when`(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        val item1 = UserInfo("",0.0, "")
        val item2 = RepositoryInfo("","", UserInfo("",0.0, ""))
        val list: List<GitHubItem> = arrayListOf(item1, item2)

        `when`(gitHubModel.getGitHubInfo("qwerty", SearchFilter.ALL)).thenReturn(Observable.create {
            emitter ->
            emitter.onNext(list)
            emitter.onComplete()
        })

        viewModelMain.refreshData("qwerty")
        assertThat(viewModelMain.ginHubItems.value, equalTo(list))
    }

    @Test
    fun should_disable_network_available_error_when_network_available_and_query_is_empty() {
        `when`(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        viewModelMain.refreshData("")
        assertThat(viewModelMain.isNetworkAvailable.get(), equalTo(true))
    }

    @Test
    fun should_disable_network_available_error_when_network_available_and_query_not_empty() {
        `when`(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        `when`(gitHubModel.getGitHubInfo("qwerty", SearchFilter.ALL)).thenReturn(Observable.create {
            emitter ->
            emitter.onNext(arrayListOf())
            emitter.onComplete()
        })

        viewModelMain.refreshData("qwerty")
        assertThat(viewModelMain.isNetworkAvailable.get(), equalTo(true))
    }

    @Test
    fun should_enable_network_available_error_when_network_is_not_available_and_query_is_empty() {
        `when`(connectivityChecker.isNetworkAvailable()).thenReturn(false)
        viewModelMain.refreshData("")
        assertThat(viewModelMain.isNetworkAvailable.get(), equalTo(false))
    }

}
