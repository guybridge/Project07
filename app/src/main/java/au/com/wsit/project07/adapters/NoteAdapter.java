package au.com.wsit.project07.adapters;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

import au.com.wsit.project07.R;

import au.com.wsit.project07.utils.NoteHeaderItems;
import au.com.wsit.project07.utils.NoteItems;
import au.com.wsit.project07.utils.ParseUtils;
import au.com.wsit.project07.utils.ToDoConstants;

/**
 * Created by guyb on 18/10/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final String TAG = NoteAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<NoteItems> mNoteItems;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;


    public NoteAdapter(Context context, ArrayList<NoteItems> noteItems)
    {
        mContext = context;
        mNoteItems = noteItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        ViewHolder viewHolder =  new ViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {

        ViewHolder vhItem = (ViewHolder)holder;
        vhItem.mTitle.setText(mNoteItems.get(position).getmNoteTitle());
        vhItem.mDetails.setText(mNoteItems.get(position).getmNoteDetails());


        // Delete the note on long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                final String noteID = mNoteItems.get(position).getmNoteID();
                deleteNote(holder, noteID);
                return false;
            }
        });

    }


    // Deletes a note
    private void deleteNote(final RecyclerView.ViewHolder holder, final String noteID)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to remove this note?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                ParseQuery<ParseObject> deleteQuery = ParseQuery.getQuery(ToDoConstants.NOTES_CLASS_NAME);
                try
                {
                    deleteQuery.get(noteID).deleteEventually();
                    notifyItemRemoved(holder.getAdapterPosition());

                }
                catch (ParseException e)
                {
                    Log.i(TAG, "Problem deleting item");
                }
            }

        });

        builder.setNegativeButton("Nope", null);
        AlertDialog dialog = builder.create();
        animate(dialog);
        dialog.show();
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

//    private void animateItem()
//    {
//
//            itemView.setScaleX(0);
//            itemView.setScaleY(0);
//            itemView.animate().scaleY(1).scaleX(1).start();
//
//    }


    @Override
    public int getItemCount()
    {
        return mNoteItems.size();
    }

    public int getItemViewType(int position)
    {
        if(isPositionHeader(position))
        {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }


    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mTitle;
        private TextView mDetails;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.itemNoteTitle);
            mDetails = (TextView) itemView.findViewById(R.id.itemNoteDetails);

        }

    }
}
