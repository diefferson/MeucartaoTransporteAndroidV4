package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.repository.LinesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test

class SaveAllLinesJsonTest{

    private val linesRepositoryMock: LinesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val downloadListenerMock:DownloadProgressListener = mock()
    private lateinit var saveAllLinesJson: SaveAllLinesJson

    @Before
    fun setup(){
        saveAllLinesJson = SaveAllLinesJson(linesRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(linesRepositoryMock.saveAllLinesFromJson(MockData.PATH, downloadListenerMock)).then {
            downloadListenerMock.onAttachmentDownloadUpdate(10)
            downloadListenerMock.onAttachmentDownloadUpdate(50)
            downloadListenerMock.onAttachmentDownloadUpdate(100)
        }
        saveAllLinesJson.buildUseCaseObservable(SaveAllLinesJson.Params(MockData.PATH,downloadListenerMock ))
        verify(downloadListenerMock).onAttachmentDownloadUpdate(10)
        verify(downloadListenerMock).onAttachmentDownloadUpdate(50)
        verify(downloadListenerMock).onAttachmentDownloadUpdate(100)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(linesRepositoryMock.saveAllLinesFromJson(MockData.PATH, downloadListenerMock)).thenThrow(KnownException::class.java)
        val result = saveAllLinesJson.buildUseCaseObservable(SaveAllLinesJson.Params(MockData.PATH,downloadListenerMock ))
    }
}