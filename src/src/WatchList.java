package src;

import java.util.ArrayList;
import java.util.List;

public class WatchList {
    //item list
    //now import list into Jlist with method

    private List<Item> holder;

    public WatchList() {
        this.holder = new ArrayList<>();
    }

    public List<Item> getHolder() {
        return holder;
    }

    public void setHolder(List<Item> holder) {
        this.holder = holder;
    }

    public void add(Item item){
        this.getHolder().add(item);
    }

    public void delete(Item item){
        this.getHolder().remove(item);
    }



}
