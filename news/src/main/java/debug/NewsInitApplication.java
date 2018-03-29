package debug;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by ZhangPeng on 2-22-0022.
 */

public class NewsInitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
