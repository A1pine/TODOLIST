package com.comp2100.todolist;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public class MessageEvent {
    TaskDB todo;
    Place place;
    //Send tasks to the cardView List
    MessageEvent(TaskDB tasks){
        todo = tasks;
    }

    //Send location information to the addpage

    MessageEvent(Place place){
        this.place = place;
    }
}
