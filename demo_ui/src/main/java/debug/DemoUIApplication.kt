package debug

import android.app.Application
import com.wenyou.uidemo.glide.GlideImageLoadStrategySimple
import com.wenyou.yuilibrary.YUI

class DemoUIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        YUI.init(this, GlideImageLoadStrategySimple(), true)
    }
}