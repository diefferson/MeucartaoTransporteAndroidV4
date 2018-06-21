package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.widget.Toast
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.app.App
import br.com.disapps.meucartaotransporte.services.*
import br.com.disapps.meucartaotransporte.base.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.base.util.PermissionsUtils
import kotlinx.android.synthetic.main.activity_data_usage.*
import kotlinx.coroutines.experimental.async
import org.koin.android.architecture.ext.viewModel

class DataUsageActivity : BaseActivity(){

    override val viewModel by viewModel<DataUsageViewModel>()
    override val activityLayout = R.layout.activity_data_usage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
        setupClickListeners()
    }

    private fun setupClickListeners() {

        update_line.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadLinesService.startService(App.instance as Context) }
            }
        }
        update_schedule.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadSchedulesService.startService(App.instance as Context) }
            }
        }
        update_cwb_shapes.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadShapesService.startService(App.instance as Context, City.CWB) }
            }
        }
        update_met_shapes.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadShapesService.startService(App.instance as Context, City.MET) }
            }
        }
        update_cwb_itineraries.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadItinerariesService.startService(App.instance as Context, City.CWB) }
            }
        }
        update_met_itineraries.setOnClickListener {
            if (PermissionsUtils.requestPermission(this, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)) {
                async { DownloadItinerariesService.startService(App.instance as Context, City.MET) }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionsUtils.WRITE_STORAGE_CODE // Allowed was selected so Permission granted
            -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.all_right))
                    setMessage(getString(R.string.can_download))
                    setCancelable(false)
                    setNeutralButton(getString(R.string.ok)) {_, _-> }
                }.create().show()

            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                /*Usuario marcou opção não perguntar novamente*/
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.necessary_permission))
                    setMessage(getString(R.string.save_files_permission))
                    setCancelable(false)

                    setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        Toast.makeText(this@DataUsageActivity, getString(R.string.error_save_data), Toast.LENGTH_SHORT).show()
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
                            PermissionsUtils.requestPermission(this@DataUsageActivity, PermissionsUtils.WRITE_STORAGE_PERMISSION, PermissionsUtils.WRITE_STORAGE_CODE)
                        }

                        setNegativeButton(getString(R.string.cancel)) { _, _ ->
                            Toast.makeText(this@DataUsageActivity, getString(R.string.error_save_data), Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }
        }
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}