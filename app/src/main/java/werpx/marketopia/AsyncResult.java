package werpx.marketopia;


import werpx.marketopia.RoomDatabase.Productltable;

public interface AsyncResult {
    void asyncFinished(Productltable results);

    void whereforfavourit(Integer results);


}