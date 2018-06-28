package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCardTest{

    private val cardsRepositoryMock:CardsRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val cardMock:Card = mock()

    private lateinit var getCardUseCase: GetCard

    @Before
    fun  setup(){
        getCardUseCase = GetCard(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(cardsRepositoryMock.card(cardMock)).thenReturn(cardMock)
        val result = getCardUseCase.buildUseCaseObservable(GetCard.Params(cardMock))
        assertEquals(cardMock, result)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(cardsRepositoryMock.card(cardMock)).thenThrow(KnownException::class.java)
        val result = getCardUseCase.buildUseCaseObservable(GetCard.Params(cardMock))
    }

}