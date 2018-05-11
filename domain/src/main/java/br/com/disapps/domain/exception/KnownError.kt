package br.com.disapps.domain.exception

enum class KnownError{

    //NETWORK ERRORS
    UNKNOWN_EXCEPTION,
    NETWORK_EXCEPTION,
    NOT_FOUND_EXCEPTION,
    EMPTY_EXCEPTION,
    INVALID_ARGUMENT_EXCEPTION,
    INVALID_OPERATION_EXCEPTION,
    UNAUTHORIZED_EXCEPTION,
    MAINTENANCE_EXCEPTION,
    URBS_RETURN_ERROR,
    //CUSTOM ERRORS
    CARD_EXISTS_EXCEPTION,
    INVALID_CARD_EXCEPTION,

    INVALID_LINE_EXCEPTION,
    INVALID_DOCUMENT_EXCEPTION,
    INACTIVE_CARD_EXCEPTION,
    LINK_DOCUMENT_CARD_EXCEPTION,
}