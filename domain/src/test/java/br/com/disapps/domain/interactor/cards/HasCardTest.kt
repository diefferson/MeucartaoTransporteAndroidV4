package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class HasCardTest{

    private lateinit var hasCard: HasCard
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()
    private val mockCard:Card = mock()

    @Before
    fun setup(){
        hasCard = HasCard(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testHasCardUseCaseObservableHappyCase() {
        hasCard.buildUseCaseObservable(HasCard.Params.forCard(mockCard))

        verify(mockCardsRepository).hasCard(mockCard)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        hasCard.buildUseCaseObservable(null!!)
    }
}