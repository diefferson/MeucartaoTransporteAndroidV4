package br.com.disapps.meucartaotransporte.ui.lines.searchView

import android.annotation.SuppressLint
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesAdapter
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import kotlinx.android.synthetic.main.fragment_list_lines.*
import org.koin.android.architecture.ext.viewModel

class SearchViewActivity : BaseActivity(){

    override val viewModel by viewModel<SearchViewViewModel>()

    override val activityLayout = R.layout.activity_search_view

    private val adapter: LinesAdapter by lazy{
        LinesAdapter(ArrayList()).apply {
            emptyView = inflateView(R.layout.empty_view, lines_recycler )
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.fav_line -> {
                        viewModel.favoriteLine(adapter.data[position] as Line)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lines_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SearchViewActivity.adapter
        }

        viewModel.lines.observe(this, Observer {
            adapter.setNewData(it)
        })
    }


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_searchable_activity, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView

        val item = menu?.findItem(R.id.action_search_lines)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = item?.actionView as SearchView
        } else {
            searchView = MenuItemCompat.getActionView(item) as SearchView
        }

        val closeButton = searchView.findViewById<View>(R.id.search_close_btn) as ImageView
        closeButton.isEnabled = false
        closeButton.setImageDrawable(resources.getDrawable(R.drawable.transparent))

        searchView.isIconified = false

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.filterLines(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
               // viewModel.filterLines(newText)
                return true
            }
        })

        viewModel.filterLines("")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleSearch(intent)
    }

    private fun handleSearch(intent: Intent) {
        if (Intent.ACTION_SEARCH.equals(intent.action!!, ignoreCase = true)) {
            val q = intent.getStringExtra(SearchManager.QUERY)
            viewModel.filterLines(q)
        }
    }

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, SearchViewActivity::class.java)
            context.startActivity(intent)
        }
    }

}