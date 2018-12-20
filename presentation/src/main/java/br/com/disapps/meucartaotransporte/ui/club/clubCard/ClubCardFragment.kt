package br.com.disapps.meucartaotransporte.ui.club.clubCard

import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_club_card.*
import org.koin.android.viewmodel.ext.android.viewModel

class ClubCardFragment :BaseFragment(){

    override val viewModel: ClubCardViewModel by viewModel()
    override val fragmentLayout = R.layout.fragment_club_card
    override val fragmentTag = "ClubCardFragment"

    companion object {
        fun newInstance() = ClubCardFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text="123456789"
        val multiFormatWriter =  MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qr_code.setImageBitmap(bitmap)
        } catch ( e: WriterException) {
            e.printStackTrace()
        }
    }
}