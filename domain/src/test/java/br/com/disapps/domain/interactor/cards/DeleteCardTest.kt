package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.*
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class DeleteCardTest{

    private lateinit var deleteCard: DeleteCard
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()
    private val mockCard:Card = mock()

    @Before
    fun setup(){
        deleteCard = DeleteCard(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testDeleteCardUseCaseObservableHappyCase() {
        deleteCard.buildUseCaseObservable(DeleteCard.Params.forCard(mockCard))

        verify(mockCardsRepository).deleteCard(mockCard)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        deleteCard.buildUseCaseObservable(null!!)
    }
}