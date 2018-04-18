package br.com.disapps.domain.listeners

interface DownloadProgressListener {
    fun onAttachmentDownloadUpdate(percent: Int)
}