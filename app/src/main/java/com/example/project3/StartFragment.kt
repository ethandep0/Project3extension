package com.example.project3

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var questionCount = 0
        var difficulty = ""
        var operation = ""
        val score = StartFragmentArgs.fromBundle(requireArguments()).score
        val totalQuestions = StartFragmentArgs.fromBundle(requireArguments()).totalQuestions



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        if (score != null && totalQuestions != null && score >= 0 && totalQuestions > 0) {
            val percentage = (score.toFloat() / totalQuestions) * 100
            val resultTextView: TextView = view.findViewById(R.id.your_score) // You need to create this TextView in your fragment's layout file.
            if (percentage >= 80) {
                resultTextView.text = "Congratulations! You scored $score out of $totalQuestions."
                resultTextView.setTextColor(Color.GREEN)
            } else {
                resultTextView.text = "You scored $score out of $totalQuestions. Better luck next time!"
                resultTextView.setTextColor(Color.RED)
            }
        }


        //question number display
        val questionsCountText: TextView = view.findViewById(R.id.countTextView)

        //minus 1 question button
        val incrementMinusButton: Button = view.findViewById(R.id.minusButton)
        incrementMinusButton.setOnClickListener {
            if (questionCount > 0) { //ensure questions won't go negative.
                questionCount--
                questionsCountText.text = questionCount.toString()
            }
        }

        //plus 1 question button
        val incrementPlusButton: Button = view.findViewById(R.id.plusButton)
        incrementPlusButton.setOnClickListener {
            questionCount++
            questionsCountText.text = questionCount.toString()
        }

        // difficulty RadioGroup
        val difficultyRadioGroup: RadioGroup = view.findViewById(R.id.difficultyRadioGroup)
        difficultyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.easyRadioButton -> {
                    difficulty = "easy"
                }
                R.id.mediumRadioButton -> {
                    difficulty = "medium"
                }
                R.id.hardRadioButton -> {
                    difficulty = "hard"
                }
            }
        }

        // operation RadioGroup
        val operationRadioGroup: RadioGroup = view.findViewById(R.id.operationRadioGroup)
        operationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.additionRadioButton -> {
                    operation = "addition"
                }
                R.id.multiplicationRadioButton -> {
                    operation = "multiplication"
                }
                R.id.divisionRadioButton -> {
                    operation = "division"
                }
                R.id.subtractionRadioButton -> {
                    operation = "subtraction"
                }
            }
        }

        //start button, navigates to the next screen.
        val startButton: Button = view.findViewById(R.id.startButton)
        startButton.setOnClickListener {
            if (difficulty.isNotEmpty() && operation.isNotEmpty() && questionCount != 0) {
                //pass variables in safe args and goto next screen.
                val action = StartFragmentDirections.actionStartFragmentToQuizFragment(difficulty, questionCount, operation)
                val navController = findNavController()
                navController.navigate(action)
            }
        }

        return view
    }
}
