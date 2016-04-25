package com.wldtaster.tellmeastory;

import android.content.Context;
import android.widget.MediaController;
/**
 * Created by Georges on 20/04/2016.
 */
public class MusicController extends MediaController {

    public MusicController(Context c){
        super(c);
    }

    public void hide(){}
    //Prevent the MediaController from automatically hiding after three seconds

}