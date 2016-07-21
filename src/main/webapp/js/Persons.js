
define([
    "dijit/registry",
    "dojo/request",
    "dijit/layout/ContentPane",
    "dojox/data/JsonRestStore",

    "gridx/Grid",
    "gridx/core/model/cache/Sync",
    "gridx/modules/HiddenColumns",
    "gridx/modules/extendedSelect/Row",
    "gridx/modules/SingleSort",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar"
], function(registry, request, ContentPane, JsonRestStore, Grid, Cache, HiddenColumns, SelectRow, SingleSort, Filter, FilterBar) {
    
    return {
        add: function () {
            var tabContainer = registry.byId("tabContainer");
            var tab = new ContentPane({
                id: 'personsTab',
                title: 'Slaves',
                closable: true
            });

            var store = new JsonRestStore({
                target: "http://localhost:8080/Adaptation/rest/ecm/employees/"
            });

            var grid = new Grid({
                id: "personsGrid",
                cacheClass: Cache,
                store: store,
                selectRowTriggerOnCell: true,
                modules: [HiddenColumns, SelectRow, SingleSort, Filter, FilterBar]
            });
            grid.placeAt('personsTab');
            grid.on("rowClick", function(evt){

            });
            grid.startup();
            tabContainer.addChild(tab);
        }
    }
});