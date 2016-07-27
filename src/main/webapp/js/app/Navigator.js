
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/text!./templates/Navigator.html",
    "dojo/on",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/store/Memory",
    "dijit/tree/ObjectStoreModel",
    "dijit/Tree",
    "app/RequestHandler"
], function (declare, 
             lang, 
             template, 
             on,
             _WidgetBase, 
             _TemplatedMixin, 
             _WidgetsInTemplateMixin, 
             Memory, 
             ObjectStoreModel, 
             Tree, 
             RequestHandler) {
    return declare('app.Navigator', [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

        baseClass: "fullHeight",
        templateString: template,
        baseUrl: "http://localhost:8080/Adaptation/rest/list/",

        constructor: function () {
            lang.mixin(this);
        },
        postCreate: function () {
            this.inherited(arguments);
            var baseUrl = this.baseUrl;
            {
                var personsStore = new Memory({
                    data: [
                        { id: 'structure', name:'Структура'},
                        { id: 'persons', name:'Сотрудники', parent: 'structure' },
                        { id: 'organizations', name:'Организации', parent: 'structure' },
                        { id: 'departments', name:'Отделы', parent: 'structure' }
                    ],
                    getChildren: function(object){
                        return this.query({parent: object.id});
                    }
                });

                var personsModel = new ObjectStoreModel({
                    store: personsStore,
                    query: {id: 'structure'}
                });

                var personsTree = new Tree({
                    model: personsModel,
                    onClick: function (item,node,evt) {
                        var request = new RequestHandler();
                        request.request(baseUrl +item.id, {async: true, handleAs: 'json'});
                    }
                });
                personsTree.placeAt(this.employeesMenuNode);
                personsTree.startup();
            }
            {
                var docsStore = new Memory({
                    data: [
                        { id: 'structure', name:'Документы'},
                        { id: 'tasks', name:'Поручения', parent: 'structure' },
                        { id: 'incomings', name:'Входящие', parent: 'structure' },
                        { id: 'outgoings', name:'Исходящие', parent: 'structure' }
                    ],
                    getChildren: function(object){
                        return this.query({parent: object.id});
                    }
                });

                var docsModel = new ObjectStoreModel({
                    store: docsStore,
                    query: {id: 'structure'}
                });

                var docsTree = new Tree({
                    model: docsModel,
                    onClick: function (item,node,evt) {
                        var request = new RequestHandler();
                        request.request(baseUrl +item.id, {async: true, handleAs: 'json'});
                    }
                });
                docsTree.placeAt(this.docsMenuNode);
                docsTree.startup();
            }


            var request = new RequestHandler();
            request.request(this.baseUrl + "persons", {async: true, handleAs: 'json'});
            //request.request("http://localhost:8080/Adaptation/rest/ecm/employees/employee/1", {async: true, handleAs: 'json'});
            
            this.own(
                on(this.employeesMenuNode, 'click', lang.hitch(this, "_clickkk"))
            );
        },
        _clickkk: function (e) {
            console.log('employeesMenuNode was clicked');
        }
    })
});
