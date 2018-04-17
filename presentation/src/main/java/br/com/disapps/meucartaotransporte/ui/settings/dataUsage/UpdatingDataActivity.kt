package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.services.updateLines.UpdateLinesService
import kotlinx.android.synthetic.main.activity_updating_data.*

class UpdatingDataActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updating_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        back_button.setOnClickListener { finish() }
        setupUpdate()
    }

    private fun setupUpdate(){
        intent.extras?.let {
            when(it.getInt(UPDATE)){
                UpdateData.LINES.toInt() -> UpdateLinesService.startService(this)
            }
        }
    }

    companion object {
        private const val UPDATE = "updateData"
        fun launch(context: Context, updateData :UpdateData){
            context.startActivity(Intent(context, UpdatingDataActivity::class.java).apply {
                putExtra(UPDATE, updateData.toInt())
            })
        }
    }

}