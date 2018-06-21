package br.com.disapps.meucartaotransporte.ui.intro

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.services.ScheduleJob
import br.com.disapps.meucartaotransporte.base.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import br.com.disapps.meucartaotransporte.base.util.PermissionsUtils
import kotlinx.android.synthetic.main.activity_intro.*
import org.koin.android.architecture.ext.viewModel


class IntroActivity : BaseActivity(){

    override val viewModel by viewModel<IntroViewModel>()

    override val activityLayout = R.layout.activity_intro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUpdate()

        setupClickListeners()

        observeViewModel()
    }

    override fun recreate() {
        super.recreate()
        viewModel.isRecreated = true
        viewModel.isError.value = false
        viewModel.isComplete.value = false
    }

    private fun observeViewModel() {
        viewModel.isComplete.observe(this, Observer {
            if (it != null && it) {
                intro_text.visibility = View.GONE
                btn_continue.visibility = View.VISIBLE
                progress.setPercent(100)
                ScheduleJob.schedule(this, ScheduleJob.LINE_TYPE)
                ScheduleJob.schedule(this, ScheduleJob.SCHEDULE_TYPE)
            }
        })

        viewModel.isError.observe(this, Observer {
            if(it!= null && it){
                downloading.visibility = View.GONE
                error.visibility = View.VISIBLE
            }
        })
    }

    private fun setupClickListeners() {
        btn_continue.setOnClickListener {
            downloading.visibility = View.GONE
            alert.visibility = View.VISIBLE
        }

        btn_ok.setOnClickListener {
            MainActivity.launch(this)
            finish()
        }

        btn_try_again.setOnClickListener {
            recreate()
        }
    }

    private fun setupUpdate(){
        if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
            progress.startDeterminate()
            progress.setPercent(0)
            viewModel.initData(cacheDir.absolutePath)
            viewModel.progress.observe(this, Observer {
                it?.let {
                    progress.setPercent((it / 2) - 1)
                }
            })
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionsUtils.WRITE_STORAGE_CODE // Allowed was selected so Permission granted
            -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                setupUpdate()

            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                /*Usuario marcou opção não perguntar novamente*/
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.necessary_permission))
                    setMessage(getString(R.string.save_files_permission))
                    setCancelable(false)

                    setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        Toast.makeText(this@IntroActivity, getString(R.string.error_save_data), Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    setPositiveButton(getString(R.string.settings)) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 1000)     // Comment 3.
                    }
                }.create().show()

            } else {
                /*Usuario negou a permissão*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.necessary_permission))
                        setMessage(getString(R.string.save_files_permission))
                        setCancelable(false)

                        setPositiveButton("OK") { _, _ ->
                            PermissionsUtils.requestPermission(this@IntroActivity, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)
                        }

                        setNegativeButton(getString(R.string.cancel)) { _, _ ->
                            Toast.makeText(this@IntroActivity, getString(R.string.error_save_data), Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }
        }
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }
}