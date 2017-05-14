/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johannes.lsctic.panels.gui.plugins.TextfilePlugin;

import com.google.common.eventbus.EventBus;
import com.johannes.lsctic.panels.gui.plugins.AddressBookEntry;
import com.johannes.lsctic.panels.gui.plugins.AddressLoader;
import com.johannes.lsctic.panels.gui.plugins.DataSource;

import java.util.ArrayList;

/**
 *
 * @author johannes
 */
public class TextFileLoader implements AddressLoader {

    private EventBus eventBus;
    private DataSource source;

    public TextFileLoader(DataSource source) {
        this.source = source;
    }

    @Override
    public ArrayList<AddressBookEntry> getResults(String ein, int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saved() {

    }

    @Override
    public void discarded() {

    }

    @Override
    public DataSource getDataSource() {
        return source;
    }

    @Override
    public void setEventBus(EventBus eventBus) {
        this.eventBus =eventBus;
    }

}
