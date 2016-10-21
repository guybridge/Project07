package au.com.wsit.project07.utils;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guyb on 18/10/16.
 */
public class ParseUtils
{
    private static final String TAG = ParseUtils.class.getSimpleName();

    public interface Callback
    {
        void result(ArrayList<NoteItems> noteList);
    }

    public interface ImportantCallback
    {
        void result(ArrayList<NoteItems> noteList);
    }


    public void getNote(final Callback callback)
    {
        final ArrayList<NoteItems> noteList = new ArrayList<NoteItems>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ToDoConstants.NOTES_CLASS_NAME);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>()
        {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {

                for (ParseObject object : objects)
                {

                    Log.i(TAG, "Note createdAt: " + object.getCreatedAt().toString());

                    // Item
                    NoteItems note = new NoteItems();
                    note.setmNoteTitle(object.getString(ToDoConstants.NOTE_TITLE));
                    note.setmNoteDetails(object.getString(ToDoConstants.NOTE_DETAILS));
                    note.setmNoteID(object.getObjectId());
                    note.setImportant(object.getBoolean(ToDoConstants.NOTE_IMPORTANT));

                    Log.i(TAG, "Title: " + object.getString(ToDoConstants.NOTE_TITLE));
                    Log.i(TAG, "Details: " + object.getString(ToDoConstants.NOTE_DETAILS));
                    Log.i(TAG, "Note ID: " + object.getObjectId());

                    noteList.add(note);
                }

                callback.result(noteList);
            }
        });
    }

    public void showImportant(final ImportantCallback callback)
    {
        final ArrayList<NoteItems> noteList = new ArrayList<NoteItems>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ToDoConstants.NOTES_CLASS_NAME);
        query.whereEqualTo(ToDoConstants.NOTE_IMPORTANT, true);
        query.findInBackground(new FindCallback<ParseObject>()
        {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if (e == null)
                {
                    for (ParseObject object : objects)
                    {
                        // Item
                        NoteItems note = new NoteItems();
                        note.setmNoteTitle(object.getString(ToDoConstants.NOTE_TITLE));
                        note.setmNoteDetails(object.getString(ToDoConstants.NOTE_DETAILS));
                        note.setmNoteID(object.getObjectId());
                        note.setImportant(object.getBoolean(ToDoConstants.NOTE_IMPORTANT));

                        Log.i(TAG, "IMPORTANT: Title: " + object.getString(ToDoConstants.NOTE_TITLE));
                        Log.i(TAG, "IMPORTANT: Details: " + object.getString(ToDoConstants.NOTE_DETAILS));
                        Log.i(TAG, "IMPORTANT: Note ID: " + object.getObjectId());

                        noteList.add(note);
                    }

                    callback.result(noteList);
                }
            }
        });

    }




}
