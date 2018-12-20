package br.com.disapps.meucartaotransporte.ui.club.promotions

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.model.ClubPromotion
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class PromotionsViewModel: BaseViewModel(){

    val promotions = MutableLiveData<List<ClubPromotion>>()

    fun getPromotions(){
        promotions.value = mockPromotions()
    }

    private fun mockPromotions() = ArrayList<ClubPromotion>().apply{
        add(ClubPromotion("Teste 1", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 2", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 3", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 4", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 5", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 6", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 7", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 8", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 9", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
        add(ClubPromotion("Teste 10", "Teste 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In finibus, nisl non ultricies suscipit, risus lorem aliquam purus, condimentum bibendum neque tortor quis diam. Suspendisse rutrum purus eget eleifend scelerisque. Nunc non dui neque. Nam hendrerit quam quis quam vestibulum, vitae egestas elit elementum"))
    }

}