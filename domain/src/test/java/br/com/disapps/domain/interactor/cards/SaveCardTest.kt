package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import br.com.disapps.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class SaveCardTest{

    private lateinit var saveCard: SaveCard
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()
    private val mockCard:Card = mock()

    @Before
    fun setup(){
        saveCard = SaveCard(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testSaveCardUseCaseObservableHappyCase() {
        saveCard.buildUseCaseObservable(SaveCard.Params(mockCard))

        verify(mockCardsRepository).saveCard(mockCard)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        saveCard.buildUseCaseObservable(null!!)
    }

}