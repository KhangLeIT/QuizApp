// Generated by view binder compiler. Do not edit!
package com.example.doancs2nhom7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.doancs2nhom7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ContentMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavBar;

  @NonNull
  public final FrameLayout mainFrame;

  private ContentMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNavBar, @NonNull FrameLayout mainFrame) {
    this.rootView = rootView;
    this.bottomNavBar = bottomNavBar;
    this.mainFrame = mainFrame;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_nav_bar;
      BottomNavigationView bottomNavBar = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavBar == null) {
        break missingId;
      }

      id = R.id.main_frame;
      FrameLayout mainFrame = ViewBindings.findChildViewById(rootView, id);
      if (mainFrame == null) {
        break missingId;
      }

      return new ContentMainBinding((ConstraintLayout) rootView, bottomNavBar, mainFrame);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
