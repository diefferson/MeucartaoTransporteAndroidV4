package br.com.disapps.domain.interactor

import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test
import br.com.disapps.domain.executor.ThreadExecutor
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

import org.assertj.core.api.Assertions.assertThat
import org.mockito.BDDMockito.given


class UseCaseTest {

    private lateinit var useCase: UseCaseTestClass
    private lateinit var testObserver: TestDisposableObserver<Any>
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()

    @Before
    fun setUp() {
        this.useCase = UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread)
        this.testObserver = TestDisposableObserver()
        given<Scheduler>(mockPostExecutionThread.scheduler).willReturn(TestScheduler())
    }

    @Test
    fun testBuildUseCaseObservableReturnCorrectResult() {
        useCase.execute(testObserver, UseCaseTestClass.Params.EMPTY)
        assertThat(testObserver.valuesCount).isZero()
    }

    @Test
    fun testSubscriptionWhenExecutingUseCase() {
        useCase.execute(testObserver, UseCaseTestClass.Params.EMPTY)
        useCase.dispose()
        assertThat(testObserver.isDisposed).isTrue()
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenExecuteWithNullObserver() {
        useCase.execute(null!!,UseCaseTestClass.Params.EMPTY)
    }

    private class UseCaseTestClass(threadExecutor: ThreadExecutor,
                                   postExecutionThread: PostExecutionThread) : UseCase<Any, UseCaseTestClass.Params>(threadExecutor, postExecutionThread) {

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
