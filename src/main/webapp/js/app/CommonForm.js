
define([
    "app/RequestHandler",
    "dojo/_base/declare",
    "dojo/Stateful",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_OnDijitClickMixin",
    "dijit/_WidgetsInTemplateMixin"
], function(
    RequestHandler,
    declare,
    Stateful,
    _WidgetBase,
    _TemplatedMixin,
    _OnDijitClickMixin,
    _WidgetsInTemplateMixin
){
    return declare("app.CommonForm", [ _WidgetBase, _TemplatedMixin, _OnDijitClickMixin, _WidgetsInTemplateMixin ], {
        baseClass: 'tabContent',
        templateString: null,
        model: null,
        response: null,
        baseUrl: "http://localhost:8080/Adaptation/rest/",
        
        constructor: function(response){
            this.templateString = response.template;
            this.model = new Stateful(response.model);
            this.response = response;
        },
        postCreate: function () {
            this.inherited(arguments);
            if(this.avatarNode){
                this.avatarNode.src = this.model.photoURL;
            }
            
            if(this.jsScript){
                var container = this;
                var response = this.response;
                require([this.response.jsScript], function (f) {
                    f.initialize(container, response);
                });
            }
            
        },
        sendEvent: function(params){
            console.log(params);
            console.log(this.response.prefix);
            /*this.request(params.request, {
                event: params.eventType,
                model: this.model
            });*/
        }
        
        //
    });
});