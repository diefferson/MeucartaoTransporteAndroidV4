package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.CardsRepository

/**
 * Created by dnso on 15/03/2018.
 */
class GetPassValue(val cardRepository: CardsRepository) : UseCase<Float, UseCase.None>() {

    override suspend fun run(params: None): Float {
        return this.cardRepository.getPassValue()
    }

}