package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class GetCardsTest{

    private lateinit var getCards: GetCards
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()

    @Before
    fun setup(){
        getCards = GetCards(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testGetCardsUseCaseObservableHappyCase() {
        getCards.buildUseCaseObservable(Unit)

        verify(mockCardsRepository).cards()
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        getCards.buildUseCaseObservable(null!!)
    }
}