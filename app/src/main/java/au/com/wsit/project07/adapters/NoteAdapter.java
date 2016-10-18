package au.com.wsit.project07.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.wsit.project07.R;

import au.com.wsit.project07.utils.NoteItems;

/**
 * Created by guyb on 18/10/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
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
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position)
    {

        holder.bindViewHolder(mNoteItems.get(position));
    }



    @Override
    public int getItemCount()
    {
        return mNoteItems.size();
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

        public void bindViewHolder(NoteItems item)
        {
            try
            {
                mTitle.setText(item.getmNoteTitle());
                mDetails.setText(item.getmNoteDetails());
                animate();

            }
            catch(NullPointerException e)
            {
                Log.i(TAG, "Error binding item: " + e.getMessage());
            }

        }

        private void animate()
        {
            itemView.setScaleX(0);
            itemView.setScaleY(0);
            itemView.animate().scaleY(1).scaleX(1).start();
        }
    }
}
