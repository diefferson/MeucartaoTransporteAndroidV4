package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteCardTest{

    private val cardsRepositoryMock: CardsRepository = mock()
    private val cardMock:Card = mock()

    private lateinit var deleteCardUseCase: DeleteCard

    @Before
    fun setup(){
        deleteCardUseCase = DeleteCard(cardsRepositoryMock)
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