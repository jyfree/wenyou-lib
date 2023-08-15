package com.wenyou.baselibrary.acp

interface AcpApplyInterface {
    val isCustomApply: Boolean
    fun showApplyDialog(listener: AcpApplyActionListener)
}