package com.iec.makeup.core



interface Reducer<State: Reducer.ViewState, Event: Reducer.ViewEvent, Effect: Reducer.ViewEffect> {
    /*
    - This viewState representation for state of the UI, contains all the compose need to display
     */
    interface ViewState

    /*
    - Hold User interaction
     */
    interface ViewEvent

    /*
    - Hold side effect, like navigation, toast
     */
    interface ViewEffect


    /*
    - The reduce function receive the state of current screen, and the user event, the produce the new state and side effect.
     */
    fun reduce(currentState: State, event: Event): Pair<State, Effect?>
}