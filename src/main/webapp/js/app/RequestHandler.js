/**
 * Created by dyapparov on 26.07.2016.
 */
require([
    "dojo/_base/declare",
    "dojo/request",
    "dojo/_base/lang",
    "dijit/layout/ContentPane",
    "js/CommonForm"
], function(
    declare,
    request,
    lang,
    ContentPane,
    CommonForm
){
    return declare("js.RequestHandler", [], {
        request: function(url, params){
            request.get(url, params).then(lang.hitch(this, function(response){
                this._processResponse(response);
            }));
        },
        
        _processResponse: function(response){
            var container = null;
            if (response.target = "NEW_TAB") {
                container = this._createContainer("TAB", { title: response.tabTitle });
            } else if (response.target = "NEW_DIALOG") {
                //
            }
            var form = new CommonForm(response);
            container.set('content', form);
        },
        
        _createContainer: function(container, params){
            var parent = this.tabContainer;
            if (container == "TAB") {
                var tab = new ContentPane({
                    title: params.title
                });
                params.addChild(tab);
                return tab;
            } else {
                //
            }
        }
    });
});