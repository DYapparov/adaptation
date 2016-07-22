
require([
    "http://localhost:8080/Adaptation/js/TabFactory.js",
    "http://localhost:8080/Adaptation/js/Persons.js",
    "dijit/registry",
    "dijit/Menu",
    "dijit/MenuItem",
    "dojo/request",
    "dojo/parser",
    "dojo/domReady!"
], function(TabFactory, Persons, registry, Menu, MenuItem, request, parser){
    parser.parse();
    /////////////////////////////////fill Persons menu////////////////////////////////////

    var personsMenu = new Menu({
        onItemClick: function (item, event) {
            TabFactory.addTab(item.id);
        }
    }, "personsDiv");
    request.get("http://localhost:8080/Adaptation/rest/ecm/employees", {
        handleAs: "json"
    }).then(function(data){
        for(var i = 0; i<data.length; i++){
            var person = data[i];
            personsMenu.addChild(new MenuItem({
                id: "personItem/" + person.id,
                label: person.lastName + " " + person.firstName + " " + person.middleName
            }));
        }
        personsMenu.startup();
    }, function(error){alert(error);});

    ///////////////////////////////fill Documents menu///////////////////////////////

    var docsMenu = new Menu({
        onItemClick: function (item, event) {
            TabFactory.addTab(item.id);
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
                id: "docItem/" + doc.children[2].textContent || doc[2].innerText,
                label: doc.children[1].textContent || doc[1].innerText
            }));
        }
        docsMenu.startup();
    }, function(error){alert(error);});
    Persons.add();

    /* so sad------
    request.get("http://localhost:8080/Adaptation/rest/documents/all", {
        handleAs: "json"
    }).then(function(data){
        for(var i = 0; i<data.length; i++){
            var doc = data[i];
            docsMenu.addChild(new MenuItem({
                id: "docItem/" + doc.id,
                label: doc.docName
            }));
        }

        docsMenu.startup();
    }, function(error){alert(error);});
    */
});