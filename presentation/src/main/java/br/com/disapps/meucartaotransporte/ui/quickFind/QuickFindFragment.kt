package br.com.disapps.meucartaotransporte.ui.quickFind

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.databinding.FragmentQuickFindBinding
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_quick_find.*
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindFragment: BaseFragment<FragmentQuickFindBinding>(){

    companion object {
        fun newInstance() = QuickFindFragment()
    }

    override val viewModel: QuickFindViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_quick_find

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_quick_find.setOnClickListener { viewModel.consult(card_code.text.toString(),card_cpf.text.toString() ) }

    }



}