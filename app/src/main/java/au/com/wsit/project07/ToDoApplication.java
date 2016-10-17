package au.com.wsit.project07;

import android.app.Application;

import com.parse.Parse;


/**
 * Created by guyb on 17/10/16.
 */
public class ToDoApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Parse.Configuration configuration = new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("uQdj1F255ULlelgibUCs2idc0w8sI8g1zt2KLe92")
                .clientKey("QDG8uu9q59SqiT61mlLGz6RGFOmQRiOgAPAm6wmX")
                .server("https://parseapi.back4app.com")
                .build();

        Parse.initialize(configuration);
    }
}
