package au.com.wsit.project07;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                animate();
                Log.i(TAG, "Add note clicked");
                AddNoteFragment addNote = new AddNoteFragment();
                FragmentManager fm = getFragmentManager();
                addNote.show(fm , "AddNoteFragment");

            }
        });


    }

    private void animate()
    {
        mFab.setScaleX(0);
        mFab.setScaleY(0);
        mFab.animate().scaleX(1).scaleY(1);
    }
}
