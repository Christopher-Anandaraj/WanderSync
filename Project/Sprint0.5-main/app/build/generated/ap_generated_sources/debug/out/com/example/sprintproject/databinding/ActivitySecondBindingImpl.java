package com.example.sprintproject.databinding;
import com.example.sprintproject.R;
import com.example.sprintproject.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySecondBindingImpl extends ActivitySecondBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.videoView, 1);
        sViewsWithIds.put(R.id.loginText, 2);
        sViewsWithIds.put(R.id.box, 3);
        sViewsWithIds.put(R.id.username, 4);
        sViewsWithIds.put(R.id.editTextUsername, 5);
        sViewsWithIds.put(R.id.password, 6);
        sViewsWithIds.put(R.id.editTextPassword, 7);
        sViewsWithIds.put(R.id.loginButton, 8);
        sViewsWithIds.put(R.id.createButton, 9);
        sViewsWithIds.put(R.id.button_quit_login, 10);
        sViewsWithIds.put(R.id.imageView, 11);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySecondBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivitySecondBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.Button) bindings[10]
            , (android.widget.Button) bindings[9]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[5]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.Button) bindings[8]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[4]
            , (android.widget.VideoView) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}