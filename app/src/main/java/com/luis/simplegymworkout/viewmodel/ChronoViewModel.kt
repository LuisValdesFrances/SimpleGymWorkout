package com.luis.simplegymworkout.viewmodel

import androidx.lifecycle.ViewModel

class ChronoViewModel() : ViewModel() {

    var currentTime : Long = 0

    enum class State {
        STOP, RUN, PAUSE
    }
    var state : State = State.STOP

}