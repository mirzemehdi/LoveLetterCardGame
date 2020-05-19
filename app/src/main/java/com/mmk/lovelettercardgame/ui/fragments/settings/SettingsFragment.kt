package com.mmk.lovelettercardgame.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.Constants
import kotlinx.android.synthetic.main.fragment_settings_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_volume.updateVolumeValue(Constants.CURRENT_VOLUME_MUSIC)
        sb_volume.progress=Constants.CURRENT_VOLUME_MUSIC
        sb_volume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view_volume.updateVolumeValue(progress)
                Constants.CURRENT_VOLUME_MUSIC=progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}
