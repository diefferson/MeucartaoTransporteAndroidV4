package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCardsTest{

    private val cardsRepositoryMock: CardsRepository = mock()
    private val cardsMock: List<Card> = mock()

    private lateinit var getCardsUseCase: GetCards

    @Before
    fun  setup(){
        getCardsUseCase = GetCards(cardsRepositoryMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(cardsRepositoryMock.cards()).thenReturn(cardsMock)
        val result = getCardsUseCase.run(UseCase.None())
        assertEquals(cardsMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(cardsRepositoryMock.cards()).thenThrow(KnownException::class.java)
        val result = getCardsUseCase.run(UseCase.None())
    }

}