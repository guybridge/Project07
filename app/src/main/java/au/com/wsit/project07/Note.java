package au.com.wsit.project07;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by guyb on 17/10/16.
 */
public class Note
{

    private String mNoteTitle;
    private String mNoteDetails;
    private Context mContext;

    public interface Callback
    {
        void saved();
        void saveFailed(String error);
    }

    public String getmNoteTitle()
    {
        return mNoteTitle;
    }

    public void setmNoteTitle(String mNoteTitle)
    {
        this.mNoteTitle = mNoteTitle;
    }

    public String getmNoteDetails()
    {
        return mNoteDetails;
    }

    public void setmNoteDetails(String mNoteDetails)
    {
        this.mNoteDetails = mNoteDetails;
    }

    public void saveNote(final Callback callback)
    {
        ParseObject note = new ParseObject(ToDoConstants.NOTES_CLASS_NAME);
        note.put(ToDoConstants.NOTE_TITLE, mNoteTitle);
        note.put(ToDoConstants.NOTE_DETAILS, mNoteDetails);
        note.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    callback.saved();
                }
                else
                {
                    callback.saveFailed(e.getMessage());
                }
            }
        });
    }



}
