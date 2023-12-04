package com.example.quizadmindoancs2.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.QuestionActivity;
import com.example.quizadmindoancs2.R;
import com.example.quizadmindoancs2.TestActivity;
import com.example.quizadmindoancs2.model.TestModel;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestModel> testList;
    private ProgressDialog progressDailog;
    private RecyclerView testView;
    private AlertDialog.Builder editCatDialog;
    private TestAdapter adapter;

    public TestAdapter(List<TestModel> testList) {
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView testNo;
        private TextView timeTest;
        private ImageView textDelB;
        private EditText editTimeTest;
        private Button ed_updateB, ed_cancelB;
        private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        private int idTest =0;
        private boolean testDeleteFT = false;
        private boolean editTestFT = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testNo = itemView.findViewById(R.id.textNo);
            timeTest = itemView.findViewById(R.id.timeTest);
            textDelB = itemView.findViewById(R.id.testDeleteB);
            editCatDialog = new AlertDialog.Builder(itemView.getContext());
            View customLayout = LayoutInflater.from(itemView.getContext()).inflate(R.layout.edit_test_dialog, null,false);
            editCatDialog.setView(customLayout);
            editTimeTest = customLayout.findViewById(R.id.ed_test_time);
            ed_updateB = customLayout.findViewById(R.id.ed_up_test_btn);
            ed_cancelB = customLayout.findViewById(R.id.ed_test_cancel);

            View RecLayout = LayoutInflater.from(itemView.getContext()).inflate(R.layout.activity_test, null,false);
            testView = RecLayout.findViewById(R.id.test_recycler_view);


        }
        private void setData(final int pos){

            testNo.setText("Test No " + String.valueOf(pos +1));
            timeTest.setText("time is " + String.valueOf(DbQuery.g_testList.get(pos).getTime()) + " min");

            textDelB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog;

                        dialog = new AlertDialog.Builder(itemView.getContext())
                                .setTitle("Delete Test " + String.valueOf(pos +1) + DbQuery.g_catList.get(DbQuery.g_select_cat_index).getName())
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        deleteTest(pos);
                                        testDeleteFT = false;

                                        Intent intent = new Intent(itemView.getContext(), TestActivity.class);
                                        itemView.getContext().startActivity(intent);
                                        ((Activity) itemView.getContext()).finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                        dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE);
                        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        dialog.getButton(dialog.BUTTON_NEGATIVE).setBackgroundColor(Color.GREEN);
                        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 250, 0);
                        dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);

                        testDeleteFT = true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.g_select_test_index = pos;
                    Intent it = new Intent(itemView.getContext(), QuestionActivity.class);
                    itemView.getContext().startActivity(it);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(editTestFT == true){
                        Intent intent = new Intent(itemView.getContext(), TestActivity.class);
                        itemView.getContext().startActivity(intent);
                        ((Activity)itemView.getContext()).finish();
                    }
                    else {
                        idTest = pos;
                        //Toast.makeText(itemView.getContext(), String.valueOf(DbQuery.g_testList.get(idTest).getTime()), Toast.LENGTH_SHORT).show();
                        editCatDialog.setTitle("Edit Test " + String.valueOf(idTest + 1));
                        editTestFT = true;
                        editCatDialog.show();
                        final int time = DbQuery.g_testList.get(idTest).getTime();
                        editTimeTest.setText(String.valueOf(String.valueOf(time)));

                    }

                    return false;
                }
            });
            ed_updateB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editTimeTest.getText().toString().isEmpty())
                    {
                        editTimeTest.setError("Enter Time");
                        return;
                    }
                    else
                    {
                        updateTest(editTimeTest.getText().toString(), idTest , itemView.getContext());

                        int timeTest = Integer.parseInt(editTimeTest.getText().toString());
                        DbQuery.g_testList.get(idTest).setTime(timeTest);

                        Intent intent = new Intent(itemView.getContext(), TestActivity.class);
                        itemView.getContext().startActivity(intent);
                        //Toast.makeText(myView.getContext(), cat_List.get(idDoc).getDocID(), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(itemView.getContext(), TestActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
            ed_cancelB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(itemView.getContext(), TestActivity.class);
                    itemView.getContext().startActivity(it);
                    ((Activity)itemView.getContext()).finish();
                }
            });
        }

        private void deleteTest(final int pos){
           DbQuery.deleteTest(pos, new MyCompleteListener() {
               @Override
               public void onSuccess() {
                   Toast.makeText(itemView.getContext(), "Delete Test Successfully", Toast.LENGTH_SHORT).show();
               }
               @Override
               public void onFailure() {
                   Toast.makeText(itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();

               }
           });
        }

        private void updateTest(String catNewName, int pos, Context context)
        {
            //progressDailog.show();
            DbQuery.updateTestList(pos, catNewName, new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context, "Update Test Successfully", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure() {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }


}
