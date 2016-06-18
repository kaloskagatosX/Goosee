package com.wldtaster.tellmeastory;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * View Adapter
 */

class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {
	private static final String TAG = "BookRecyclerViewAdapter"; //Tag to identify the source of the errors
	static private ArrayList<BookItem> BookItems;
	private BooksAndSections listOfBooksAndSections;
	//static private int TagCardViewBookItem=0;

	/**
	 * Class prototype
	 */
	public BookRecyclerViewAdapter() {
		listOfBooksAndSections = new BooksAndSections();
		BookItems = listOfBooksAndSections.getBookItems();
	}

	@Override
	public BookRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//Create a new view and inflate the layout 'R.layout.card_book'
		CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book, parent, false);
		return new ViewHolder(cv);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		//Set the values inside a given view
		CardView cardView = holder.cardView;

		BookItem cardViewBookItem = BookItems.get(position);

		cardView.setTag(R.string.TagCardViewBookItem, cardViewBookItem); //Store the BookItem object into the CardView at index 'TagCardViewBookItem'

		BookItem tmpCardViewBookItem = (BookItem) cardView.getTag(R.string.TagCardViewBookItem);


		ImageView imageView = (ImageView) cardView.findViewById(R.id.imageViewBookImage);
		imageView.setImageResource(BookItems.get(position).getBookPhotoID());

		TextView textViewBookName_EN = (TextView) cardView.findViewById(R.id.textViewBookName_EN);
		textViewBookName_EN.setText(BookItems.get(position).getBookName_EN() + " (" + BookItems.get(position).getBookName_HK() + ")");


		TextView textViewBookDescription = (TextView) cardView.findViewById(R.id.textViewBookDescription);
		textViewBookDescription.setText(BookItems.get(position).getBookDescription());
		//textViewBookDescription.setVerticalScrollBarEnabled(true);

		/*
		textViewBookDescription.setMovementMethod(new ScrollingMovementMethod());
		textViewBookDescription.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

					v.getParent().requestDisallowInterceptTouchEvent(true);


				return false;
			}

		});

		textViewBookDescription.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override
			public void onScrollStateChanged(TextView textView, int newState) {
				//super.onScrollStateChanged(textView, newState);
				textView.getParent().requestDisallowInterceptTouchEvent(true);
				Log.d(TAG, "<<onScrollStateChanged...>>");
			}
		}); */

	}

	@Override
	public int getItemCount() {
		//Return the number of items in the data set
		try {
			return BookItems.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		//Define the view holder
		private CardView cardView;
		//public ClipData.Item currentItem;


		/**
		 * Each ViewHolder will display a CardView
		 */
		public ViewHolder(CardView v) {
			super(v);
			cardView = v;

			cardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					BooksAndSections tmpBookCollection = new BooksAndSections();
					BookItem cardViewBookItem = (BookItem) v.getTag(R.string.TagCardViewBookItem); //Retrieve the BookItem object from the CardView Tag
					ArrayList<SectionItem> numberOfSectionsInSelectedBook = tmpBookCollection.getSectionItems(cardViewBookItem.getBookID());

					if (numberOfSectionsInSelectedBook.size() < 1) {
						Toast.makeText(App.context(), "There are no sections in the selected book!", Toast.LENGTH_SHORT).show();
					} else {

						Intent tmpIntent = new Intent(App.context(), SectionActivity.class);
						tmpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						tmpIntent.putExtra(Constants.bookItemObject, cardViewBookItem);
						App.context().startActivity(tmpIntent);
					}
				}
			});

		}
	}
}