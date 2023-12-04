package com.example.quizadmindoancs2.query;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.quizadmindoancs2.CategoryActivity;
import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.TestActivity;
import com.example.quizadmindoancs2.adapter.CategoryAdapter;
import com.example.quizadmindoancs2.model.CategoryModel;
import com.example.quizadmindoancs2.model.QuestionModel;
import com.example.quizadmindoancs2.model.TestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {

    public static FirebaseFirestore g_firestore;

    public static List<CategoryModel> g_catList = new ArrayList<>();
    public static int g_select_cat_index = 0;

    public static List<TestModel> g_testList = new ArrayList<>();
    public static  int g_select_test_index = 0;

    public static List<QuestionModel> g_quesList = new ArrayList<>();

    public static int g_select_ques_index = 0;

    public static void loadQuestionTest(MyCompleteListener completeListener)
    {
        g_quesList.clear();
        g_firestore.collection("QUESTION")
                .whereEqualTo("CATEGORY", g_catList.get(g_select_cat_index).getDocID())
                .whereEqualTo("TEST", g_testList.get(g_select_test_index).getTestID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            g_quesList.add(new QuestionModel(
                                    doc.getId(),
                                    doc.getString("QUESTION"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue()
                            ));
                        }
                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();

                    }
                });
    }

    public static void loadCategories(MyCompleteListener completeListener)
    {
        g_catList.clear();

        g_firestore.collection("QUIZ").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            docList.put(doc.getId(), doc);
                        }
                        QueryDocumentSnapshot catListDoc = docList.get("Categories");

                        long catCount = catListDoc.getLong("COUNT");

                        for (int i=1;i<=catCount;i++)
                        {
                            String catID = catListDoc.getString("CAT" + String.valueOf(i) + "_ID");
                            QueryDocumentSnapshot catDoc = docList.get(catID);

                            int noOfTest = catDoc.getLong("NO_OF_TESTS").intValue();
                            String catName = catDoc.getString("NAME");
                            g_catList.add(new CategoryModel(catID,catName,noOfTest));
                        }
                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();

                    }
                });
    }


    public static void loadData(MyCompleteListener completeListener)
    {
        loadCategories(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                completeListener.onSuccess();
            }

            @Override
            public void onFailure() {
                completeListener.onFailure();

            }
        });
    }

    public static void deleteCategory(int id, Context context, List<CategoryModel> cat_List)
    {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Map<String, Object> catDoc = new ArrayMap<>();
        int index = 1;
        for (int i= 0; i< cat_List.size(); i++)
        {
            if (i!=id)
            {
                catDoc.put("CAT" + String.valueOf(index) + "_ID", cat_List.get(i).getDocID());
                index++;
            }

        }
        catDoc.put("COUNT", index -1);
        firestore.collection("QUIZ").document("Categories")
                .set(catDoc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                        //CategoryActivity.cat_List.remove(id);
                        DbQuery.g_catList.remove(id);
                        cat_List.remove(id);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void deleteTest(final int pos, MyCompleteListener completeListener)
    {
        Map<String, Object> catDoc = new ArrayMap<>();
        int index = 1;
        for (int i= 0; i< DbQuery.g_testList.size(); i++)
        {
            if (i!=pos)
            {
                int timeTest = DbQuery.g_testList.get(i).getTime();
                catDoc.put("TEST" + String.valueOf(index) + "_ID", DbQuery.g_testList.get(i).getTestID());
                catDoc.put("TEST" + String.valueOf(index) + "_TIME",timeTest);
                index++;
            }

        }

        g_firestore.collection("QUIZ").document(DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID())
                .collection("TESTS_LIST").document("TEST_INFO")
                .set(catDoc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Map<String, Object> udNoOfTest = new ArrayMap<>();
                        int setNoOfTestList = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getNoOfTest(); ;
                        udNoOfTest.put("NO_OF_TESTS", setNoOfTestList - 1);
                        g_firestore.collection("QUIZ").document(DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID())
                                .update(udNoOfTest);

                        String curr_cat_id = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID();
                        g_firestore.collection("QUIZ").document(curr_cat_id)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        int setNoOfTestList =documentSnapshot.getLong("NO_OF_TESTS").intValue();
                                        DbQuery.g_catList.get(DbQuery.g_select_cat_index).setNoOfTest(setNoOfTestList);
                                        completeListener.onSuccess();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        completeListener.onFailure();
                                    }
                                });

                       // DbQuery.g_testList.remove(pos);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void updateTestList(int pos, String catNewName, MyCompleteListener completeListener)
    {
        Map<String, Object> catData = new ArrayMap<>();
        int posTest = pos + 1;
        int editTestTimeUD = Integer.parseInt(catNewName);
        catData.put("TEST" + String.valueOf(posTest) + "_TIME",editTestTimeUD);

        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // Toast.makeText(context, cat_List.get(pos).getDocID(), Toast.LENGTH_SHORT).show();

        g_firestore.collection("QUIZ").document(DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID())
                .collection("TESTS_LIST").document("TEST_INFO")
                .update(catData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                       completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    public static void addCategory(String title, MyCompleteListener completeListener)
    {
        WriteBatch batch = g_firestore.batch();
        Map<String, Object> catData = new ArrayMap<>();
        catData.put("NAME", title);
        catData.put("NO_OF_TESTS", 0);

        String doc_id = g_firestore.collection("QUIZ").document().getId();

        g_firestore.collection("QUIZ").document(doc_id)
                .set(catData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Map<String, Object> catDoc = new ArrayMap<>();
                        catDoc.put("NAME", title);
                        catDoc.put("NO_OF_TESTS", 0);
                        catDoc.put("CAT_ID", doc_id);
                        DocumentReference bmDoc = g_firestore.collection("QUIZ").document();
                        batch.set(bmDoc,catDoc);
                        g_firestore.collection("QUIZ").document(doc_id)
                                .update(catDoc);

                        Map<String, Object> cate_Doc = new ArrayMap<>();
                        cate_Doc.put("CAT" + String.valueOf(DbQuery.g_catList.size()+1) +"_ID", doc_id);
                        cate_Doc.put("COUNT", DbQuery.g_catList.size() +1);

                        g_firestore.collection("QUIZ").document("Categories")
                                .update(cate_Doc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        DbQuery.g_catList.add(new CategoryModel(doc_id, title, 0));
//                                        adapter = new CategoryAdapter(DbQuery.g_catList);
//                                        cat_view.setAdapter(adapter);
//                                        progressDailog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        completeListener.onFailure();
                                    }
                                });
                        completeListener.onSuccess();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    public static void addNewTest(String timeTest, MyCompleteListener completeListener)
    {
        String curr_cat_id = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID();
        int curr_no_of_test = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getNoOfTest();
        int timetest = Integer.parseInt(timeTest);

        Map<String, Object> test_Data = new ArrayMap<>();
        String testID = curr_cat_id.toUpperCase().substring(0,8);
        test_Data.put("TEST" + String.valueOf(curr_no_of_test+ 1) + "_ID",testID + "_IDTEST" + String.valueOf(curr_no_of_test+ 1));
        test_Data.put("TEST"+ String.valueOf(curr_no_of_test+ 1) + "_TIME", timetest);

        if(curr_no_of_test == 0)
        {
            g_firestore.collection("QUIZ").document(curr_cat_id)
                    .collection("TESTS_LIST").document("TEST_INFO")
                    .set(test_Data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Map<String, Object> setNoOfTest = new ArrayMap<>();
                            setNoOfTest.put("NO_OF_TESTS", curr_no_of_test + 1);
                            g_firestore.collection("QUIZ").document(curr_cat_id)
                                    .update(setNoOfTest)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            completeListener.onSuccess();
                                            g_catList.get(g_select_cat_index).setNoOfTest(curr_no_of_test + 1);
                                            //Toast.makeText(,  "OK !!", Toast.LENGTH_SHORT).show();
                                           // progressDailog.dismiss();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                           // Toast.makeText(TestActivity.this, String.valueOf(timetest) + "Error", Toast.LENGTH_SHORT).show();
                                           // progressDailog.dismiss();
                                            completeListener.onFailure();
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(TestActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                           // progressDailog.dismiss();
                            completeListener.onFailure();

                        }
                    });
        }
        else
        {
            g_firestore.collection("QUIZ").document(curr_cat_id)
                    .collection("TESTS_LIST").document("TEST_INFO")
                    .update(test_Data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Map<String, Object> setNoOfTest = new ArrayMap<>();
                            setNoOfTest.put("NO_OF_TESTS", curr_no_of_test + 1);
                            g_firestore.collection("QUIZ").document(curr_cat_id)
                                    .update(setNoOfTest);
                            g_catList.get(g_select_cat_index).setNoOfTest(curr_no_of_test + 1);
                           // Toast.makeText(TestActivity.this, String.valueOf(timetest) + "OK !!", Toast.LENGTH_SHORT).show();
                           // progressDailog.dismiss();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // Toast.makeText(TestActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                           // progressDailog.dismiss();

                        }
                    });

        }
    }

    public static void loadTestData(final MyCompleteListener completeListener)
    {
        g_testList.clear();
        if (g_testList.size() == 0) {
            completeListener.onSuccess();
        }

        g_firestore.collection("QUIZ").document(g_catList.get(g_select_cat_index).getDocID())
                .collection("TESTS_LIST").document("TEST_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String curr_cat_id = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID();
                        g_firestore.collection("QUIZ").document(curr_cat_id)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot2) {
                                     int noOfTest =documentSnapshot2.getLong("NO_OF_TESTS").intValue();
                                        //int noOfTests = g_catList.get(g_select_cat_index).getNoOfTest();
                                        for (int i = 1; i<=noOfTest;i++)
                                        {
                                            g_testList.add(new TestModel(
                                                    documentSnapshot.getString("TEST" +String.valueOf(i)+ "_ID"),
                                                    0,
                                                    documentSnapshot.getLong("TEST" +String.valueOf(i)+"_TIME").intValue()
                                            ));
                                        }
                                        completeListener.onSuccess();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        completeListener.onFailure();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();

                    }
                });
    }

}
