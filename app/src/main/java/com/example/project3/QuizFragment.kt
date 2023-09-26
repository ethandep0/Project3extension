package com.example.project3

import android.os.Bundle
import com.example.project3.R
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_DIFFICULTY = "difficulty"
private const val ARG_OPERATION = "operation"
private const val ARG_QUESTION_COUNT = "questionCount"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var questionTextView: TextView
    private lateinit var userAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        questionTextView = view.findViewById(R.id.textView4)
        userAnswer = view.findViewById(R.id.typeAnswerBox)

        // IMPORT VARIABLES FROM StartFragment (difficulty, operation, questionCount) USING SAFE ARGS
        val difficulty = QuizFragmentArgs.fromBundle(requireArguments()).difficulty
        val operation = QuizFragmentArgs.fromBundle(requireArguments()).operation
        val receivedCorrect = StartFragmentArgs.fromBundle(requireArguments()).correct
        val questionCount = QuizFragmentArgs.fromBundle(requireArguments()).questionCount

        var questions = arrayListOf<String>() // store questions here
        var answers = arrayListOf<Int>() // store answers here

        fun updateQuestion(question: String) {
            // Update the question TextView with the new question
            questionTextView.text = question
        }


        fun generateQuestions(difficulty: String?, operation: String?, questionCount: Int) {

            val random = Random()

            // Define operand limits based on difficulty
            val maxOperand: Int = when (difficulty) {
                "easy" -> 10
                "medium" -> 25
                "hard" -> 50
                else -> 10 // Default to easy if difficulty is not recognized
            }

            for (i in 0 until questionCount) {
                val number1 = random.nextInt(maxOperand + 1)
                val number2 = random.nextInt(maxOperand + 1)
                var question: String = ""
                var answer: Int = 0

                when (operation) {
                    "addition" -> {
                        question = "$number1 + $number2"
                        answer = number1 + number2
                    }
                    "subtraction" -> {
                        question = "$number1 - $number2"
                        answer = number1 - number2
                    }
                    "multiplication" -> {
                        question = "$number1 x $number2"
                        answer = number1 * number2
                    }
                    "division" -> {
                        // Ensure division produces a whole number answer
                        val divisor = if (number2 != 0) number2 else 1
                        val dividend = number1 * divisor
                        question = "$dividend ÷ $divisor"
                        answer = dividend / divisor
                    }
                    else -> {
                        // Default to addition if operation is not recognized
                        // question = "$number1 + $number2"
                        answer = number1 + number2
                    }

                }


                questions.add(question)
                answers.add(answer)

            }

            // Now, 'questions' and 'answers' lists contain the generated questions and answers
            // You can access them using their respective indices.
        }

        generateQuestions(difficulty, operation, questionCount)
        // call generateQuestions()

        updateQuestion(questions[0])

        var currentQuestionNumber = 0 // start with 0 instead of 1 so you can use this for indexing the question/answer

        var wrong = 0 // keep track of questions user gets wrong/right
        var correct = 0

        // now you can do the quiz stuff here.


        //done button, navigates to the next screen if last question.
        val doneButton: Button = view.findViewById(R.id.doneButton)
        doneButton.setOnClickListener {
            val userResponse = userAnswer.text.toString()
//
            if (userResponse.isNotEmpty()) { //check if answer is correct
                val correctAnswer = answers[currentQuestionNumber].toString()
                if (userResponse == correctAnswer) {
                    correct += 1
                    val toastMessage = "Correct. Good work!"
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                } else {
                    val toastMessage = "Wrong"
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    wrong += 1
                }
                currentQuestionNumber += 1
//
                if (currentQuestionNumber == questionCount) { //goto next screen
                    val action = QuizFragmentDirections.actionQuizFragmentToStartFragment(correct, questionCount, difficulty, operation)
                    findNavController().navigate(action)
                    navController.navigate(action)

                } else {
                    // Update the question for the next round
                    userAnswer.text = ""
                    updateQuestion(questions[currentQuestionNumber])
            }
            }

        }

        return view
    }
}
