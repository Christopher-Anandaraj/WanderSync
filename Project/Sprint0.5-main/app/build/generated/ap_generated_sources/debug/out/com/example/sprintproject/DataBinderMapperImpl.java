package com.example.sprintproject;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.sprintproject.databinding.ActivityCreateBindingImpl;
import com.example.sprintproject.databinding.ActivityMainBindingImpl;
import com.example.sprintproject.databinding.ActivityNavigationBindingImpl;
import com.example.sprintproject.databinding.ActivitySecondBindingImpl;
import com.example.sprintproject.databinding.TransitionLayoutBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCREATE = 1;

  private static final int LAYOUT_ACTIVITYMAIN = 2;

  private static final int LAYOUT_ACTIVITYNAVIGATION = 3;

  private static final int LAYOUT_ACTIVITYSECOND = 4;

  private static final int LAYOUT_TRANSITIONLAYOUT = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sprintproject.R.layout.activity_create, LAYOUT_ACTIVITYCREATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sprintproject.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sprintproject.R.layout.activity_navigation, LAYOUT_ACTIVITYNAVIGATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sprintproject.R.layout.activity_second, LAYOUT_ACTIVITYSECOND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sprintproject.R.layout.transition_layout, LAYOUT_TRANSITIONLAYOUT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCREATE: {
          if ("layout/activity_create_0".equals(tag)) {
            return new ActivityCreateBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_create is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYNAVIGATION: {
          if ("layout/activity_navigation_0".equals(tag)) {
            return new ActivityNavigationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_navigation is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSECOND: {
          if ("layout/activity_second_0".equals(tag)) {
            return new ActivitySecondBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_second is invalid. Received: " + tag);
        }
        case  LAYOUT_TRANSITIONLAYOUT: {
          if ("layout/transition_layout_0".equals(tag)) {
            return new TransitionLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for transition_layout is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/activity_create_0", com.example.sprintproject.R.layout.activity_create);
      sKeys.put("layout/activity_main_0", com.example.sprintproject.R.layout.activity_main);
      sKeys.put("layout/activity_navigation_0", com.example.sprintproject.R.layout.activity_navigation);
      sKeys.put("layout/activity_second_0", com.example.sprintproject.R.layout.activity_second);
      sKeys.put("layout/transition_layout_0", com.example.sprintproject.R.layout.transition_layout);
    }
  }
}
