// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class QuestionListLayoutBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final View divider;

  @NonNull
  public final ImageButton drawerCloseB;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final GridView quesListGv;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  private QuestionListLayoutBinding(@NonNull DrawerLayout rootView, @NonNull View divider,
      @NonNull ImageButton drawerCloseB, @NonNull DrawerLayout drawerLayout,
      @NonNull ImageView imageView, @NonNull ImageView imageView3, @NonNull ImageView imageView4,
      @NonNull ImageView imageView5, @NonNull GridView quesListGv, @NonNull TextView textView,
      @NonNull TextView textView2, @NonNull TextView textView4, @NonNull TextView textView5) {
    this.rootView = rootView;
    this.divider = divider;
    this.drawerCloseB = drawerCloseB;
    this.drawerLayout = drawerLayout;
    this.imageView = imageView;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.imageView5 = imageView5;
    this.quesListGv = quesListGv;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView4 = textView4;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static QuestionListLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static QuestionListLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.question_list_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static QuestionListLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.divider;
      View divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.drawerCloseB;
      ImageButton drawerCloseB = ViewBindings.findChildViewById(rootView, id);
      if (drawerCloseB == null) {
        break missingId;
      }

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.imageView5;
      ImageView imageView5 = ViewBindings.findChildViewById(rootView, id);
      if (imageView5 == null) {
        break missingId;
      }

      id = R.id.ques_list_gv;
      GridView quesListGv = ViewBindings.findChildViewById(rootView, id);
      if (quesListGv == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      return new QuestionListLayoutBinding((DrawerLayout) rootView, divider, drawerCloseB,
          drawerLayout, imageView, imageView3, imageView4, imageView5, quesListGv, textView,
          textView2, textView4, textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}