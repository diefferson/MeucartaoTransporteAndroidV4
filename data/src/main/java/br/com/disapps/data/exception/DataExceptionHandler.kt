package br.com.disapps.data.exception

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RetornoCartao
import br.com.disapps.data.entity.RetornoExtrato
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException

fun cardOrError(response : RetornoCartao) : Cartao{
    if(response.code != "000"){
        throw errorHandler(response.code)
    }

    if(response.content == null){
        throw KnownException(KnownError.UNKNOWN_EXCEPTION)
    }

    return response.content!!
}

fun extractOrError(response: RetornoExtrato): List<Extrato>{
    if(response.code != "000"){
        throw errorHandler(response.code)
    }

    if(response.content == null){
        throw KnownException(KnownError.UNKNOWN_EXCEPTION)
    }

    return response.content!!
}

private fun errorHandler(code : String) : KnownException {
    return when(code){
        "001" -> KnownException(KnownError.INVALID_ARGUMENT_EXCEPTION)
        "002" -> KnownException(KnownError.INVALID_OPERATION_EXCEPTION)
        "003" -> KnownException(KnownError.INVALID_DOCUMENT_EXCEPTION)
        "004" -> KnownException(KnownError.INVALID_CARD_EXCEPTION)
        "005" -> KnownException(KnownError.LINK_DOCUMENT_CARD_EXCEPTION)
        "006" -> KnownException(KnownError.INACTIVE_CARD_EXCEPTION)
        "007" -> KnownException(KnownError.UNAUTHORIZED_EXCEPTION)
        "008" -> KnownException(KnownError.INVALID_LINE_EXCEPTION)
        "009" -> KnownException(KnownError.EMPTY_EXCEPTION)
        "011" -> KnownException(KnownError.MAINTENANCE_EXCEPTION)
        else -> KnownException(KnownError.NETWORK_EXCEPTION)
    }
}
