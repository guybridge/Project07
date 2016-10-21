package au.com.wsit.project07.ui;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.wsit.project07.R;
import au.com.wsit.project07.adapters.NoteAdapter;
import au.com.wsit.project07.adapters.SimpleItemTouchHelperCallback;
import au.com.wsit.project07.utils.Note;
import au.com.wsit.project07.utils.NoteItems;
import au.com.wsit.project07.utils.ParseUtils;
import au.com.wsit.project07.utils.ToDoConstants;


public class MainActivity extends AppCompatActivity implements AddNoteFragment.Listener, SortFragment.SortCallback
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton mFab;
    private NoteAdapter mAdapter;
    private RecyclerView mNoteRecycler;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<NoteItems> mNoteList;

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
                animateButton();
                showDialogFragment();

            }
        });

        getNotes();

    }



    // Get the notes from the Parse backend
    private void getNotes()
    {

                final ParseUtils noteGetter = new ParseUtils();
                noteGetter.getNote(new ParseUtils.Callback()
                {
                    @Override
                    public void result(ArrayList<NoteItems> noteList)
                    {
                        mNoteList = noteList;
                        // Load notes into the adapter and display
                        mAdapter = new NoteAdapter(MainActivity.this, mNoteList);

                        ItemTouchHelper.Callback callback =
                                new SimpleItemTouchHelperCallback(mAdapter);
                        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                        touchHelper.attachToRecyclerView(mNoteRecycler);

                        mNoteRecycler.setAdapter(mAdapter);
                    }
                });

    }

    private void animateButton()
    {
        mFab.setScaleX(0);
        mFab.setScaleY(0);
        mFab.animate().scaleX(1).scaleY(1);
    }

    // Result from dialog
    @Override
    public void result(String title, String details, boolean isImportant)
    {
        Note note = new Note();
        note.setmNoteTitle(title);
        note.setmNoteDetails(details);
        note.setIsImportant(isImportant);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_sort:
                // Sort dialog
                showSortDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogFragment()
    {
        AddNoteFragment addNote = new AddNoteFragment();
        FragmentManager fm = getFragmentManager();
        addNote.show(fm, "AddNoteFragment");
    }

    private void showSortDialog()
    {
        FragmentManager fm = getFragmentManager();
        SortFragment sortFragment = new SortFragment();
        sortFragment.show(fm, "SortFragment");
    }

    // Callback from sort fragment
    @Override
    public void sortType(String type)
    {
        // Get the type selected and sort by that
       if(type.equals(ToDoConstants.DATE))
       {
           // Sort by date
           getNotes();
       }
       else if(type.equals(ToDoConstants.ALPHABETICALLY))
       {
           // Sort A-Z
       }
        else if(type.equals(ToDoConstants.IMPORTANCE))
       {
           // Sort by importance
           ParseUtils showImportant = new ParseUtils();
           showImportant.showImportant(new ParseUtils.ImportantCallback()
           {
               @Override
               public void result(ArrayList<NoteItems> noteList)
               {
                   mNoteList = noteList;
                   // Load notes into the adapter and display
                   mAdapter = new NoteAdapter(MainActivity.this, mNoteList);

                   ItemTouchHelper.Callback callback =
                           new SimpleItemTouchHelperCallback(mAdapter);
                   ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                   touchHelper.attachToRecyclerView(mNoteRecycler);

                   mNoteRecycler.setAdapter(mAdapter);
               }
           });
       }
    }
}
