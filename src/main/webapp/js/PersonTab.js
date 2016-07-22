
define([
        "dijit/registry",
        "dojo/request",
        "dijit/layout/ContentPane",
        "dijit/form/Button",
        "dojox/form/Uploader",
        "dojo/_base/json",
        "http://localhost:8080/Adaptation/js/widget/PersonWidget.js",
        "http://localhost:8080/Adaptation/js/widget/EditPersonWidget.js"
    ], function(registry, request, ContentPane, Button, Uploader, dojo, PersonWidget, EditPersonWidget) {

    return {
        addPersonTab: function (id) {
            var url = "http://localhost:8080/Adaptation/rest/ecm/employees/";
            var tabContainer = registry.byId("tabContainer");
            var contentWidget;
            var person;
            var editMode = false;

            var pane = new ContentPane({
                id: 'personTab' + id,
                title: 'Person ' + id,
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
                        var data = contentWidget.validate();
                        if(data){
                            data = dojo.toJson(data);
                            request.post(url + "employee/update",{
                                 data: data,
                                 headers: {
                                 "Content-Type": 'application/json; charset=utf-8',
                                 "Accept": "application/json"
                                 }
                            }).then(function (data) {
                                tabContainer.removeChild(pane);
                                pane.destroyRecursive();
                            }, function (error) {});
                        } else {
                            return;
                        }
                    } else {
                        this.set('label', 'Сохранить');
                        editMode = !editMode;
                        contentWidget.destroyRecursive();
                        contentWidget = new EditPersonWidget(person);
                        pane.addChild(contentWidget);
                    }
                }
            });
            var photo = new Uploader({
                multiple: true,
                label: 'Фото',
                url: url + 'employee/update/photo/' + id,
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

            request.get(url + "employee/" + id, {
                handleAs: "json"
            }).then(function(data){
                person = data;
                contentWidget = new PersonWidget(person);
                pane.addChild(contentWidget);
                tabContainer.addChild(pane);
            }, function(error){alert(error);});


        }
    }
});
