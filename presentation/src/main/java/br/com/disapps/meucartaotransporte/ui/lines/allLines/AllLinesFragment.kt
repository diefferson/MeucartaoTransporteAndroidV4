package br.com.disapps.meucartaotransporte.ui.lines.allLines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesAdapter
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.util.custom.ObservableScrollViewCallbacks
import br.com.disapps.meucartaotransporte.util.custom.ScrollUtils
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import kotlinx.android.synthetic.main.fragment_list_lines.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesFragment : BaseFragment() {

    override val viewModel by viewModel<LinesViewModel>()

    override val fragmentLayout = R.layout.fragment_list_lines

    private val adapter:LinesAdapter by lazy{
        LinesAdapter(ArrayList()).apply {
            emptyView = activity?.inflateView(R.layout.empty_view, lines_recycler )
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.fav_line -> {
                        viewModel.favoriteLine(adapter.data[position] as Line)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lines_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AllLinesFragment.adapter
        }

        if (activity is ObservableScrollViewCallbacks) {
            // Scroll to the specified offset after layout
            val args = arguments
            if (args != null && args.containsKey(ARG_SCROLL_Y)) {
                val scrollY = args.getInt(ARG_SCROLL_Y, 0)
                ScrollUtils.addOnGlobalLayoutListener(lines_recycler, Runnable { lines_recycler.scrollTo(0, scrollY) })
            }

            // TouchInterceptionViewGroup should be a parent view other than ViewPager.
            // This is a workaround for the issue #117:
            // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
            lines_recycler.setTouchInterceptionViewGroup(activity!!.findViewById<ViewGroup>(R.id.vContainer) )

            lines_recycler.setScrollViewCallbacks(activity as ObservableScrollViewCallbacks)
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.lines.observe(this, Observer {
            adapter.setNewData(it)
        })
    }

    companion object {
        const val ARG_SCROLL_Y = "scroll_y"
        fun newInstance(mScrollY: Int = 0):AllLinesFragment {
            val f = AllLinesFragment()
            val args = Bundle()
            args.putInt(ARG_SCROLL_Y, mScrollY)
            f.arguments = args
            return f
        }
    }
}