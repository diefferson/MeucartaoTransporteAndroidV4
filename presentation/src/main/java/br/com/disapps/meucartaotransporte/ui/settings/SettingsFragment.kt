package br.com.disapps.meucartaotransporte.ui.settings

import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsFragment : BaseFragment(){

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val viewModel: SettingsViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iAppActivityListener.setTitle(getString(R.string.settings))
    }
}