package debug;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by vettel on 18-2-14.
 */

public class FileInitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
