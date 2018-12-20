package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetExtractTest{

    private val cardsRepositoryMock: CardsRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val cardMock: Card = mock()
    private val extractMock :List<Extract> = mock()

    private lateinit var getExtractUseCase: GetExtract

    @Before
    fun  setup(){
        getExtractUseCase = GetExtract(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(cardsRepositoryMock.extract(cardMock)).thenReturn(extractMock)
        val result = getExtractUseCase.buildUseCaseObservable(GetExtract.Params(cardMock))
        assertEquals(extractMock, result)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(cardsRepositoryMock.extract(cardMock)).thenThrow(KnownException::class.java)
        val result = getExtractUseCase.buildUseCaseObservable(GetExtract.Params(cardMock))
    }
}