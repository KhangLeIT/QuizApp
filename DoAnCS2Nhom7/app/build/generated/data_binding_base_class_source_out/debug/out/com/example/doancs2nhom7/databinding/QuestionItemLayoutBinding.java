// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class QuestionItemLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton optionA;

  @NonNull
  public final AppCompatButton optionB;

  @NonNull
  public final AppCompatButton optionC;

  @NonNull
  public final AppCompatButton optionD;

  @NonNull
  public final TextView tvQuestion;

  private QuestionItemLayoutBinding(@NonNull LinearLayout rootView,
      @NonNull AppCompatButton optionA, @NonNull AppCompatButton optionB,
      @NonNull AppCompatButton optionC, @NonNull AppCompatButton optionD,
      @NonNull TextView tvQuestion) {
    this.rootView = rootView;
    this.optionA = optionA;
    this.optionB = optionB;
    this.optionC = optionC;
    this.optionD = optionD;
    this.tvQuestion = tvQuestion;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static QuestionItemLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static QuestionItemLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.question_item_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static QuestionItemLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.optionA;
      AppCompatButton optionA = ViewBindings.findChildViewById(rootView, id);
      if (optionA == null) {
        break missingId;
      }

      id = R.id.optionB;
      AppCompatButton optionB = ViewBindings.findChildViewById(rootView, id);
      if (optionB == null) {
        break missingId;
      }

      id = R.id.optionC;
      AppCompatButton optionC = ViewBindings.findChildViewById(rootView, id);
      if (optionC == null) {
        break missingId;
      }

      id = R.id.optionD;
      AppCompatButton optionD = ViewBindings.findChildViewById(rootView, id);
      if (optionD == null) {
        break missingId;
      }

      id = R.id.tv_question;
      TextView tvQuestion = ViewBindings.findChildViewById(rootView, id);
      if (tvQuestion == null) {
        break missingId;
      }

      return new QuestionItemLayoutBinding((LinearLayout) rootView, optionA, optionB, optionC,
          optionD, tvQuestion);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
