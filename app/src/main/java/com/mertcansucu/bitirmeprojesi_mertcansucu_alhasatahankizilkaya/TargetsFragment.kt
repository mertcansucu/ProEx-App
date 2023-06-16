package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView
import androidx.drawerlayout.widget.DrawerLayout


class TargetsFragment : Fragment() {
    private lateinit var videoView:VideoView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_targets, container, false)
        videoView=view.findViewById(R.id.videoView)
        val videoPath = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.youtubevideo)
        videoView.setVideoURI(videoPath)
        val mediaController = android.widget.MediaController(requireContext())
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()

        return view
    }
}