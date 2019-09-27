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
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCardsTest{

    private val cardsRepositoryMock: CardsRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val cardsMock: List<Card> = mock()

    private lateinit var getCardsUseCase: GetCards

    @Before
    fun  setup(){
        getCardsUseCase = GetCards(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(cardsRepositoryMock.cards()).thenReturn(cardsMock)
        val result = getCardsUseCase.run(Unit)
        assertEquals(cardsMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(cardsRepositoryMock.cards()).thenThrow(KnownException::class.java)
        val result = getCardsUseCase.run(Unit)
    }

}