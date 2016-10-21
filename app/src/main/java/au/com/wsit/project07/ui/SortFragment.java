package au.com.wsit.project07.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import au.com.wsit.project07.R;
import au.com.wsit.project07.utils.ToDoConstants;

/**
 * Created by guyb on 21/10/16.
 */
public class SortFragment extends DialogFragment
{

    private static final String TAG = SortFragment.class.getSimpleName();
    private SortCallback mListener;
    private RadioGroup mSortGroup;

    public interface SortCallback
    {
        void sortType(String type);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mListener = (SortCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_sort, container, false);

        mSortGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        mSortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.dateSort:
                        Log.i(TAG, "Sorting by date");
                        mListener.sortType(ToDoConstants.DATE);
                        dismiss();
                        break;
                    case R.id.AZSort:
                        Log.i(TAG, "Sorting alphabetically");
                        mListener.sortType(ToDoConstants.ALPHABETICALLY);
                        dismiss();
                        break;
                    case R.id.importanceSort:
                        Log.i(TAG, "Sorting by importance");
                        mListener.sortType(ToDoConstants.IMPORTANCE);
                        dismiss();
                        break;
                }
            }
        });


        return rootView;
    }
}
