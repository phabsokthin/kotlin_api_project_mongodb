package com.example.fetchapimongoddb.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchapimongoddb.Holder.BaseViewHolder
import com.example.fetchapimongoddb.Model.CourseModel
import com.example.fetchapimongoddb.R
import com.example.fetchapimongoddb.Service.DeleteDataAdapter
import com.example.fetchapimongoddb.Service.UpdateDataAdapter

class CourseAdapter(
    private val context: Context,
    private val courseList: MutableList<CourseModel>
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object{
        private const val TYPE_COURSE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType) {
            TYPE_COURSE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false)
                CourseViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid Type")
        }

    }




    override fun getItemCount(): Int {
        return  courseList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

            val item= courseList[position]
            (holder as CourseViewHolder).bind(item)

    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_COURSE
    }


    inner class CourseViewHolder(itemView: View) : BaseViewHolder<CourseModel>(itemView) {

        private val textCourseName: TextView = itemView.findViewById(R.id.txtCourseName)
        private val textTitle: TextView = itemView.findViewById(R.id.txtTitle)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)

        override fun bind(item: CourseModel) {
            textCourseName.text = item.name
            textTitle.text = item.email
            btnDelete.text= "លុប"
            btnEdit.text = "កែប្រែ"

            //delete
            btnDelete.setOnClickListener {
                AlertDialog.Builder(itemView.context).apply {
                    setTitle("Confirm Delete")
                    setMessage("Are you sure you want to delete this item?")
                    setPositiveButton("Yes") { _, _ ->
                        val deleteAdapter = DeleteDataAdapter()
                        deleteAdapter.delete("delete", item._id, object : DeleteDataAdapter.DeleteDataListener {
                            override fun onDeleteSuccess() {
                                Toast.makeText(itemView.context, "Deleted successfully!", Toast.LENGTH_SHORT).show()
                                val position = bindingAdapterPosition
                                if (position != RecyclerView.NO_POSITION) {
                                    courseList.removeAt(position)
                                    notifyItemRemoved(position)
                                    notifyItemRangeChanged(position, courseList.size)
                                }
                            }

                            override fun onDeleteFailure(error: String) {
                                Toast.makeText(itemView.context, "Failed to delete: $error", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            //edit

            btnEdit.setOnClickListener {
                val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_edit_course, null)
                val editTextCourseName = dialogView.findViewById<EditText>(R.id.editTextCourseName)
                val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)

                editTextCourseName.setText(item.name)
                editTextEmail.setText(item.email)

                val alertDialog = AlertDialog.Builder(itemView.context).apply {
                    setTitle("Update Course")
                    setView(dialogView)
                    setPositiveButton("បាទ", null) // Set later to handle click manually
                    setNegativeButton("បោះបង់") { dialog, _ -> dialog.dismiss() }
                }.create()

                alertDialog.show()

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val updateDataAdapter = UpdateDataAdapter()

                    val updatedName = editTextCourseName.text.toString()
                    val updatedEmail = editTextEmail.text.toString()

                    if (updatedName.isBlank() || updatedEmail.isBlank()) {
                        Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val updatedCourse = CourseModel(item._id, updatedName, updatedEmail)

                    updateDataAdapter.updateCourse("update", item._id, updatedCourse, object : UpdateDataAdapter.UpdateDataListener {
                        override fun onUpdateSuccess(updatedCourse: CourseModel) {
                            item.name = updatedCourse.name
                            item.email = updatedCourse.email
                            notifyItemChanged(bindingAdapterPosition)
                            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show()

                            alertDialog.dismiss()
                        }

                        override fun onUpdateFailure(error: String) {
                            Toast.makeText(context, "Failed: $error", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

        }

    }


    //update data for search
    fun updateData(newList: List<CourseModel>) {
        courseList.clear()  // Clear old data
        courseList.addAll(newList)  // Add new data
        notifyDataSetChanged() // Refresh RecyclerView
    }
}