package com.volvain.yash;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Text2Speech {

    Context context;
    TextToSpeech tts;
    private void speak(String text){
        tts=new TextToSpeech(context,new TextToSpeech.OnInitListener(){
            public void onInit(int status){
                if(status != tts.ERROR)
                    tts.setLanguage(Locale.ENGLISH);
            }
        });


        tts.setSpeechRate(0.9f);
        tts.setPitch(.9f);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        else
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void stop(){//call this in onDestroy
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
        // super.onDestroy();
    }
}
