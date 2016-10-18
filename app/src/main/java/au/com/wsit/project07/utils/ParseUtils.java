package au.com.wsit.project07.utils;

import android.telecom.Call;
import android.util.Log;

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
                    NoteItems note = new NoteItems();
                    note.setmNoteTitle(object.getString(ToDoConstants.NOTE_TITLE));
                    note.setmNoteDetails(object.getString(ToDoConstants.NOTE_DETAILS));
                    Log.i(TAG, "Title: " + object.getString(ToDoConstants.NOTE_TITLE));
                    Log.i(TAG, "Details: " + object.getString(ToDoConstants.NOTE_DETAILS));

                    noteList.add(note);
                }

                callback.result(noteList);
            }
        });
    }
}
