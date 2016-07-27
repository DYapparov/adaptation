
define([
    "dojo/_base/declare",
    "dojo/request",
    "dijit/registry",
    "dojo/_base/lang",
    "dijit/layout/ContentPane",
    "app/CommonForm"
], function(
    declare,
    request,
    registry,
    lang,
    ContentPane,
    CommonForm
){
    return declare("app.RequestHandler", [], {
        request: function(url, params){
            request.get(url, params).then(lang.hitch(this, function(response){
                this._processResponse(response);
            }));
        },
        
        _processResponse: function(response){
            var container = null;
            var form = null;
            if (response.target == "NEW_TAB") {
                container = this._createContainer("TAB", { title: response.tabTitle });
                form = new CommonForm(response);
            } else if (response.target == "NEW_DIALOG") {
                //
                form = new CommonForm(response);
            } else {

            }
            container.set('content', form);
        },
        
        _createContainer: function(containerType, params){
            var parent = registry.byId('tabContainer');
            // var parent = this.tabContainerNode;
            if (containerType == "TAB") {
                var tab = new ContentPane({
                    title: params.title,
                    closable: true
                });
                parent.addChild(tab);
                return tab;
            } else if (containerType == "MENU") {
                
            }
        }
    });
});