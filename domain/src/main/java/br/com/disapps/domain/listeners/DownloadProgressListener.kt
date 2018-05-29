package br.com.disapps.domain.listeners

import java.io.Serializable

interface DownloadProgressListener : Serializable {
    fun onAttachmentDownloadUpdate(percent: Int)
}