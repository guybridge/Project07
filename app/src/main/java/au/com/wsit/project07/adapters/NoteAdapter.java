package au.com.wsit.project07.adapters;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;

import au.com.wsit.project07.R;

import au.com.wsit.project07.utils.NoteItems;
import au.com.wsit.project07.utils.ToDoConstants;

/**
 * Created by guyb on 18/10/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter
{
    private static final String TAG = NoteAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<NoteItems> mNoteItems;


    public NoteAdapter(Context context, ArrayList<NoteItems> noteItems)
    {
        mContext = context;
        mNoteItems = noteItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {

        ViewHolder vhItem = (ViewHolder)holder;
        vhItem.mTitle.setText(mNoteItems.get(position).getmNoteTitle());
        vhItem.mDetails.setText(mNoteItems.get(position).getmNoteDetails());

        if(mNoteItems.get(position).isImportant())
        {
            vhItem.mImportant.setVisibility(View.VISIBLE);
        }
        else
        {
            vhItem.mImportant.setVisibility(View.GONE);
        }


        animateItem(vhItem.itemView);

    }


    // Deletes a note
    private void deleteNote(final String noteID)
    {
        ParseQuery<ParseObject> deleteQuery = ParseQuery.getQuery(ToDoConstants.NOTES_CLASS_NAME);
        try
        {
            deleteQuery.get(noteID).deleteEventually();
            Log.i(TAG, "Note deleted");

        }
        catch (ParseException e)
        {
            Log.i(TAG, "Problem deleting item: " + e.getMessage());
        }
    }

    // Animate dialogs
    private void animate(Dialog dialog)
    {

            View view = dialog.getWindow().getDecorView();

            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(view,
                    PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
            scaleDown.setDuration(400);
            scaleDown.start();

    }

    private void animateItem(View itemView)
    {

            itemView.setScaleX(0);
            itemView.setScaleY(0);
            itemView.animate().scaleY(1).scaleX(1).start();

    }


    @Override
    public int getItemCount()
    {
        return mNoteItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition)
    {
        if(fromPosition < toPosition)
        {
            for (int i = fromPosition; i < toPosition; i++)
            {
                Collections.swap(mNoteItems, i, i + 1);

            }
        }
        else
        {
            for (int i = fromPosition; i > toPosition; i--)
            {
                Collections.swap(mNoteItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position)
    {
        final String noteID = mNoteItems.get(position).getmNoteID();
        deleteNote(noteID);

        mNoteItems.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mTitle;
        private TextView mDetails;
        private ImageView mImportant;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.itemNoteTitle);
            mDetails = (TextView) itemView.findViewById(R.id.itemNoteDetails);
            mImportant = (ImageView) itemView.findViewById(R.id.importantImageView);

        }

    }
}
