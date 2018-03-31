package br.com.disapps.meucartaotransporte.ui.settings

import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.settings.help.HelpActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsFragment : BaseFragment(){

    override val viewModel by viewModel<SettingsViewModel>()

    override val fragmentLayout = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iAppActivityListener.setTitle(getString(R.string.settings))

        help.setOnClickListener { HelpActivity.launch(context!!) }
    }


    companion object {
        fun newInstance() = SettingsFragment()
    }

}