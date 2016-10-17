package au.com.wsit.project07;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by guyb on 17/10/16.
 */
public class AddNoteFragment extends DialogFragment
{

    private static final String TAG = AddNoteFragment.class.getSimpleName();
    private EditText mNoteTitle;
    private EditText mNoteDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_add_note, container, false);

        mNoteTitle = (EditText) rootView.findViewById(R.id.noteTitle);
        mNoteDetails = (EditText) rootView.findViewById(R.id.noteDetails);


        return rootView;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        saveNote();
    }

    public void saveNote()
    {
        String noteTitle = mNoteTitle.getText().toString();
        String noteDetails = mNoteDetails.getText().toString();

        Note note = new Note();
        note.setmNoteTitle(noteTitle);
        note.setmNoteDetails(noteDetails);
        note.saveNote(new Note.Callback()
        {
            @Override
            public void saved()
            {
                Toast.makeText(getContext(), "Saved note", Toast.LENGTH_LONG).show();
            }

            @Override
            public void saveFailed(String error)
            {
                Toast.makeText(getContext(), "problem saving note", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Unable to save note: " + error);
            }
        });
    }
}
