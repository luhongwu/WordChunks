/*
 * Copyright 2017 Julia Kozhukhovskaya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appchamp.wordchunks.ui.game

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appchamp.wordchunks.R
import com.appchamp.wordchunks.extensions.invisible
import com.appchamp.wordchunks.extensions.visible
import com.appchamp.wordchunks.ui.customviews.StoreDialog
import com.appchamp.wordchunks.ui.game.adapters.ChunksAdapter
import com.appchamp.wordchunks.ui.game.adapters.WordsAdapter
import com.appchamp.wordchunks.ui.smallbang.SmallBang
import com.appchamp.wordchunks.util.Constants.CHUNKS_GRID_NUM
import com.appchamp.wordchunks.util.Constants.WORDS_GRID_NUM
import kotlinx.android.synthetic.main.frag_game.*


class GameFragment : LifecycleFragment() {
    private val TAG: String = javaClass.simpleName
    private val viewModel by lazy { ViewModelProviders.of(activity).get(GameViewModel::class.java) }
    private val smallBang by lazy { SmallBang.attach2Window(activity) }
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var chunksAdapter: ChunksAdapter

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.frag_game, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeUi(viewModel) // Create and set the adapters for the RecyclerViews.
        setGameGradient()
        setupWordsAdapter()
        setupChunksAdapter()

        // Click listeners
        btnShuffle.setOnClickListener {
            viewModel.onShuffleClick()
            chunksAdapter.notifyDataSetChanged()
        }
        btnHint.setOnClickListener { onHintClicked() }
        btnClear.setOnClickListener {
            viewModel.onClearClick().forEach {
                chunksAdapter.notifyItemChanged(it)
            }
        }
    }

    private fun setGameGradient() {
        // get current level color and next level color
        val currentLevelColor = viewModel.getLiveLevel().value?.color ?: "#7bda7a"
        val nextLevelColor = viewModel.getNextLevelColor() ?: currentLevelColor
        val colors = intArrayOf(Color.parseColor(currentLevelColor), Color.parseColor(nextLevelColor))
        val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
        gradient.background = gd
    }

    private fun onHintClicked() {
        if (viewModel.getUser().value?.hints!! > 0) {
            val wordPos = viewModel.onHintClicked()
            if (wordPos != -1) {
                wordsAdapter.notifyItemChanged(wordPos)
                smallBang?.bang(rlHintsView)
                viewModel.decreaseHints(1)
//                tvHintsCount.text = viewModel.getHintsCount().toString()
//            val hintedWordView = rvWords.findViewHolderForLayoutPosition(wordPos).itemView.tvLetters
//            smallBang?.bang(hintedWordView)
            }
        } else {
            val dialog = StoreDialog.newInstance()
            dialog.show(activity.supportFragmentManager, "fragment_store")
        }
    }

    private fun subscribeUi(viewModel: GameViewModel) {
        viewModel.getLiveLevel().observe(this, Observer {
            it?.let { wordsAdapter.setPackColor(it.color) }
        })
        viewModel.getLiveWords().observe(this, Observer {
            it?.let {
                it.toList().let { words ->
                    if (words.none { it.position != 0 }) {
                        viewModel.setupWordsPositions()
                    }
                    wordsAdapter.updateItems(words)
                }
            }
        })
        viewModel.getLiveChunks().observe(this, Observer {
            it?.let {
                it.toList().let { chunks ->
                    if (chunks.none { it.position != 0 }) {
                        viewModel.onShuffleClick()
                    }
                    chunksAdapter.updateItems(chunks)
                }
                updateChunksTextView(viewModel.getSelectedChunksString())
                updateChunksCountView(viewModel.getSelectedChunksLength())
                updateClearIcon(viewModel.getClearIconVisibility())
                val wordPos = viewModel.isWordSolved()
                if (wordPos != -1) {
                    wordsAdapter.notifyItemChanged(wordPos)
                    viewModel.onWordSolved().forEach {
                        chunksAdapter.notifyItemChanged(it)
                    }
                }
            }
        })
    }

    private fun setupWordsAdapter() {
        wordsAdapter = WordsAdapter()
        rvWords.adapter = wordsAdapter
        rvWords.layoutManager = CustomGridLayoutManager(activity, WORDS_GRID_NUM)
        rvWords.setHasFixedSize(true)
//        rvWords.translationY = 0.5F
//        rvWords.alpha = 0f
//        rvWords.animate()
//                .translationY(0F)
//                .setDuration(1000)
//                .alpha(1f)
//                .setInterpolator(AccelerateDecelerateInterpolator())
//                .start()
    }

    private fun setupChunksAdapter() {
        chunksAdapter = ChunksAdapter {
            viewModel.onChunkClick(it)
            chunksAdapter.notifyItemChanged(it.position)
        }
        rvChunks.adapter = chunksAdapter
        rvChunks.layoutManager = CustomGridLayoutManager(activity, CHUNKS_GRID_NUM)
        rvChunks.setHasFixedSize(true)
        rvChunks.itemAnimator.changeDuration = 50L
    }

    private fun updateChunksTextView(text: String) {
        tvInputChunks.text = text
    }

    private fun updateChunksCountView(length: Int) {
        tvChunksCount.text = length.toString()
        when {
            length > 0 -> rlChunksCount.visible()
            else -> rlChunksCount.invisible()
        }
    }

    private fun updateClearIcon(isVisible: Boolean) = when {
        isVisible -> btnClear.visible()
        else -> btnClear.invisible()
    }
}