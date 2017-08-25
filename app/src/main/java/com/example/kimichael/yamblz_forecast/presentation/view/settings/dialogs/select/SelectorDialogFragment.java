package com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Sinjvf on 05.08.2017.
 * <p>
 * super class for choosing dialogs
 */

public abstract class SelectorDialogFragment extends DialogFragment implements SelectableDialogView{
    @BindView(R.id.radio_group)
    RadioGroup group;
    protected Unbinder unbinder;
    private static final String POSITION = "position";

    protected int ids[];

    protected abstract void initIds();
    public static Bundle getBundle(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        return args;
    }

    public abstract void saveByPos(int position);

    @OnClick(R.id.ok_button)
    public void okClick() {
        int id = group.getCheckedRadioButtonId();
        int pos = 0;
        while (ids[pos] != id) pos++;
        if(pos<ids.length)  saveByPos(pos);
        dismiss();
    }

    @Override
    public void checkPosition(int position) {
        ((RadioButton) (group.findViewById(ids[position]))).setChecked(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        App.getInstance().releaseSettingsScreenComponent();
    }
}
