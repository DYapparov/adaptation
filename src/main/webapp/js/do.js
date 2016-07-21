

require([
    "http://localhost:8080/Adaptation/js/widget/PersonWidget.js",
    "http://localhost:8080/Adaptation/js/PersonTab.js",
    "http://localhost:8080/Adaptation/js/DocumentTab.js",
    //"http://localhost:8080/Adaptation/js/Persons.js",
    "dijit/registry",
    "dijit/Menu",
    "dijit/MenuItem",
    "dojo/request",
    "dijit/layout/ContentPane",
    "dojo/domReady!"
], function(PersonWidget, PersonTab, DocumentTab, /*Persons,*/ registry, Menu, MenuItem, request, ContentPane){

    //
    //fill Persons menu
    //

    var personsMenu = new Menu({
        onItemClick: function (item, event) {
            PersonTab.addPersonTab(item.params);
        }
    }, "personsDiv");
    request.get("http://localhost:8080/Adaptation/rest/ecm/employees", {
        handleAs: "json"
    }).then(function(data){
        for(var i = 0; i<data.length; i++){
            var person = data[i];
            personsMenu.addChild(new MenuItem({
                id: "personItem_" + person.id,
                label: person.lastName + " " + person.firstName + " " + person.middleName,
                params: person.id
            }));
        }
        personsMenu.startup();
    }, function(error){alert(error);});

    //
    //fill Documents menu
    //

    var docsMenu = new Menu({
        onItemClick: function (item, event) {
            DocumentTab.addDocumentTab(item.params);
        }
    }, "docsDiv");

    request.get("http://localhost:8080/Adaptation/rest/documents/all", {
        handleAs: "xml"
    }).then(function(xmldata){
        var docs = xmldata.firstChild.childNodes;
        var doc;
        for(var i = 0; i<docs.length; i++){
            doc = docs[i];
            docsMenu.addChild(new MenuItem({
                id: "docItem" + doc.children[2].textContent || doc[2].innerText,
                label: doc.children[1].textContent || doc[1].innerText,
                params: doc.children[2].textContent || doc[2].innerText
            }));
        }
        docsMenu.startup();
    }, function(error){alert(error);});

    //
    //draw Persons tab
    //
    //Persons.add();
});