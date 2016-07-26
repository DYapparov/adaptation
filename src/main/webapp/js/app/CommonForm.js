/**
 * Created by dyapparov on 26.07.2016.
 */
require([
    "dojo/_base/declare",
    "dojo/Stateful",
    "js/RequestHandler"
], function(
    declare,
    Stateful,
    RequestHandler
){
    return declare("js.CommonForm", [ widgetMixins, RequestHandler ], {
        templateString: null,
        model: null,
        
        constructor: function(params){
            this.templateString = params.template;
            this.model = new Stateful(params.model);
        },
        
        sendEvent: function(params){
            this.request(params.request, { 
                event: params.eventType,
                model: this.model
            });
        }
        
        //
    });
});