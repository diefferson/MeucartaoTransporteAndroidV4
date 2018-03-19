package br.com.disapps.meucartaotransporte.ui.quickFind

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindFragment: BaseFragment(){

    companion object {
        fun newInstance() = QuickFindFragment()
    }

    override val viewModel: QuickFindViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_quick_find


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObservers()
    }

    private fun viewModelObservers() {
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
    }

}