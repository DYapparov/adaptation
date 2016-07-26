
require([
    "app/TabFactory",
    "app/Persons",
    "dijit/registry",
    "dijit/Menu",
    "dijit/MenuItem",
    "dojo/request",
    "dojo/domReady!"
], function(TabFactory, Persons, registry, Menu, MenuItem, request){
    /////////////////////////////////fill Persons menu////////////////////////////////////
    //declare
    var personsMenu = new Menu({
        onItemClick: function (item, event) {
            TabFactory.addPersonTab(item.params);
        }
    }, "personsDiv");

    //fill
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

    ///////////////////////////////fill Documents menu///////////////////////////////
    //declare
    var docsMenu = new Menu({
        onItemClick: function (item, event) {
            TabFactory.addDocumentTab(item.params);
        }
    }, "docsDiv");

    //fill
    request.get("http://localhost:8080/Adaptation/rest/documents/all", {
        handleAs: "xml"
    }).then(function(xmldata){
        var docs = xmldata.firstChild.childNodes;
        var doc;
        for(var i = 0; i<docs.length; i++){
            doc = docs[i];
            var type = doc.getAttribute('xsi:type');
            var regNo = doc.children[4].textContent || doc[4].innerText;
            var docId = doc.children[2].textContent || doc[2].innerText;
            if (type=='task'){
                type = 'Поручение №';
            } else if (type=='outgoing'){
                type = 'Исходящее №';
            } else if (type=='incoming'){
                type = 'Входящее №';
            } else {
                console.log('Unknown document type');
            }
            docsMenu.addChild(new MenuItem({
                id: "docItem_" + docId,
                label: type + regNo,
                params: docId
            }));
        }
        docsMenu.startup();
    }, function(error){alert(error);});

    /////////////////////////////Dobavlenie vkladki sotrudnikov////////////////////////////////
    document.getElementById('employeesMenuHeader').onclick = function () {
        Persons.add();
    }
});