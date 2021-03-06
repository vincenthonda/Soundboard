package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    private  lateinit var binding: ActivityMainBinding

    lateinit var soundPool : SoundPool
    lateinit var SimpleSong: Button
    val noteMap =  HashMap<String,Int>()
    var aNote = 0
    var bbNote = 0
    var bNote = 0
    var cNote = 0
    var ccNote = 0
    var dNote = 0
    var ddNote = 0
    var eNote = 0
    var fNote = 0
    var ffNote = 0
    var gNote = 0
    var ggNote = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSoundPool()
        setListeners()

        var song = setGson()
        playSong(song)
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        binding.buttonMainA.setOnClickListener(soundBoardListener)
        binding.buttonMainB.setOnClickListener(soundBoardListener)
        binding.buttonMainBb.setOnClickListener(soundBoardListener)
        binding.buttonMainC.setOnClickListener(soundBoardListener)
        binding.buttonMainCc.setOnClickListener(soundBoardListener)
        binding.buttonMainD.setOnClickListener(soundBoardListener)
        binding.buttonMainDd.setOnClickListener(soundBoardListener)
        binding.buttonMainE.setOnClickListener(soundBoardListener)
        binding.buttonMainF.setOnClickListener(soundBoardListener)
        binding.buttonMainFf.setOnClickListener(soundBoardListener)
        binding.buttonMainG.setOnClickListener(soundBoardListener)
        binding.buttonMainGg.setOnClickListener(soundBoardListener)
        binding.buttonMainSimpleSong.setOnClickListener(soundBoardListener)
    }



    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
//        })
        aNote = soundPool.load(this, R.raw.scalea, 1)
        bbNote = soundPool.load(this, R.raw.scalebb, 1)
        bNote = soundPool.load(this, R.raw.scaleb, 1)
        cNote =  soundPool.load(this, R.raw.scalec, 1)
        ccNote = soundPool.load(this, R.raw.scalecs, 1)
        dNote = soundPool.load(this, R.raw.scaled, 1)
        ddNote =soundPool.load(this, R.raw.scaleds, 1)
        eNote = soundPool.load(this, R.raw.scalee, 1)
        fNote = soundPool.load(this, R.raw.scalef, 1)
        ffNote = soundPool.load(this, R.raw.scalefs, 1)
        gNote = soundPool.load(this, R.raw.scaleg, 1)
        ggNote = soundPool.load(this, R.raw.scalegs, 1)

        noteMap["A"] = aNote
        noteMap["B"] = bNote
        noteMap["Bb"] =bbNote
        noteMap["C"] = cNote
        noteMap["Cc"] = ccNote
        noteMap["D"] = dNote
        noteMap["Dd"] = ddNote
        noteMap["E"] = eNote
        noteMap["F"] = fNote
        noteMap["Ff"] = ffNote
        noteMap["G"] = gNote
        noteMap["Gg"] = ggNote

    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    private fun playNote(note: String){
        playNote(noteMap[note] ?: 0)
    }
    fun setGson() : List<Note> {
        val inputStream = resources.openRawResource(R.raw.song)
        val jsonText = inputStream.bufferedReader().use{it.readText()}
        Log.d(TAG, "onCreate: $jsonText")
        val gson = Gson()
        val oType = object: TypeToken<List<Note>>(){}.type
        var song = gson.fromJson<List<Note>>(jsonText, oType)
        Log.d(TAG, "onCreate: $song" )
        return song
    }

    private fun playSong(song: List<Note>) {
        // loop through our note objects in the list and play them in order
        // use the existing playNote functions to help
    }
    private suspend fun playsimpleSong(){

            playNote("A"
                    )
            delay(600)
            playNote("C")
            delay(600)
            playNote("Cc")
            delay(600)
            playNote("D")
            delay(600)
            playNote("Dd")
            delay(600)
            playNote("E")
            delay(600)
            playNote("F")
            delay(600)
            playNote("Ff")
            delay(600)


    }
    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.button_main_a -> playNote(aNote)
                R.id.button_main_bb -> playNote(bbNote)
                R.id.button_main_b -> playNote(bNote)
                R.id.button_main_c -> playNote(cNote)
                R.id.button_main_cc -> playNote(ccNote)
                R.id.button_main_d -> playNote(dNote)
                R.id.button_main_dd -> playNote(ddNote)
                R.id.button_main_e -> playNote(eNote)
                R.id.button_main_f -> playNote(fNote)
                R.id.button_main_ff -> playNote(ffNote)
                R.id.button_main_g -> playNote(gNote)
                R.id.button_main_gg -> playNote(ggNote)
                R.id.button_main_simpleSong -> GlobalScope.launch {
                    playsimpleSong()
                }
            }
        }
    }
}