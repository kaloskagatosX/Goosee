package com.wldtaster.tellmeastory;

/**
 * This class defined the Section object
 */
public class SectionItem implements Comparable<SectionItem>{

    private String SectionID;
    private String SectionNumber;
    private String BookID;
    private String Text_EN;
    private String Text_HK;
    private String PageNumber;
    private String PagePhotoID;
    private String SoundID;
    private String Comments;

    @Override
    public int compareTo(SectionItem CompareSectionItem) {
         Integer ThisSectionNumber=Integer.parseInt(this.getSectionNumber());
         Integer CompareSectionNumber=Integer.parseInt(CompareSectionItem.getSectionNumber());

        return ThisSectionNumber.compareTo(CompareSectionNumber);
    }
    //--------------------------------------------------------
    void setSectionID(String tmpSectionID){SectionNumber=tmpSectionID;}
    String getSectionID(){return SectionID;}
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setSectionNumber(String tmpSectionNumber){SectionNumber=tmpSectionNumber;}
    String getSectionNumber(){return SectionNumber;}
    //--------------------------------------------------------

    //--------------------------------------------------------
   void setBookID(String tmpBookID){BookID=tmpBookID;}
    String getBookID(){return BookID;
    }
    //--------------------------------------------------------


    //--------------------------------------------------------
    void setText_EN(String tmpText_EN){Text_EN=tmpText_EN;}
    String getText_EN(){
        return Text_EN;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setText_HK(String tmpText_HK){Text_HK=tmpText_HK;}
    String getText_HK(){
        return Text_HK;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setPageNumber(String tmpPageNumber){PageNumber=tmpPageNumber;}
    String getPageNumber(){
        return PageNumber;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setPagePhotoID(String tmpPagePhotoID){PagePhotoID=tmpPagePhotoID;}
    String getPagePhotoID(){
        return PagePhotoID;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setSoundID(String tmpSoundID){SoundID=tmpSoundID;}
    String getSoundID(){
        return SoundID;
    }
    //--------------------------------------------------------
    //--------------------------------------------------------
    void setComments(String tmpComments){Comments=tmpComments;}
    String getComments(){return Comments;}
    //--------------------------------------------------------

    //--------------------------------------------------------
    int getSectionPhotoID() {
        String MiniatureResourceName="";
        if ((PagePhotoID==null) || (PagePhotoID.length()<1)){
            MiniatureResourceName="m"+getBookID();
        }else{
            MiniatureResourceName=PagePhotoID;
        }
        int resId =  App.context().getResources().getIdentifier(MiniatureResourceName,"drawable",App.context().getPackageName());

        /**If the resource for the book cannot be found, used the default image*/
        if (resId==0){
            resId=App.context().getResources().getIdentifier("default_section","drawable",App.context().getPackageName());
        }
        return resId;
    }
    //--------------------------------------------------------


    //--------------------------------------------------------
    int getAudioResource() {
        String AudioResourceName="";
        if ((SoundID==null) || (SoundID.length()<1)){
            AudioResourceName="audio_"+getBookID() + "_" + getSectionNumber();
        }else{
            AudioResourceName=SoundID;
        }


        int resId = App.context().getResources().getIdentifier(AudioResourceName,"raw",App.context().getPackageName());

        /**If the resource for the book cannot be found, used the default audio*/
        if (resId==0){
            /*TODO The line below needs to be deleted as we do not need to play a default audio song for each book*/
            resId=App.context().getResources().getIdentifier("default_section","raw",App.context().getPackageName());
        }

        return resId;
    }
    //--------------------------------------------------------


}
