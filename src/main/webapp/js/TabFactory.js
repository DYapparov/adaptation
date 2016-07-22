
define([
        "dijit/registry",
        "dojo/request",
        "dijit/layout/ContentPane",
        "dijit/form/Button",
        "dojox/form/Uploader",
        "dojo/_base/json",
        "http://localhost:8080/Adaptation/js/ContentWidget.js"
    ], function(registry, request, ContentPane, Button, Uploader, dojo, ContentWidget) {

    return {
        addTab: function (buttonId) {
            var url = "http://localhost:8080/Adaptation/rest/what_should_i_do/";
            var tabContainer = registry.byId("tabContainer");
            var editMode = false;
            
            request.get(url + buttonId, {
                handleAs: "json"
            }).then(function(data){
                var model = data.model;
                var template = data.template;
                var contentWidget = new ContentWidget(model, template);
                var pane = new ContentPane({
                    id: 'tab' + (model.lastName||model.docName) + model.id,
                    title: (model.lastName||model.docName),
                    closable: true
                });

                var buttonPane = new ContentPane({
                    baseClass: "topMenu"
                });

                var close = new Button({
                    showlabel: true,
                    label: 'Закрыть',
                    onClick: function () {
                        tabContainer.removeChild(pane);
                        pane.destroyRecursive();
                    }
                });
                var edit = new Button({
                    showlabel: true,
                    label: 'Изменить',
                    onClick: function () {
                        if(editMode){
                            this.set('label', 'Изменить');
                            editMode = !editMode;
                            var data = contentWidget.validate();
                            if(data){
                                var jsonData = dojo.toJson(data);
                                request.post(url + "update/person",{
                                    data: jsonData,
                                    handleAs: "json",
                                    headers: {
                                        "Content-Type": 'application/json; charset=utf-8',
                                        "Accept": "application/json"
                                    }
                                }).then(function (data) {
                                    contentWidget.destroyRecursive();
                                    contentWidget = new ContentWidget(data.model, data.template);
                                    pane.addChild(contentWidget);
                                }, function (error) {});
                            } else {
                                return;
                            }
                        } else {
                            this.set('label', 'Сохранить');
                            editMode = !editMode;
                            request.get(url + "edit/" + buttonId, {
                                handleAs: "json"
                            }).then(function(data){
                                contentWidget.destroyRecursive();
                                contentWidget = new ContentWidget(data.model, data.template);
                                pane.addChild(contentWidget);
                            }, function(error){alert(error);});
                        }
                    }
                });
                var photo = new Uploader({
                    multiple: true,
                    label: 'Фото',
                    url: url + 'employee/update/photo/' + model.id,
                    uploadOnSelect: true,
                    onComplete: function (files) {
                        console.log('finished');
                    }
                });
                var test = new Button({
                    showlabel: true,
                    label: 'test',
                    onClick: function () {
                        //TODO DeletePersonRequest
                    }
                });

                buttonPane.addChild(close);
                buttonPane.addChild(edit);
                buttonPane.addChild(photo);
                buttonPane.addChild(test);
                pane.addChild(buttonPane);
                pane.addChild(contentWidget);
                tabContainer.addChild(pane);
            }, function(error){alert(error);});
        }
    }
});
