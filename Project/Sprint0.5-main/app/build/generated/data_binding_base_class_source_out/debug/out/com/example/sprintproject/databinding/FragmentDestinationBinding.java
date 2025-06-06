// Generated by view binder compiler. Do not edit!
package com.example.sprintproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
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

public final class FragmentDestinationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonCalculateVacation;

  @NonNull
  public final Button buttonLogTravel;

  @NonNull
  public final Button buttonLogTravelCancel;

  @NonNull
  public final Button buttonLogTravelSubmit;

  @NonNull
  public final Button buttonVacationTimeCancel;

  @NonNull
  public final Button buttonVacationTimeSubmit;

  @NonNull
  public final EditText endDate;

  @NonNull
  public final ConstraintLayout fragmentDestination;

  @NonNull
  public final ListView listViewTravelLogs;

  @NonNull
  public final EditText startDate;

  @NonNull
  public final TextView textDestination;

  @NonNull
  public final TextView textDurationTimeForm;

  @NonNull
  public final TextView textEndDate;

  @NonNull
  public final TextView textEndDateTimeForm;

  @NonNull
  public final TextView textStartDate;

  @NonNull
  public final TextView textStartDateTimeForm;

  @NonNull
  public final TextView textTravelLocation;

  @NonNull
  public final TextView textVacationTimeResultTitle;

  @NonNull
  public final EditText travelLocation;

  @NonNull
  public final TableLayout vacationForm;

  @NonNull
  public final EditText vacationTimeDurationDataInfo;

  @NonNull
  public final EditText vacationTimeEndDataInfo;

  @NonNull
  public final TableLayout vacationTimeForm;

  @NonNull
  public final TableLayout vacationTimeFormResults;

  @NonNull
  public final TextView vacationTimeResult;

  @NonNull
  public final EditText vacationTimeStartDataInfo;

  @NonNull
  public final TableLayout viewTravelLogs;

  private FragmentDestinationBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonCalculateVacation, @NonNull Button buttonLogTravel,
      @NonNull Button buttonLogTravelCancel, @NonNull Button buttonLogTravelSubmit,
      @NonNull Button buttonVacationTimeCancel, @NonNull Button buttonVacationTimeSubmit,
      @NonNull EditText endDate, @NonNull ConstraintLayout fragmentDestination,
      @NonNull ListView listViewTravelLogs, @NonNull EditText startDate,
      @NonNull TextView textDestination, @NonNull TextView textDurationTimeForm,
      @NonNull TextView textEndDate, @NonNull TextView textEndDateTimeForm,
      @NonNull TextView textStartDate, @NonNull TextView textStartDateTimeForm,
      @NonNull TextView textTravelLocation, @NonNull TextView textVacationTimeResultTitle,
      @NonNull EditText travelLocation, @NonNull TableLayout vacationForm,
      @NonNull EditText vacationTimeDurationDataInfo, @NonNull EditText vacationTimeEndDataInfo,
      @NonNull TableLayout vacationTimeForm, @NonNull TableLayout vacationTimeFormResults,
      @NonNull TextView vacationTimeResult, @NonNull EditText vacationTimeStartDataInfo,
      @NonNull TableLayout viewTravelLogs) {
    this.rootView = rootView;
    this.buttonCalculateVacation = buttonCalculateVacation;
    this.buttonLogTravel = buttonLogTravel;
    this.buttonLogTravelCancel = buttonLogTravelCancel;
    this.buttonLogTravelSubmit = buttonLogTravelSubmit;
    this.buttonVacationTimeCancel = buttonVacationTimeCancel;
    this.buttonVacationTimeSubmit = buttonVacationTimeSubmit;
    this.endDate = endDate;
    this.fragmentDestination = fragmentDestination;
    this.listViewTravelLogs = listViewTravelLogs;
    this.startDate = startDate;
    this.textDestination = textDestination;
    this.textDurationTimeForm = textDurationTimeForm;
    this.textEndDate = textEndDate;
    this.textEndDateTimeForm = textEndDateTimeForm;
    this.textStartDate = textStartDate;
    this.textStartDateTimeForm = textStartDateTimeForm;
    this.textTravelLocation = textTravelLocation;
    this.textVacationTimeResultTitle = textVacationTimeResultTitle;
    this.travelLocation = travelLocation;
    this.vacationForm = vacationForm;
    this.vacationTimeDurationDataInfo = vacationTimeDurationDataInfo;
    this.vacationTimeEndDataInfo = vacationTimeEndDataInfo;
    this.vacationTimeForm = vacationTimeForm;
    this.vacationTimeFormResults = vacationTimeFormResults;
    this.vacationTimeResult = vacationTimeResult;
    this.vacationTimeStartDataInfo = vacationTimeStartDataInfo;
    this.viewTravelLogs = viewTravelLogs;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDestinationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDestinationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_destination, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDestinationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_calculate_vacation;
      Button buttonCalculateVacation = ViewBindings.findChildViewById(rootView, id);
      if (buttonCalculateVacation == null) {
        break missingId;
      }

      id = R.id.button_log_travel;
      Button buttonLogTravel = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogTravel == null) {
        break missingId;
      }

      id = R.id.button_log_travel_cancel;
      Button buttonLogTravelCancel = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogTravelCancel == null) {
        break missingId;
      }

      id = R.id.button_log_travel_submit;
      Button buttonLogTravelSubmit = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogTravelSubmit == null) {
        break missingId;
      }

      id = R.id.button_vacation_time_cancel;
      Button buttonVacationTimeCancel = ViewBindings.findChildViewById(rootView, id);
      if (buttonVacationTimeCancel == null) {
        break missingId;
      }

      id = R.id.button_vacation_time_submit;
      Button buttonVacationTimeSubmit = ViewBindings.findChildViewById(rootView, id);
      if (buttonVacationTimeSubmit == null) {
        break missingId;
      }

      id = R.id.end_date;
      EditText endDate = ViewBindings.findChildViewById(rootView, id);
      if (endDate == null) {
        break missingId;
      }

      ConstraintLayout fragmentDestination = (ConstraintLayout) rootView;

      id = R.id.listView_travel_logs;
      ListView listViewTravelLogs = ViewBindings.findChildViewById(rootView, id);
      if (listViewTravelLogs == null) {
        break missingId;
      }

      id = R.id.start_date;
      EditText startDate = ViewBindings.findChildViewById(rootView, id);
      if (startDate == null) {
        break missingId;
      }

      id = R.id.text_destination;
      TextView textDestination = ViewBindings.findChildViewById(rootView, id);
      if (textDestination == null) {
        break missingId;
      }

      id = R.id.text_duration_time_form;
      TextView textDurationTimeForm = ViewBindings.findChildViewById(rootView, id);
      if (textDurationTimeForm == null) {
        break missingId;
      }

      id = R.id.text_end_date;
      TextView textEndDate = ViewBindings.findChildViewById(rootView, id);
      if (textEndDate == null) {
        break missingId;
      }

      id = R.id.text_end_date_time_form;
      TextView textEndDateTimeForm = ViewBindings.findChildViewById(rootView, id);
      if (textEndDateTimeForm == null) {
        break missingId;
      }

      id = R.id.text_start_date;
      TextView textStartDate = ViewBindings.findChildViewById(rootView, id);
      if (textStartDate == null) {
        break missingId;
      }

      id = R.id.text_start_date_time_form;
      TextView textStartDateTimeForm = ViewBindings.findChildViewById(rootView, id);
      if (textStartDateTimeForm == null) {
        break missingId;
      }

      id = R.id.text_travel_location;
      TextView textTravelLocation = ViewBindings.findChildViewById(rootView, id);
      if (textTravelLocation == null) {
        break missingId;
      }

      id = R.id.text_vacation_time_result_title;
      TextView textVacationTimeResultTitle = ViewBindings.findChildViewById(rootView, id);
      if (textVacationTimeResultTitle == null) {
        break missingId;
      }

      id = R.id.travel_location;
      EditText travelLocation = ViewBindings.findChildViewById(rootView, id);
      if (travelLocation == null) {
        break missingId;
      }

      id = R.id.vacation_form;
      TableLayout vacationForm = ViewBindings.findChildViewById(rootView, id);
      if (vacationForm == null) {
        break missingId;
      }

      id = R.id.vacation_time_duration_data_info;
      EditText vacationTimeDurationDataInfo = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeDurationDataInfo == null) {
        break missingId;
      }

      id = R.id.vacation_time_end_data_info;
      EditText vacationTimeEndDataInfo = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeEndDataInfo == null) {
        break missingId;
      }

      id = R.id.vacation_time_form;
      TableLayout vacationTimeForm = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeForm == null) {
        break missingId;
      }

      id = R.id.vacation_time_form_results;
      TableLayout vacationTimeFormResults = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeFormResults == null) {
        break missingId;
      }

      id = R.id.vacation_time_result;
      TextView vacationTimeResult = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeResult == null) {
        break missingId;
      }

      id = R.id.vacation_time_start_data_info;
      EditText vacationTimeStartDataInfo = ViewBindings.findChildViewById(rootView, id);
      if (vacationTimeStartDataInfo == null) {
        break missingId;
      }

      id = R.id.view_travel_logs;
      TableLayout viewTravelLogs = ViewBindings.findChildViewById(rootView, id);
      if (viewTravelLogs == null) {
        break missingId;
      }

      return new FragmentDestinationBinding((ConstraintLayout) rootView, buttonCalculateVacation,
          buttonLogTravel, buttonLogTravelCancel, buttonLogTravelSubmit, buttonVacationTimeCancel,
          buttonVacationTimeSubmit, endDate, fragmentDestination, listViewTravelLogs, startDate,
          textDestination, textDurationTimeForm, textEndDate, textEndDateTimeForm, textStartDate,
          textStartDateTimeForm, textTravelLocation, textVacationTimeResultTitle, travelLocation,
          vacationForm, vacationTimeDurationDataInfo, vacationTimeEndDataInfo, vacationTimeForm,
          vacationTimeFormResults, vacationTimeResult, vacationTimeStartDataInfo, viewTravelLogs);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
