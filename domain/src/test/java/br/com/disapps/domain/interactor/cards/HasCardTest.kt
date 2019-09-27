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

class HasCardTest{
    private val cardsRepositoryMock: CardsRepository = mock()
    private val logExceptionMock: LogException = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on { scheduler } doReturn(Unconfined) }
    private val postExecutionContextMock: PostExecutionContext = mock{ on { scheduler } doReturn(Unconfined) }
    private val cardMock: Card = mock()

    private lateinit var hasCardUseCase: HasCard

    @Before
    fun setup(){
        hasCardUseCase = HasCard(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }


    @Test
    fun testrunSuccessCase() = runBlocking {
        whenever(cardsRepositoryMock.hasCard(cardMock)).thenReturn(true)
        val result = hasCardUseCase.run(HasCard.Params(cardMock))
        assertEquals(true, result)
    }

    @Test(expected = KnownException::class)
    fun testrunErrorCase() = runBlocking {
        whenever(cardsRepositoryMock.hasCard(cardMock)).thenThrow(KnownException::class.java)
        val result = hasCardUseCase.run(HasCard.Params(cardMock))
    }
}