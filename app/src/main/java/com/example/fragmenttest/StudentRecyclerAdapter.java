package com.example.fragmenttest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_students_app.model.Student;

import java.util.List;

class StudentViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv;
    TextView idTv;
    CheckBox cb;
    List<Student> studentList;

    public StudentViewHolder(@NonNull View itemView, StudentRecyclerAdapter.OnItemClickListener listener, List<Student> studentList) {
        super(itemView);
        this.studentList = studentList;
        nameTv = itemView.findViewById(R.id.studentlistrow_name_tv);
        idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
        cb = itemView.findViewById(R.id.studentlistrow_cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) cb.getTag();
                Student student = studentList.get(pos);
                student.cb = cb.isChecked();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                listener.onItemClick(position);
            }
        });
    }

    public void bind(Student student, int position) {
        nameTv.setText(student.name);
        idTv.setText(student.id);
        cb.setChecked(student.cb);
        cb.setTag(position);
    }
}

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }

    LayoutInflater inflater;
    List<Student> studentList;

    public StudentRecyclerAdapter(LayoutInflater inflater, List<Student> studentList) {
        this.inflater = inflater;
        this.studentList = studentList;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate((R.layout.student_list_row), parent,false);
        return new StudentViewHolder(view,listener, studentList);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student,position);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

}

