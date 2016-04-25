package com.wldtaster.tellmeastory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BooksFragment extends Fragment {

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView bookRecycler = (RecyclerView) v.findViewById(R.id.Book_Recycler_View);

        /*
        bookRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        }); */




        bookRecycler.setLayoutManager(layoutManager);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter();
        bookRecycler.setAdapter(adapter);

        /*In the line below should we return the view 'v' or the RecyclerView 'bookRecycler'??*/
        return v;
    }
}
