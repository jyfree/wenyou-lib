package com.wenyou.supply.download.ok.bean

enum class DownState(val state: Int) {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
}