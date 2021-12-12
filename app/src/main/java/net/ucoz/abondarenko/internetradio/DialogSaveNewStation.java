package net.ucoz.abondarenko.internetradio;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DialogSaveNewStation extends DialogFragment {

    public interface OnDialogSaveEventListener {
        void saveDataStationListener(String name, String link);
    }

    OnDialogSaveEventListener onDialogSaveEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDialogSaveEventListener = (OnDialogSaveEventListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.save_station);
        View view = inflater.inflate(R.layout.dialog_add_station, null, false);
        EditText editTextNameStation = (EditText) view.findViewById(R.id.edNameStation);
        EditText editTextLinkStation = (EditText) view.findViewById(R.id.edLinkStation);
        Button buttonSave = (Button) view.findViewById(R.id.btnSave);
        Button buttonCancel = (Button) view.findViewById(R.id.btnCancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogSaveEventListener.saveDataStationListener(editTextNameStation.getText().toString(),
                        editTextLinkStation.getText().toString());
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
