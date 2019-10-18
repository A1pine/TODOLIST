package com.comp2100.todolist;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public class MessageEvent {
    Place place;


    //Send location information to the addpage

    MessageEvent(Place place){
        this.place = place;
    }
}
