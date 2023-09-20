package com.example.project3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

// TODO: Rename fragment and its initialization parameters, e.g., ARG_PARAM1 and ARG_PARAM2
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        //GET VALUES FROM SAFE ARGS
        val score = ResultFragmentArgs.fromBundle(requireArguments()).correct
        val questionCount = ResultFragmentArgs.fromBundle(requireArguments()).questionCount

        //set score text
        val ScoreTextView: TextView = view.findViewById(R.id.scoreText)
        val scoreText = "Your Score: $score/$questionCount"
        ScoreTextView.text = scoreText

        //set restart button
        val RestartButton = view.findViewById<Button>(R.id.restartButton)
        RestartButton.setOnClickListener {//goto start screen
            val navController = findNavController()
            val action = ResultFragmentDirections.actionResultFragmentToStartFragment()
            navController.navigate(action)
        }

        return view
    }

}
