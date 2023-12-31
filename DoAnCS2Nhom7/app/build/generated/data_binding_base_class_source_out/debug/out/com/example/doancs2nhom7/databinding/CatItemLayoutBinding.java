// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CatItemLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView catName;

  @NonNull
  public final TextView noOfTests;

  private CatItemLayoutBinding(@NonNull RelativeLayout rootView, @NonNull TextView catName,
      @NonNull TextView noOfTests) {
    this.rootView = rootView;
    this.catName = catName;
    this.noOfTests = noOfTests;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CatItemLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CatItemLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.cat_item_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CatItemLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.catName;
      TextView catName = ViewBindings.findChildViewById(rootView, id);
      if (catName == null) {
        break missingId;
      }

      id = R.id.no_of_tests;
      TextView noOfTests = ViewBindings.findChildViewById(rootView, id);
      if (noOfTests == null) {
        break missingId;
      }

      return new CatItemLayoutBinding((RelativeLayout) rootView, catName, noOfTests);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
