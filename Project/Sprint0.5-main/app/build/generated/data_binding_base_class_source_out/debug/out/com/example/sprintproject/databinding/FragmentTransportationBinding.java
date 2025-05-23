// Generated by view binder compiler. Do not edit!
package com.example.sprintproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.sprintproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTransportationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout fragmentTransportation;

  @NonNull
  public final TextView textTransportation;

  private FragmentTransportationBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout fragmentTransportation, @NonNull TextView textTransportation) {
    this.rootView = rootView;
    this.fragmentTransportation = fragmentTransportation;
    this.textTransportation = textTransportation;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTransportationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTransportationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_transportation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTransportationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout fragmentTransportation = (ConstraintLayout) rootView;

      id = R.id.text_transportation;
      TextView textTransportation = ViewBindings.findChildViewById(rootView, id);
      if (textTransportation == null) {
        break missingId;
      }

      return new FragmentTransportationBinding((ConstraintLayout) rootView, fragmentTransportation,
          textTransportation);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
