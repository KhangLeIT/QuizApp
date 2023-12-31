// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AnswersItemLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final View divider3;

  @NonNull
  public final TextView optionA;

  @NonNull
  public final TextView optionB;

  @NonNull
  public final TextView optionC;

  @NonNull
  public final TextView optionD;

  @NonNull
  public final TextView quesNo;

  @NonNull
  public final TextView question;

  @NonNull
  public final TextView result;

  private AnswersItemLayoutBinding(@NonNull LinearLayout rootView, @NonNull View divider3,
      @NonNull TextView optionA, @NonNull TextView optionB, @NonNull TextView optionC,
      @NonNull TextView optionD, @NonNull TextView quesNo, @NonNull TextView question,
      @NonNull TextView result) {
    this.rootView = rootView;
    this.divider3 = divider3;
    this.optionA = optionA;
    this.optionB = optionB;
    this.optionC = optionC;
    this.optionD = optionD;
    this.quesNo = quesNo;
    this.question = question;
    this.result = result;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AnswersItemLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AnswersItemLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.answers_item_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AnswersItemLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.divider3;
      View divider3 = ViewBindings.findChildViewById(rootView, id);
      if (divider3 == null) {
        break missingId;
      }

      id = R.id.optionA;
      TextView optionA = ViewBindings.findChildViewById(rootView, id);
      if (optionA == null) {
        break missingId;
      }

      id = R.id.optionB;
      TextView optionB = ViewBindings.findChildViewById(rootView, id);
      if (optionB == null) {
        break missingId;
      }

      id = R.id.optionC;
      TextView optionC = ViewBindings.findChildViewById(rootView, id);
      if (optionC == null) {
        break missingId;
      }

      id = R.id.optionD;
      TextView optionD = ViewBindings.findChildViewById(rootView, id);
      if (optionD == null) {
        break missingId;
      }

      id = R.id.quesNo;
      TextView quesNo = ViewBindings.findChildViewById(rootView, id);
      if (quesNo == null) {
        break missingId;
      }

      id = R.id.question;
      TextView question = ViewBindings.findChildViewById(rootView, id);
      if (question == null) {
        break missingId;
      }

      id = R.id.result;
      TextView result = ViewBindings.findChildViewById(rootView, id);
      if (result == null) {
        break missingId;
      }

      return new AnswersItemLayoutBinding((LinearLayout) rootView, divider3, optionA, optionB,
          optionC, optionD, quesNo, question, result);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
