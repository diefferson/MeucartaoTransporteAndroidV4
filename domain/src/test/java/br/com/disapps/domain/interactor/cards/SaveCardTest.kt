package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveCardTest{
    private val cardsRepositoryMock: CardsRepository = mock()
    private val cardMock: Card = mock()

    private lateinit var saveCardUseCase: SaveCard

    @Before
    fun setup(){
        saveCardUseCase = SaveCard(cardsRepositoryMock)
    }


    @Test
    fun testrunSuccessCase() = runBlocking {
        val result = saveCardUseCase.run(SaveCard.Params(cardMock))
    }

    @Test(expected = KnownException::class)
    fun testrunErrorCase() = runBlocking {
        whenever(cardsRepositoryMock.saveCard(cardMock)).thenThrow(KnownException::class.java)
        val result = saveCardUseCase.run(SaveCard.Params(cardMock))
    }
}