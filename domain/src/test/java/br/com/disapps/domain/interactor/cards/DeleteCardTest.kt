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

class DeleteCardTest{

    private val cardsRepositoryMock: CardsRepository = mock()
    private val logExceptionMock: LogException = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on { scheduler } doReturn(Unconfined) }
    private val postExecutionContextMock: PostExecutionContext = mock{ on { scheduler } doReturn(Unconfined) }
    private val cardMock:Card = mock()

    private lateinit var deleteCardUseCase: DeleteCard

    @Before
    fun setup(){
        deleteCardUseCase = DeleteCard(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }


    @Test
    fun testrunSuccessCase() = runBlocking {
        val result = deleteCardUseCase.run(DeleteCard.Params(cardMock))
    }

    @Test(expected = KnownException::class)
    fun testrunErrorCase() = runBlocking {
        whenever(cardsRepositoryMock.deleteCard(cardMock)).thenThrow(KnownException::class.java)
        val result = deleteCardUseCase.run(DeleteCard.Params(cardMock))
    }
}