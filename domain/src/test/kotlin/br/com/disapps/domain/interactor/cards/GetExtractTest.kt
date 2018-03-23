package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class GetExtractTest{

    private lateinit var getExtract: GetExtract
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()

    private lateinit var extracts: ArrayList<Extract>

    @Before
    fun setup(){
        extracts = ArrayList()
        whenever(mockCardsRepository.extract(FAKE_CARD)).thenReturn(Observable.just(extracts))
        getExtract = GetExtract(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)

    }

    @Test
    fun testGetExtractUseCaseObservableHappyCase() {
        getExtract.buildUseCaseObservable(GetExtract.Params.forCard(FAKE_CARD))

        verify(mockCardsRepository).extract(FAKE_CARD)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test
    fun getExtractAPIShouldReturnData(){
        val observableTest = getExtract.buildUseCaseObservable(GetExtract.Params.forCard(FAKE_CARD)).test()
        observableTest.assertValue(extracts)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        getExtract.buildUseCaseObservable(null!!)
    }

    companion object {
        val FAKE_CARD = Card("0002909840", "09695910980")
    }
}