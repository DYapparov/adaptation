
define([
    "app/TabFactory",

    "dijit/registry",
    "dojo/request",
    "dijit/layout/ContentPane",
    "dojo/date/locale",
    "dojo/date/stamp",
    "dojo/store/Memory",

    "gridx/Grid",
    "gridx/core/model/cache/Async",
    "gridx/modules/HiddenColumns",
    "gridx/modules/extendedSelect/Row",
    "gridx/modules/SingleSort",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar"
], function(TabFactory, registry, request, ContentPane, locale, stamp, Store, Grid, Cache, HiddenColumns, SelectRow, SingleSort, Filter, FilterBar) {
    
    return {
        add: function () {
            var tabContainer = registry.byId("tabContainer");

            //Proverka na dublirovanie tabov
            var tabs= tabContainer.getChildren();
            for (var i = 0; i < tabs.length; i++) {
                if (tabs[i].id == 'personsTab') {
                    console.log('Such tab was already opened');
                    return;
                }
            }


            var tab = new ContentPane({
                id: 'personsTab',
                title: 'Slaves',
                closable: true
            });

            var columns = [
                {field: 'lastName', name: 'Lastname'},
                {field: 'firstName', name: 'Firstname'},
                {field: 'middleName', name: 'Middlename'},
                {field: 'post', name: 'Post', formatter: function (value, rowId) {
                    return value.post.name;
                }},
                {field: 'birthday', name: 'Birthday', formatter: function (value, rowId) {
                    var birthday = stamp.fromISOString(value.birthday);
                    return locale.format(birthday, {selector:"date", datePattern:"dd-MM-yyyy"});
                }}
            ];
            
            request.get("http://localhost:8080/Adaptation/rest/ecm/employees/", {
                handleAs: "json"
            }).then(function (data) {
                var store = new Store({data: data});
                var grid = new Grid({
                    id: "personsGrid",
                    cacheClass: Cache,
                    store: store,
                    structure: columns,
                    selectRowTriggerOnCell: true,
                    modules: [HiddenColumns, SelectRow, SingleSort, Filter, FilterBar]
                });
                grid.placeAt('personsTab');
                grid.on("rowClick", function(evt){

                });
                grid.on("rowDblClick", function(evt){
                    var person = grid.store.get(grid.select.row.getSelected()[0]);
                    console.log(person);
                    TabFactory.addPersonTab(person.id);
                });
                grid.startup();
                tabContainer.addChild(tab);
            }, function (error) {
                alert(error);
            });
        }
    }
});