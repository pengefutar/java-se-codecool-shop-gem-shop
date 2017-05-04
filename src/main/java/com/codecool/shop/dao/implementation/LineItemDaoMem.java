package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.03..
 */
public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> DATA = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private LineItemDaoMem() {
    }

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(DATA.size() + 1);
        DATA.add(lineItem);
    }

    @Override
    public LineItem find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<LineItem> getAll() {
        return DATA;
    }

}
