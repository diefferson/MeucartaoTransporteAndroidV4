package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.*
import br.com.disapps.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class GetCardTest{

    private lateinit var getCard: GetCard
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()
    private val mockCard:Card = mock()

    @Before
    fun setup(){
        getCard = GetCard(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testGetCardUseCaseObservableHappyCase() {
        getCard.buildUseCaseObservable(GetCard.Params(mockCard))

        verify(mockCardsRepository).card(mockCard)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        getCard.buildUseCaseObservable(null!!)
    }

}