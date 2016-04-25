/**
 * @version 0.0.1
 * @since 12/04/2016
 */

package com.wldtaster.tellmeastory;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LibraryActivity extends AppCompatActivity {
    private static final String TAG = LibraryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /**Create the activity and populate the savedInstanceState if the activity existed but has been destroyed (otherwise savedInstanceState will return 'null'*/
        setContentView(R.layout.activity_library); /** Call to the XML layout library which display the activity */

        if (savedInstanceState != null) {
            // Restore value of members from saved state
        } else {
            // Probably initialize members with default values for a new instance
        }

        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            BooksFragment bookFragment = new BooksFragment();
            fragmentTransaction.add(R.id.library_activity_recycler_view, bookFragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d(TAG, "[ERROR] " + e.getMessage());
        }
    }
}
