package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread
import org.junit.Before
import org.junit.Test

class GetExtractTest{

    private lateinit var getExtract: GetExtract
    private val mockCardsRepository : CardsRepository = mock()
    private val mockThreadExecutor: ThreadExecutor = mock()
    private val mockPostExecutionThread: PostExecutionThread = mock()
    private val mockCard:Card = mock()

    private lateinit var extracts: ArrayList<Extract>

    @Before
    fun setup(){
        extracts = ArrayList()
        whenever(mockCardsRepository.extract(mockCard)).thenReturn(Single.just(extracts))
        getExtract = GetExtract(mockCardsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun testGetExtractUseCaseObservableHappyCase() {
        getExtract.buildUseCaseObservable(GetExtract.Params(mockCard))

        verify(mockCardsRepository).extract(mockCard)
        verifyNoMoreInteractions(mockCardsRepository)
        verifyZeroInteractions(mockPostExecutionThread)
        verifyZeroInteractions(mockThreadExecutor)
    }

    @Test
    fun getExtractAPIShouldReturnData(){
        val observableTest = getExtract.buildUseCaseObservable(GetExtract.Params(mockCard)).test()
        observableTest.assertValue(extracts)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailWhenNoOrEmptyParameters() {
        getExtract.buildUseCaseObservable(null!!)
    }

}