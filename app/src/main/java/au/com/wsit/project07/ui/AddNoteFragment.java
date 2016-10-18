package au.com.wsit.project07.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.wsit.project07.utils.Note;
import au.com.wsit.project07.R;

/**
 * Created by guyb on 17/10/16.
 */
public class AddNoteFragment extends DialogFragment
{

    private static final String TAG = AddNoteFragment.class.getSimpleName();
    private EditText mNoteTitle;
    private EditText mNoteDetails;
    private Button mSaveNote;
    private Listener mListener;

    public interface Listener
    {
        void result(String title, String details);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mListener = (Listener)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_add_note, container, false);

        mNoteTitle = (EditText) rootView.findViewById(R.id.noteTitle);
        mNoteDetails = (EditText) rootView.findViewById(R.id.noteDetails);
        mSaveNote = (Button) rootView.findViewById(R.id.saveButton);


        mSaveNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String noteTitle = mNoteTitle.getText().toString();
                String noteDetails = mNoteDetails.getText().toString();
                if(noteTitle.equals("") && noteDetails.equals(""))
                {
                    dismiss();
                }
                else
                {
                    mListener.result(noteTitle, noteDetails);
                    dismiss();
                }

            }
        });

        return rootView;
    }


}
