package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.data.DraftContentSharedPrefs
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils.setCursorAtEndWithFocusAndShowKeyboard
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

    val prefs by lazy { DraftContentSharedPrefs(this.requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)


        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                if (!binding.edit.text.isNullOrBlank()) {
                    val text = binding.edit.text.toString()
//                    draft = text
                    prefs.saveDraft(text)
                    findNavController().navigateUp()
                } else {
                    isEnabled = false
//                    draft = null
//                    prefs.saveDraft("")
                    findNavController().navigateUp()
                }
            }

        })

        val draft = prefs.getDraft()
        println(draft)
        if (draft.isNotBlank()) binding.edit.setText(draft)

//        if (!draft.isNullOrBlank()) binding.edit.setText(draft)


        val text = arguments?.textArg
        text?.let {binding.edit.setText(it)}
        //либо так:
//        arguments?.textArg?.let(binding.edit::setText)

        binding.edit.setCursorAtEndWithFocusAndShowKeyboard()
        binding.floatingButtonOk.setOnClickListener {

            if (!binding.edit.text.isNullOrBlank()) {
                val content = binding.edit.text.toString()
//                draft = null
                prefs.saveDraft("")

                //todo сохрание черновика при сохранении отредактированного текста
                binding.edit.setText("")
                viewModel.changeContentAndSave(content)
            }
            findNavController().navigateUp()
        }

        return binding.root
    }

    //аналог static в Java
    companion object {
//        var draft: String? = null
        var Bundle.textArg: String? by StringArg
    }

}