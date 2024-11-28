package com.example.talentme.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talentme.R

class QuestionAdapter(private val questionList: List<Question>) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.question_text)
        val buttonFirst: Button = itemView.findViewById(R.id.button_0)
        val buttonSecond: Button = itemView.findViewById(R.id.button_1)
        val buttonThird: Button = itemView.findViewById(R.id.button_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]
        holder.questionText.text = question.questionText
        holder.buttonFirst.text = question.buttonFirstText
        holder.buttonSecond.text = question.buttonSecondText
        holder.buttonThird.text = question.buttonThirdText

        // Set click listeners for buttons if needed
        holder.buttonFirst.setOnClickListener {
            // Handle button click for buttonFirst
        }

        holder.buttonSecond.setOnClickListener {
            // Handle button click for buttonSecond
        }

        holder.buttonThird.setOnClickListener {
            // Handle button click for buttonThird
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}
