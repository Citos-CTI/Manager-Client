package com.johannes.lsctic.panels.gui.plugins.MysqlPlugin;

import com.johannes.lsctic.panels.gui.plugins.*;

import java.util.ArrayList;

/**
 * Created by johannes on 22.03.2017.
 */
public class MysqlPlugin implements AddressPlugin {
    private MysqlLoader loader;
    private MysqlSettingsField settingsField;
    private String AUTHOR = "Johannes Engler";
    private String AUTHOR_CONTACT = "engler.johannes@posteo.de";
    private String PLUGIN_NAME= "MysqlPlugin";
    private String PLUGIN_TAG = "MySql";

    public MysqlPlugin() {

        DataSource source = new DataSource(PLUGIN_NAME, PLUGIN_TAG);
        loader = new MysqlLoader(source);
        settingsField = new MysqlSettingsField(loader, "MySql", PLUGIN_NAME);

    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public AddressLoader getLoader() {
        return loader;
    }

    @Override
    public void setLoader(AddressLoader loader) {
        this.loader = (MysqlLoader) loader;
    }

    @Override
    public PluginSettingsField getPluginSettingsField() {
        return settingsField;
    }

    @Override
    public void setPluginSettingsField(PluginSettingsField settingsField) {
        this.settingsField = (MysqlSettingsField) settingsField;
    }

    @Override
    public String getAuthor() {
        return AUTHOR;
    }

    @Override
    public String getAuthorContact() {
        return AUTHOR_CONTACT;
    }

    @Override
    public ArrayList<AddressBookEntry> getResults(String query, int number) {
        return loader.getResults(query, number);
    }

    @Override
    public ArrayList<String[]> getDataFields() {
        return loader.getStorage().getMysqlFields();
    }

    @Override
    public void setDataFields(ArrayList<String[]> datasourceFields) {
        ArrayList<String> datasourceViewNames = new ArrayList<>();
        for (String[] datasourceField : datasourceFields) {
            datasourceViewNames.add(datasourceField[1]);
        }
        loader.getDataSource().setAvailableFields(datasourceViewNames);
        loader.getStorage().setMysqlFields(datasourceFields);
        loader.getStorageTemp().setMysqlFields(datasourceFields);
    }

    @Override
    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(loader.getStorage().getServerAddress());
        options.add(String.valueOf(loader.getStorage().getServerPort()));
        options.add(loader.getStorage().getDatabase());
        return options;
    }

    @Override
    public void setOptions(ArrayList<String> options) {
        if(options.isEmpty()) {
            options.add("localhost");
            options.add("2323");
            options.add("database");
        }
        loader.getStorage().setServerAddress(options.get(0));
        loader.getStorage().setServerPort(Integer.valueOf(options.get(1)));
        loader.getStorage().setDatabase(options.get(2));

        //to force storageTemp = storage
        loader.discarded();
    }

}
