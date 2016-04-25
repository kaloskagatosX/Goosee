package com.wldtaster.tellmeastory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class SectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        BookItem selectedBookItem = (BookItem) getIntent().getSerializableExtra(Constants.bookItemObject);

        int SectionPos = 0;


        BooksAndSections tmpBookCollection = new BooksAndSections();

        ArrayList<SectionItem> SectionItems = tmpBookCollection.getSectionItems(selectedBookItem.getBookID());


        SectionItem currentSectionItem = SectionItems.get(SectionPos);


        refreshSectionActivity(SectionItems, currentSectionItem,selectedBookItem);


    }

    void refreshSectionActivity(ArrayList<SectionItem> tmpSectionItems, SectionItem tmpSectionItem,BookItem tmpBookItem){
        TextView SectionTextViewBookName_EN = (TextView) findViewById(R.id.textViewBookName_EN);
        SectionTextViewBookName_EN.setText("Book ID "+ tmpBookItem.getBookID() + " contains "+ Integer.toString(tmpSectionItems.size()) + " items");

        TextView SectionTextViewDescription_EN = (TextView) findViewById(R.id.textViewText_EN);
        SectionTextViewDescription_EN.setText(tmpSectionItem.getText_EN());

        TextView SectionTextViewDescription_HK = (TextView) findViewById(R.id.textViewText_HK);
        SectionTextViewDescription_HK.setText(tmpSectionItem.getText_HK());
    }
}
