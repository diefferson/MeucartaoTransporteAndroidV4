package br.com.disapps.meucartaotransporte.ui.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.disapps.meucartaotransporte.R

class CustomSchedule : CardView{

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        bindAttributes(attrs)
    }

    private lateinit var adaptImageView : ImageView
    private lateinit var scheduleTextView : TextView

    private fun bindAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSchedule)

        val isAdapt = typedArray.getBoolean(R.styleable.CustomSchedule_isAdapt, false)
        val schedule = typedArray.getString(R.styleable.CustomSchedule_schedule)

        typedArray.recycle()

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_schedule, this, true)

        val baseView = getChildAt(0) as ConstraintLayout
        adaptImageView = baseView.findViewById(R.id.adapt)
        scheduleTextView = baseView.findViewById(R.id.schedule)


        scheduleTextView.text = schedule
        adaptImageView.visibility = View.GONE

        if(isAdapt){
            adaptImageView.visibility = View.VISIBLE
        }
    }

    fun setSchedule(schedule:String){
        scheduleTextView.text = schedule
    }

    fun setIsAdapt(isAdapt : Boolean) {
        adaptImageView.visibility = View.GONE
        if (isAdapt) {
            adaptImageView.visibility = View.VISIBLE
        }
    }
}