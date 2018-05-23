package br.com.disapps.meucartaotransporte.services

import android.content.Context
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.RetryStrategy
import com.firebase.jobdispatcher.Trigger
import com.firebase.jobdispatcher.Lifetime
import android.os.Bundle
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.FirebaseJobDispatcher

class ScheduleJob : JobService(){

    override fun onStopJob(job: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        val jobType = job?.extras?.getString(JOB_TYPE)
        when(jobType){
            SCHEDULE_TYPE ->  UpdateSchedulesService.startService(this, false)
            LINE_TYPE-> UpdateLinesService.startService(this, false)
        }
        return false
    }

    companion object {

        const val SCHEDULE_TYPE = "schedule"
        const val LINE_TYPE = "line"
        const val JOB_TYPE = "jobType"

        fun schedule(context:Context,  jobType:String){

            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

            //cancel previous job
            dispatcher.cancel(jobType)

            val myExtrasBundle = Bundle().apply {
                putString(JOB_TYPE, jobType)
            }

            val myJob = dispatcher.newJobBuilder()
                    .setService(ScheduleJob::class.java)
                    .setTag(jobType)
                    // one-off job
                    .setRecurring(true)
                    // don't persist past a device reboot
                    .setLifetime(Lifetime.FOREVER)
                    // start between 0 and 60 seconds from now
                    .setTrigger(Trigger.executionWindow(604800, 604900))
                    // don't overwrite an existing job with the same tag
                    .setReplaceCurrent(false)
                    // retry with exponential backoff
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    // constraints that need to be satisfied for the job to run
                    .setConstraints(
                            // only run on an unmetered network
                            Constraint.ON_UNMETERED_NETWORK
                    )
                    .setExtras(myExtrasBundle)
                    .build()

            dispatcher.mustSchedule(myJob)
        }
    }
}