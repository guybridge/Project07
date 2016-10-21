package au.com.wsit.project07.utils;

/**
 * Created by guyb on 18/10/16.
 */
public class NoteItems
{
    private String mNoteTitle;
    private String mNoteDetails;
    private String mNoteID;
    private boolean isImportant;

    public boolean isImportant()
    {
        return isImportant;
    }

    public void setImportant(boolean important)
    {
        isImportant = important;
    }



    public String getmNoteID()
    {
        return mNoteID;
    }

    public void setmNoteID(String mNoteID)
    {
        this.mNoteID = mNoteID;
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


}
