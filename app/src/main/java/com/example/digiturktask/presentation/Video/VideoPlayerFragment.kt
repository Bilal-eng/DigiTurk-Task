package com.example.digiturktask.presentation.Video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.example.digiturktask.databinding.FragmentVideoPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(UnstableApi::class)
class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding
    private var player: ExoPlayer? = null
    private var isPlayerPrepared = false

    private val lifecycleObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> onPausePlayer()
            Lifecycle.Event.ON_RESUME -> onResumePlayer()
            else -> Unit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title")
        val videoUri = arguments?.getString("videoUrl")
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
        initializePlayer(Uri.parse(videoUri))
        // Add this fragment as a lifecycle observer
        lifecycle.addObserver(lifecycleObserver)
    }

    private fun initializePlayer(videoUri: Uri) {
        val dataSourceFactory = DefaultDataSource.Factory(requireContext())
        val mediaItem = MediaItem.Builder()
            .setUri(videoUri)
            .build()
        // Create an HlsMediaSource
        val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
        player = ExoPlayer.Builder(requireContext()).build()
        player?.prepare(hlsMediaSource)
        // Add a listener to know when the player is prepared
        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    // Player is prepared
                    isPlayerPrepared = true
                    hideLoadingIndicator()
                } else if (playbackState == Player.STATE_BUFFERING) {
                    // Player is buffering
                    showLoadingIndicator()
                }
            }
        })
        player?.playWhenReady = true
        binding?.exoPlayerView?.player = player
    }

    private fun showLoadingIndicator() {
        // Show your loading indicator (e.g., ProgressBar)
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        // Hide your loading indicator (e.g., ProgressBar)
        binding?.progressBar?.visibility = View.GONE
    }


    // Pause ExoPlayer when the fragment is paused
    private fun onPausePlayer() {
        player?.playWhenReady = false
    }

    // Resume ExoPlayer when the fragment is resumed
    private fun onResumePlayer() {
        player?.playWhenReady = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        player?.release()
    }
}