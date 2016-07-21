
define([
        "dijit/registry",
        "dojo/request",
        "dijit/layout/ContentPane",
        "http://localhost:8080/Adaptation/js/widget/PersonWidget.js"
    ], function(registry, request, ContentPane, PersonWidget) {

    return {
        addPersonTab: function (id) {
            var tabContainer = registry.byId("tabContainer");

            var pane = new ContentPane({
                id: 'personTab' + id,
                title: 'Person ' + id,
                closable: true
            });

            request.get("http://localhost:8080/Adaptation/rest/ecm/employees/employee/" + id, {
                handleAs: "json"
            }).then(function(data){
                var widget = new PersonWidget(data);
                pane.set('content', widget);
                tabContainer.addChild(pane);
            }, function(error){alert(error);});

        }
    }
});
