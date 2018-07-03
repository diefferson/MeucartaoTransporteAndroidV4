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

class UpdateCardTest{
    private val cardsRepositoryMock: CardsRepository = mock()
    private val logExceptionMock: LogException = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on { scheduler } doReturn(Unconfined) }
    private val postExecutionContextMock: PostExecutionContext = mock{ on { scheduler } doReturn(Unconfined) }
    private val cardMock: Card = mock()

    private lateinit var updateCardUseCase: UpdateCard

    @Before
    fun setup(){
        updateCardUseCase = UpdateCard(cardsRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }


    @Test
    fun testBuildUseCaseObservableSuccessCase() = runBlocking {
        val result = updateCardUseCase.buildUseCaseObservable(UpdateCard.Params(cardMock))
    }

    @Test(expected = KnownException::class)
    fun testBuildUseCaseObservableErrorCase() = runBlocking {
        whenever(cardsRepositoryMock.updateCard(cardMock)).thenThrow(KnownException::class.java)
        val result = updateCardUseCase.buildUseCaseObservable(UpdateCard.Params(cardMock))
    }
}