package com.wenyou.baselibrary.acp

/**
 * acp申请权限前的操作
 */
class AcpApply : AcpApplyInterface {

    private var applyImpl: AcpApplyInterface? = null

    companion object {
        @Volatile
        private var sInstance: AcpApply? = null

        fun get(): AcpApply {
            if (sInstance == null) {
                synchronized(AcpApply::class.java) {
                    if (sInstance == null) {
                        sInstance = AcpApply()
                    }
                }
            }
            return sInstance!!
        }
    }

    fun init(applyImpl: AcpApplyInterface) {
        this.applyImpl = applyImpl
    }


    override val isCustomApply: Boolean
        get() = applyImpl?.isCustomApply ?: false

    override fun showApplyDialog(listener: AcpApplyActionListener) {
        if (isCustomApply) {
            applyImpl?.showApplyDialog(listener)
        }
    }
}