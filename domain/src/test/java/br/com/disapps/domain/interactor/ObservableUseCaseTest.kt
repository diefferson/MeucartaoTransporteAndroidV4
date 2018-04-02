package br.com.disapps.domain.interactor

import br.com.disapps.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.DefaultObserver
import br.com.disapps.domain.interactor.base.ObservableUseCase
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

import org.assertj.core.api.Assertions.assertThat
import org.mockito.BDDMockito.given


class ObservableUseCaseTest {

    private lateinit var useCase: ObservableUseCaseTestClass
    private lateinit var testObserver: TestDisposableObserver<Any>
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()

    @Before
    fun setUp() {
        this.useCase = ObservableUseCaseTestClass(mockThreadExecutor, mockPostExecutionThread)
        this.testObserver = TestDisposableObserver()
        given<Scheduler>(mockPostExecutionThread.scheduler).willReturn(TestScheduler())
    }

    @Test
    fun testBuildUseCaseObservableReturnCorrectResult() {
        useCase.execute(testObserver, ObservableUseCaseTestClass.Params.EMPTY)
        assertThat(testObserver.valuesCount).isZero()
    }

    @Test
    fun testSubscriptionWhenExecutingUseCase() {
        useCase.execute(testObserver, ObservableUseCaseTestClass.Params.EMPTY)
        useCase.dispose()
        assertThat(testObserver.isDisposed).isTrue()
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenExecuteWithNullObserver() {
        useCase.execute(null!!,ObservableUseCaseTestClass.Params.EMPTY)
    }

    private class ObservableUseCaseTestClass(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread) : ObservableUseCase<Any, ObservableUseCaseTestClass.Params>(threadExecutor, postExecutionThread) {

        override fun buildUseCaseObservable(params: Params): Observable<Any> {
            return Observable.empty()
        }
        class Params {
            companion object {
                var EMPTY = Params()
            }
        }
    }

    private class TestDisposableObserver<T> : DefaultObserver<T>() {
        var valuesCount = 0

        override fun onNext(t: T) {
            valuesCount++
        }
    }
}
