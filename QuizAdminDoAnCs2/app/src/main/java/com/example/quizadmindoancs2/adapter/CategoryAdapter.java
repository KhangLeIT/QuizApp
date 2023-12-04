package com.example.quizadmindoancs2.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizadmindoancs2.CategoryActivity;
import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.R;
import com.example.quizadmindoancs2.TestActivity;
import com.example.quizadmindoancs2.model.CategoryModel;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class CategoryAdapter extends BaseAdapter {

    private List<CategoryModel> cat_List;
    private ProgressDialog progressDailog;
    private AlertDialog.Builder editCatDialog;
    private int idDoc = 0;
    private boolean updateCateFT = false, editCateFT= false;


    public CategoryAdapter(List<CategoryModel> cat_List) {
        this.cat_List = cat_List;
    }


    @Override
    public int getCount() {
        return cat_List.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myView;
        if (view == null){
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout, viewGroup, false);

        }
        else {
            myView = view;
        }
//        myView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DbQuery.g_select_cat_index = i;
//                //Toast.makeText(myView.getContext(), "cat id is " + cat_List.get(i).getDocID(), Toast.LENGTH_SHORT).show();
//            }
//        });


        TextView catName = myView.findViewById(R.id.catName);
        TextView noOfTests = myView.findViewById(R.id.no_of_tests);
        ImageView deleteB = myView.findViewById(R.id.catDelB);



        progressDailog = new ProgressDialog(myView.getContext());
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDailog.setMessage(" Loading...");

        editCatDialog = new AlertDialog.Builder(myView.getContext());
        View customLayout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.edit_category_dialog, null);
        editCatDialog.setView(customLayout);
        editCatDialog.setTitle("Edit Category " + DbQuery.g_catList.get(idDoc).getName());

        EditText tv_editCatName =customLayout.findViewById(R.id.ed_cat_name);
        Button updateCatB = customLayout.findViewById(R.id.ed_update_btn);
        Button cancelB = customLayout.findViewById(R.id.ed_cancel);


        catName.setText(cat_List.get(i).getName());
        noOfTests.setText(String.valueOf( cat_List.get(i).getNoOfTest())+ "Tests");

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbQuery.g_select_test_index = i;
                DbQuery.g_select_cat_index = i;
                Intent intent = new Intent(view.getContext(), TestActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(editCateFT == true)
                {
                    Intent intent = new Intent(view.getContext(), TestActivity.class);
                    view.getContext().startActivity(intent);
                }
                else{
                    idDoc = i;
                    editCatDialog.show();
                    tv_editCatName.setText(cat_List.get(idDoc).getName());
                }
                editCateFT = true;
                //Toast.makeText(myView.getContext(), cat_List.get(idDoc).getDocID(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        updateCatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateCateFT == true)
                {
                    Intent intent = new Intent(view.getContext(), TestActivity.class);
                    view.getContext().startActivity(intent);
                }
                else{
                    if (tv_editCatName.getText().toString().isEmpty())
                    {
                        tv_editCatName.setError("Enter Category Name");
                        return;
                    }
                    else
                    {
                        updateCategory(tv_editCatName.getText().toString(), idDoc , myView.getContext());
                        Intent intent = new Intent(myView.getContext(), CategoryActivity.class);
                        myView.getContext().startActivity(intent);
                        //Toast.makeText(myView.getContext(), cat_List.get(idDoc).getDocID(), Toast.LENGTH_SHORT).show();
                    }
                }
                updateCateFT = true;

            }


        });
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myView.getContext(), CategoryActivity.class);
                myView.getContext().startActivity(intent);
            }
        });


        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(myView.getContext())
                        .setTitle("Delete Category")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                deleteCategory(i,myView.getContext());
                                Intent intent = new Intent(myView.getContext(), CategoryActivity.class);
                                myView.getContext().startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
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
            }
        });

        return myView;
    }
    private void deleteCategory(final int id, Context context)
    {
        progressDailog.show();
        DbQuery.deleteCategory(id, context, cat_List);


    }
    private void updateCategory(String catNewName, int pos, Context context)
    {
        progressDailog.show();
        Map<String, Object> catData = new ArrayMap<>();
        catData.put("NAME", catNewName);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
       // Toast.makeText(context, cat_List.get(pos).getDocID(), Toast.LENGTH_SHORT).show();

        firestore.collection("QUIZ").document(cat_List.get(pos).getDocID())
                .update(catData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Category Name changed Successfully", Toast.LENGTH_SHORT).show();
                        DbQuery.g_catList.get(pos).setName(catNewName);
                        cat_List.get(pos).setName(catNewName);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
