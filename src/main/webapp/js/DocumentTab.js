/**
 * Created by dyapparov on 19.07.2016.
 */


define([
    "dijit/registry",
    "dojo/request",
    "dijit/layout/ContentPane"
], function(registry, request, ContentPane) {

    return {
        addDocumentTab: function (id) {
            var tabContainer = registry.byId("tabContainer");
            var pane = new ContentPane({
                id: 'docsTab' + id,
                title: 'Document ' + id,
                closable: true
            });

            request.get("http://localhost:8080/Adaptation/rest/documents/document/" + id, {
                handleAs: "json"
            }).then(function(data){
                pane.set('content', data.docName);
                tabContainer.addChild(pane);
            }, function(error){alert(error);});
        }
    }
});
