// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityQuestionsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton clearSelB;

  @NonNull
  public final LinearLayout ll3;

  @NonNull
  public final LinearLayout ll4;

  @NonNull
  public final AppCompatButton markB;

  @NonNull
  public final ImageView markImage;

  @NonNull
  public final ImageButton nextQuesB;

  @NonNull
  public final ImageButton prevQuesB;

  @NonNull
  public final ImageView qaBookmarkB;

  @NonNull
  public final TextView qaCatName;

  @NonNull
  public final ImageView quesListGridB;

  @NonNull
  public final RecyclerView questionsView;

  @NonNull
  public final AppCompatButton submitB;

  @NonNull
  public final LinearLayout topBar;

  @NonNull
  public final TextView tvQuesID;

  @NonNull
  public final TextView tvTimer;

  private ActivityQuestionsBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton clearSelB, @NonNull LinearLayout ll3, @NonNull LinearLayout ll4,
      @NonNull AppCompatButton markB, @NonNull ImageView markImage, @NonNull ImageButton nextQuesB,
      @NonNull ImageButton prevQuesB, @NonNull ImageView qaBookmarkB, @NonNull TextView qaCatName,
      @NonNull ImageView quesListGridB, @NonNull RecyclerView questionsView,
      @NonNull AppCompatButton submitB, @NonNull LinearLayout topBar, @NonNull TextView tvQuesID,
      @NonNull TextView tvTimer) {
    this.rootView = rootView;
    this.clearSelB = clearSelB;
    this.ll3 = ll3;
    this.ll4 = ll4;
    this.markB = markB;
    this.markImage = markImage;
    this.nextQuesB = nextQuesB;
    this.prevQuesB = prevQuesB;
    this.qaBookmarkB = qaBookmarkB;
    this.qaCatName = qaCatName;
    this.quesListGridB = quesListGridB;
    this.questionsView = questionsView;
    this.submitB = submitB;
    this.topBar = topBar;
    this.tvQuesID = tvQuesID;
    this.tvTimer = tvTimer;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityQuestionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityQuestionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_questions, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityQuestionsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.clear_selB;
      AppCompatButton clearSelB = ViewBindings.findChildViewById(rootView, id);
      if (clearSelB == null) {
        break missingId;
      }

      id = R.id.ll3;
      LinearLayout ll3 = ViewBindings.findChildViewById(rootView, id);
      if (ll3 == null) {
        break missingId;
      }

      id = R.id.ll4;
      LinearLayout ll4 = ViewBindings.findChildViewById(rootView, id);
      if (ll4 == null) {
        break missingId;
      }

      id = R.id.markB;
      AppCompatButton markB = ViewBindings.findChildViewById(rootView, id);
      if (markB == null) {
        break missingId;
      }

      id = R.id.mark_image;
      ImageView markImage = ViewBindings.findChildViewById(rootView, id);
      if (markImage == null) {
        break missingId;
      }

      id = R.id.next_quesB;
      ImageButton nextQuesB = ViewBindings.findChildViewById(rootView, id);
      if (nextQuesB == null) {
        break missingId;
      }

      id = R.id.prev_quesB;
      ImageButton prevQuesB = ViewBindings.findChildViewById(rootView, id);
      if (prevQuesB == null) {
        break missingId;
      }

      id = R.id.qa_bookmarkB;
      ImageView qaBookmarkB = ViewBindings.findChildViewById(rootView, id);
      if (qaBookmarkB == null) {
        break missingId;
      }

      id = R.id.qa_catName;
      TextView qaCatName = ViewBindings.findChildViewById(rootView, id);
      if (qaCatName == null) {
        break missingId;
      }

      id = R.id.ques_list_gridB;
      ImageView quesListGridB = ViewBindings.findChildViewById(rootView, id);
      if (quesListGridB == null) {
        break missingId;
      }

      id = R.id.questions_view;
      RecyclerView questionsView = ViewBindings.findChildViewById(rootView, id);
      if (questionsView == null) {
        break missingId;
      }

      id = R.id.submitB;
      AppCompatButton submitB = ViewBindings.findChildViewById(rootView, id);
      if (submitB == null) {
        break missingId;
      }

      id = R.id.top_bar;
      LinearLayout topBar = ViewBindings.findChildViewById(rootView, id);
      if (topBar == null) {
        break missingId;
      }

      id = R.id.tv_quesID;
      TextView tvQuesID = ViewBindings.findChildViewById(rootView, id);
      if (tvQuesID == null) {
        break missingId;
      }

      id = R.id.tv_timer;
      TextView tvTimer = ViewBindings.findChildViewById(rootView, id);
      if (tvTimer == null) {
        break missingId;
      }

      return new ActivityQuestionsBinding((ConstraintLayout) rootView, clearSelB, ll3, ll4, markB,
          markImage, nextQuesB, prevQuesB, qaBookmarkB, qaCatName, quesListGridB, questionsView,
          submitB, topBar, tvQuesID, tvTimer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
