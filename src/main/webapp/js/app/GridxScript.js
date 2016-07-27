
define([
    "app/RequestHandler",

    "dijit/registry",
    "dojo/date/locale",
    "dojo/date/stamp",
    "dojo/store/Memory",
    "dojo/_base/json",

    "gridx/Grid",
    "gridx/core/model/cache/Async",
    "gridx/modules/HiddenColumns",
    "gridx/modules/extendedSelect/Row",
    "gridx/modules/SingleSort",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar"
], function(RequestHandler, registry, locale, stamp, Store, dojojson, Grid, Cache, HiddenColumns, SelectRow, SingleSort, Filter, FilterBar) {

    return {
        initialize: function (container, response) {

            var baseUrl = "http://localhost:8080/Adaptation/rest/";

            for(var i=0; i< response.columns.length; i++){
                response.columns[i].formatter = dojojson.fromJson(response.columns[i].formatter);
            }

            var store = new Store({data: response.model});
            var grid = new Grid({
                cacheClass: Cache,
                store: store,
                structure: response.columns,
                selectRowTriggerOnCell: true,
                modules: [HiddenColumns, SelectRow, SingleSort, Filter, FilterBar]
            });
            grid.on("rowClick", function(evt){

            });
            grid.on("rowDblClick", function(evt){
                var item = grid.store.get(grid.select.row.getSelected()[0]);
                var request = new RequestHandler();
                request.request(baseUrl + response.prefix + item.id, {async: true, handleAs: 'json'});
            });
            container.gridxNode.addChild(grid);
            grid.startup();
            grid.resize();
        }
    }
});