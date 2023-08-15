package com.wenyou.loadsir.simple

import com.wenyou.loadsir.R

class LoadSirSimpleLayout : LoadSirLayoutInterface {

    private var mLoadSirLayoutInterface: LoadSirLayoutInterface? = null

    companion object {
        @Volatile
        private var sInstance: LoadSirSimpleLayout? = null

        fun get(): LoadSirSimpleLayout {
            if (sInstance == null) {
                synchronized(LoadSirSimpleLayout::class.java) {
                    if (sInstance == null) {
                        sInstance = LoadSirSimpleLayout()
                    }
                }
            }
            return sInstance!!
        }
    }

    fun init(mLoadSirLayoutInterface: LoadSirLayoutInterface?) {
        this.mLoadSirLayoutInterface = mLoadSirLayoutInterface
    }

    override fun emptyLayout(): Int {
        return mLoadSirLayoutInterface?.emptyLayout() ?: R.layout.simple_loadsir_callback_empty
    }

    override fun errorLayout(): Int {
        return mLoadSirLayoutInterface?.errorLayout() ?: R.layout.simple_loadsir_callback_error
    }

    override fun loadingLayout(): Int {
        return mLoadSirLayoutInterface?.loadingLayout() ?: R.layout.simple_loadsir_callback_loading
    }
}