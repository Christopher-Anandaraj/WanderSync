// Generated by view binder compiler. Do not edit!
package com.example.sprintproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.sprintproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.eazegraph.lib.charts.PieChart;

public final class FragmentLogisticsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonAddContributor;

  @NonNull
  public final ImageButton buttonAddContributors;

  @NonNull
  public final Button buttonAddNote;

  @NonNull
  public final Button buttonDataVis;

  @NonNull
  public final ImageButton buttonNotes;

  @NonNull
  public final CardView cardViewGraph;

  @NonNull
  public final ListView contributorsList;

  @NonNull
  public final EditText editTextContributorName;

  @NonNull
  public final EditText editTextNewNote;

  @NonNull
  public final ListView listViewNotes;

  @NonNull
  public final TextView notes;

  @NonNull
  public final PieChart piechart;

  @NonNull
  public final TableLayout tableLayoutAddContributor;

  @NonNull
  public final TableLayout tableLayoutNotes;

  @NonNull
  public final TextView textContributors;

  @NonNull
  public final TextView titleAddContributor;

  private FragmentLogisticsBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonAddContributor, @NonNull ImageButton buttonAddContributors,
      @NonNull Button buttonAddNote, @NonNull Button buttonDataVis,
      @NonNull ImageButton buttonNotes, @NonNull CardView cardViewGraph,
      @NonNull ListView contributorsList, @NonNull EditText editTextContributorName,
      @NonNull EditText editTextNewNote, @NonNull ListView listViewNotes, @NonNull TextView notes,
      @NonNull PieChart piechart, @NonNull TableLayout tableLayoutAddContributor,
      @NonNull TableLayout tableLayoutNotes, @NonNull TextView textContributors,
      @NonNull TextView titleAddContributor) {
    this.rootView = rootView;
    this.buttonAddContributor = buttonAddContributor;
    this.buttonAddContributors = buttonAddContributors;
    this.buttonAddNote = buttonAddNote;
    this.buttonDataVis = buttonDataVis;
    this.buttonNotes = buttonNotes;
    this.cardViewGraph = cardViewGraph;
    this.contributorsList = contributorsList;
    this.editTextContributorName = editTextContributorName;
    this.editTextNewNote = editTextNewNote;
    this.listViewNotes = listViewNotes;
    this.notes = notes;
    this.piechart = piechart;
    this.tableLayoutAddContributor = tableLayoutAddContributor;
    this.tableLayoutNotes = tableLayoutNotes;
    this.textContributors = textContributors;
    this.titleAddContributor = titleAddContributor;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLogisticsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLogisticsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_logistics, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLogisticsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_add_contributor;
      Button buttonAddContributor = ViewBindings.findChildViewById(rootView, id);
      if (buttonAddContributor == null) {
        break missingId;
      }

      id = R.id.button_add_contributors;
      ImageButton buttonAddContributors = ViewBindings.findChildViewById(rootView, id);
      if (buttonAddContributors == null) {
        break missingId;
      }

      id = R.id.buttonAddNote;
      Button buttonAddNote = ViewBindings.findChildViewById(rootView, id);
      if (buttonAddNote == null) {
        break missingId;
      }

      id = R.id.buttonDataVis;
      Button buttonDataVis = ViewBindings.findChildViewById(rootView, id);
      if (buttonDataVis == null) {
        break missingId;
      }

      id = R.id.button_notes;
      ImageButton buttonNotes = ViewBindings.findChildViewById(rootView, id);
      if (buttonNotes == null) {
        break missingId;
      }

      id = R.id.cardViewGraph;
      CardView cardViewGraph = ViewBindings.findChildViewById(rootView, id);
      if (cardViewGraph == null) {
        break missingId;
      }

      id = R.id.contributors_list;
      ListView contributorsList = ViewBindings.findChildViewById(rootView, id);
      if (contributorsList == null) {
        break missingId;
      }

      id = R.id.editText_contributor_name;
      EditText editTextContributorName = ViewBindings.findChildViewById(rootView, id);
      if (editTextContributorName == null) {
        break missingId;
      }

      id = R.id.editTextNewNote;
      EditText editTextNewNote = ViewBindings.findChildViewById(rootView, id);
      if (editTextNewNote == null) {
        break missingId;
      }

      id = R.id.listViewNotes;
      ListView listViewNotes = ViewBindings.findChildViewById(rootView, id);
      if (listViewNotes == null) {
        break missingId;
      }

      id = R.id.notes;
      TextView notes = ViewBindings.findChildViewById(rootView, id);
      if (notes == null) {
        break missingId;
      }

      id = R.id.piechart;
      PieChart piechart = ViewBindings.findChildViewById(rootView, id);
      if (piechart == null) {
        break missingId;
      }

      id = R.id.tableLayout_add_contributor;
      TableLayout tableLayoutAddContributor = ViewBindings.findChildViewById(rootView, id);
      if (tableLayoutAddContributor == null) {
        break missingId;
      }

      id = R.id.tableLayoutNotes;
      TableLayout tableLayoutNotes = ViewBindings.findChildViewById(rootView, id);
      if (tableLayoutNotes == null) {
        break missingId;
      }

      id = R.id.text_contributors;
      TextView textContributors = ViewBindings.findChildViewById(rootView, id);
      if (textContributors == null) {
        break missingId;
      }

      id = R.id.title_add_contributor;
      TextView titleAddContributor = ViewBindings.findChildViewById(rootView, id);
      if (titleAddContributor == null) {
        break missingId;
      }

      return new FragmentLogisticsBinding((ConstraintLayout) rootView, buttonAddContributor,
          buttonAddContributors, buttonAddNote, buttonDataVis, buttonNotes, cardViewGraph,
          contributorsList, editTextContributorName, editTextNewNote, listViewNotes, notes,
          piechart, tableLayoutAddContributor, tableLayoutNotes, textContributors,
          titleAddContributor);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
