package au.com.wsit.project07.ui;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import au.com.wsit.project07.R;
import au.com.wsit.project07.adapters.NoteAdapter;
import au.com.wsit.project07.utils.Note;
import au.com.wsit.project07.utils.NoteItems;
import au.com.wsit.project07.utils.ToDoConstants;


public class MainActivity extends AppCompatActivity implements AddNoteFragment.Listener
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton mFab;
    private NoteAdapter mAdapter;
    private RecyclerView mNoteRecycler;
    private RecyclerView.LayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mNoteRecycler = (RecyclerView) findViewById(R.id.noteRecycler);
        mLayout = new LinearLayoutManager(MainActivity.this);
        mNoteRecycler.setLayoutManager(mLayout);

        mFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                animate();
                Log.i(TAG, "Add note clicked");
                AddNoteFragment addNote = new AddNoteFragment();
                FragmentManager fm = getFragmentManager();
                addNote.show(fm, "AddNoteFragment");
            }
        });

        getNotes();

    }

    // Get the notes from the Parse backend
    private void getNotes()
    {
        final ArrayList<NoteItems> noteList = new ArrayList<NoteItems>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ToDoConstants.NOTES_CLASS_NAME);
        query.findInBackground(new FindCallback<ParseObject>()
        {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {

                for (ParseObject object : objects)
                {
                    NoteItems note = new NoteItems();
                    note.setmNoteTitle(object.getString(ToDoConstants.NOTE_TITLE));
                    note.setmNoteDetails(object.getString(ToDoConstants.NOTE_DETAILS));
                    Log.i(TAG, "Title: " + object.getString(ToDoConstants.NOTE_TITLE));
                    Log.i(TAG, "Details: " + object.getString(ToDoConstants.NOTE_DETAILS));

                    noteList.add(note);

                }

                mAdapter = new NoteAdapter(MainActivity.this, noteList);
                mNoteRecycler.setAdapter(mAdapter);

            }
        });
    }

    private void animate()
    {
        mFab.setScaleX(0);
        mFab.setScaleY(0);
        mFab.animate().scaleX(1).scaleY(1);
    }

    // Result from dialog
    @Override
    public void result(String title, String details)
    {
        Note note = new Note();
        note.setmNoteTitle(title);
        note.setmNoteDetails(details);
        note.saveNote(new Note.Callback()
        {
            @Override
            public void saved()
            {
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
                getNotes();
            }

            @Override
            public void saveFailed(String error)
            {
                Toast.makeText(MainActivity.this, "Problem saving", Toast.LENGTH_LONG).show();
            }
        });
    }
}
