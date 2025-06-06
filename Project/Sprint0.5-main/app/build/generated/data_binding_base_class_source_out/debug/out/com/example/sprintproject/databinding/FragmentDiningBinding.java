// Generated by view binder compiler. Do not edit!
package com.example.sprintproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.sprintproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDiningBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button addReservationButton;

  @NonNull
  public final TextView addReservationTitle;

  @NonNull
  public final RecyclerView diningList;

  @NonNull
  public final EditText diningLocationInput;

  @NonNull
  public final EditText diningNameInput;

  @NonNull
  public final TableLayout diningReservation;

  @NonNull
  public final EditText diningTimeInput;

  @NonNull
  public final EditText diningWebsiteInput;

  @NonNull
  public final ConstraintLayout fragmentDining;

  @NonNull
  public final ImageButton openReservationCardviewButton;

  @NonNull
  public final CardView reservationCardview;

  @NonNull
  public final TextView textDining;

  private FragmentDiningBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button addReservationButton, @NonNull TextView addReservationTitle,
      @NonNull RecyclerView diningList, @NonNull EditText diningLocationInput,
      @NonNull EditText diningNameInput, @NonNull TableLayout diningReservation,
      @NonNull EditText diningTimeInput, @NonNull EditText diningWebsiteInput,
      @NonNull ConstraintLayout fragmentDining, @NonNull ImageButton openReservationCardviewButton,
      @NonNull CardView reservationCardview, @NonNull TextView textDining) {
    this.rootView = rootView;
    this.addReservationButton = addReservationButton;
    this.addReservationTitle = addReservationTitle;
    this.diningList = diningList;
    this.diningLocationInput = diningLocationInput;
    this.diningNameInput = diningNameInput;
    this.diningReservation = diningReservation;
    this.diningTimeInput = diningTimeInput;
    this.diningWebsiteInput = diningWebsiteInput;
    this.fragmentDining = fragmentDining;
    this.openReservationCardviewButton = openReservationCardviewButton;
    this.reservationCardview = reservationCardview;
    this.textDining = textDining;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDiningBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDiningBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_dining, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDiningBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_reservation_button;
      Button addReservationButton = ViewBindings.findChildViewById(rootView, id);
      if (addReservationButton == null) {
        break missingId;
      }

      id = R.id.add_reservation_title;
      TextView addReservationTitle = ViewBindings.findChildViewById(rootView, id);
      if (addReservationTitle == null) {
        break missingId;
      }

      id = R.id.dining_list;
      RecyclerView diningList = ViewBindings.findChildViewById(rootView, id);
      if (diningList == null) {
        break missingId;
      }

      id = R.id.dining_location_input;
      EditText diningLocationInput = ViewBindings.findChildViewById(rootView, id);
      if (diningLocationInput == null) {
        break missingId;
      }

      id = R.id.dining_name_input;
      EditText diningNameInput = ViewBindings.findChildViewById(rootView, id);
      if (diningNameInput == null) {
        break missingId;
      }

      id = R.id.dining_reservation;
      TableLayout diningReservation = ViewBindings.findChildViewById(rootView, id);
      if (diningReservation == null) {
        break missingId;
      }

      id = R.id.dining_time_input;
      EditText diningTimeInput = ViewBindings.findChildViewById(rootView, id);
      if (diningTimeInput == null) {
        break missingId;
      }

      id = R.id.dining_website_input;
      EditText diningWebsiteInput = ViewBindings.findChildViewById(rootView, id);
      if (diningWebsiteInput == null) {
        break missingId;
      }

      ConstraintLayout fragmentDining = (ConstraintLayout) rootView;

      id = R.id.open_reservation_cardview_button;
      ImageButton openReservationCardviewButton = ViewBindings.findChildViewById(rootView, id);
      if (openReservationCardviewButton == null) {
        break missingId;
      }

      id = R.id.reservation_cardview;
      CardView reservationCardview = ViewBindings.findChildViewById(rootView, id);
      if (reservationCardview == null) {
        break missingId;
      }

      id = R.id.text_dining;
      TextView textDining = ViewBindings.findChildViewById(rootView, id);
      if (textDining == null) {
        break missingId;
      }

      return new FragmentDiningBinding((ConstraintLayout) rootView, addReservationButton,
          addReservationTitle, diningList, diningLocationInput, diningNameInput, diningReservation,
          diningTimeInput, diningWebsiteInput, fragmentDining, openReservationCardviewButton,
          reservationCardview, textDining);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
