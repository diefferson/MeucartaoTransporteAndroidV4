package br.com.disapps.data.entity.mock

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Linha
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Line

object MockData{

    const val FAKE_CODE = "2909840"
    const val FAKE_CPF = "0969591980"
    const val FAKE_NAME = "Cartão Teste"
    const val FAKE_TYPE = "Usuário"
    const val FAKE_STATUS = "Ativo"
    const val FAKE_BALANCE = 59.05
    const val FAKE_BALANCE_DATE = "22/03/2018 08:17:51"
    const val BALANCE = "saldo"
    const val EXTRACT = "extrato"
    const val FAKE_LINE_CODE = "040"
    const val FAKE_LINE_NAME = "Interbairros IV"
    const val FAKE_LINE_CATEGORY = "Interbairros"
    const val FAKE_LINE_COLOR = "green"
    const val FAKE_LINE_FAVORITE = false
    const val FAKE_LINE_FAVORITE_INT = 0

    fun cardBO() = Card(
        FAKE_CODE,
        FAKE_CPF,
        FAKE_NAME,
        FAKE_TYPE,
        FAKE_STATUS,
        FAKE_BALANCE,
        FAKE_BALANCE_DATE
    )

    fun cardDTO() = Cartao().apply {
        codigo = FAKE_CODE
        cpf = FAKE_CPF
        nome = FAKE_NAME
        tipo = FAKE_TYPE
        estado = FAKE_STATUS
        saldo = FAKE_BALANCE
        data_saldo = FAKE_BALANCE_DATE
    }

    fun listCardBO():List<Card> = arrayListOf(cardBO(), cardBO(), cardBO())

    fun listCardDTO():List<Cartao> = arrayListOf(cardDTO(), cardDTO(), cardDTO())

    fun requestCard() = RequestCartao().apply {
        codigo = FAKE_CODE
        cpf = FAKE_CPF
    }

    fun lineBO()= Line(
        FAKE_LINE_CODE,
        FAKE_LINE_NAME,
        FAKE_LINE_CATEGORY,
        FAKE_LINE_COLOR,
        FAKE_LINE_FAVORITE
    )

    fun lineDTO() = Linha().apply{
        codigo =FAKE_LINE_CODE
        nome =FAKE_LINE_NAME
        categoria =FAKE_LINE_CATEGORY
        cor =FAKE_LINE_COLOR
        favorito =FAKE_LINE_FAVORITE_INT
    }

    fun listLineBO()= arrayListOf(lineBO(), lineBO(), lineBO())

    fun listLineDTO() = arrayListOf(lineDTO(), lineDTO(), lineDTO())

}
