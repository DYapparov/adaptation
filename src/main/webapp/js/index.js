/**
 * Created by dyapparov on 08.07.2016.
 */


var currentTab, currentHeader;
var selectedRow, selectedRowId;

function start() {
    loadPersons();
    loadDocuments();
    addTab('personsTab', '');
}

function loadPersons() {
    var personsMenu = document.getElementById("personsMenu");
    while(personsMenu.hasChildNodes()){
        personsMenu.firstChild.remove();
    }
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', 'http://localhost:8080/Adaptation/rest/ecm/employees', true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            var persons = JSON.parse(xmlhttp.responseText);
            for (var i = 0; i < persons.length; i++) {
                var person = persons[i];
                var newDiv = document.createElement("div");
                newDiv.setAttribute("class", "listItem");
                newDiv.setAttribute("onclick", "addTab('person', " + person.id + ")");
                newDiv.innerHTML = "<p>" + person.lastName + " " + person.firstName + " " + person.middleName + "</p>";
                personsMenu.appendChild(newDiv);
            }
        }
    };
}

function loadDocuments() {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', 'http://localhost:8080/Adaptation/rest/documents/all/', true);
    xmlhttp.send();
    var docs;
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            docs = xmlhttp.responseXML.getElementsByTagName("docs");
            for (var i = 0; i<docs.length; i++){
                var docName = docs[i].childNodes[1].firstChild.nodeValue;
                var id = docs[i].childNodes[2].firstChild.nodeValue;
                var newDiv = document.createElement("div");
                newDiv.setAttribute("class", "listItem");
                newDiv.setAttribute("onclick", "addTab('document', " + id + ")");
                newDiv.innerHTML = "<p>" + docName + "</p>";
                document.getElementById("documents").appendChild(newDiv);
            }
        }
    };
}

function addTab(type, id) {
    var existingTab = document.getElementById(type + id + '_tab');
    var existingTabHeader = document.getElementById(type + id);
    if(existingTab!=undefined&&existingTabHeader!=undefined){
        updateActiveTab(existingTabHeader, existingTab);
        return;
    }
    var link, header;
    if (type=='person'){
        link = 'edit_person?id=' + id;
        header = 'Person ' + id;
    } else if (type=='document'){
        link = 'document?id=' + id;
        header = 'Document ' + id;
    } else if(type=='newPerson') {
        link = 'new_person';
        header = 'New person';
    } else {
        link = 'persons';
        header = 'Slaves';
    }

    var newTab = document.createElement('div');
    newTab.setAttribute("class", "tab");
    newTab.setAttribute('id', type + id + '_tab');
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', link, true);
    xmlhttp.send(null);
    xmlhttp.onreadystatechange = function () {
        newTab.innerHTML = xmlhttp.response;
        if(type=='personsTab'){
            selectedRow=undefined;
            registerListeners();
        }
    };
    newTab.setAttribute('style', "display: block");
    document.getElementById('tabContainer').appendChild(newTab);

    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute("class", "activeTabHeader");
    newTabHeader.setAttribute('id', type+id);
    newTabHeader.innerHTML = header;
    document.getElementById('tabHeaders').appendChild(newTabHeader);
    newTabHeader.onclick = function (e) {
        if(e.target==this){
            updateActiveTab(newTabHeader, newTab);
        }
    };

    var closeButton = document.createElement('div');
    closeButton.setAttribute('class', 'closeButton');
    closeButton.innerHTML = 'X';
    closeButton.onclick = function () {
        closeTab(type, id);
    };
    newTabHeader.appendChild(closeButton);

    if(currentHeader!=undefined&&currentTab!=undefined){
        currentHeader.removeAttribute('class');
        currentHeader.setAttribute('class', 'inActiveTabHeader');
        currentTab.setAttribute('style', 'display: none');
    }
    currentHeader = newTabHeader;
    currentTab = newTab;
}

function closeTab(type, id) {
    var closingHeader = document.getElementById(type + id);
    closingHeader.remove();
    var closingTab = document.getElementById(type + id + '_tab');
    closingTab.remove();
    if(currentHeader==closingHeader){
        var tabheaders = document.getElementById('tabHeaders').childNodes;
        currentHeader = tabheaders[tabheaders.length-1];
        var tabs = document.getElementById('tabContainer').childNodes;
        currentTab = tabs[tabs.length-1];
        currentTab.setAttribute('style', 'display: block');
        currentHeader.removeAttribute('class');
        currentHeader.setAttribute('class', 'activeTabHeader');
    }
}

function updateActiveTab(header, tab){
    currentTab.setAttribute('style', 'display: none');
    currentHeader.removeAttribute('class');
    currentHeader.setAttribute('class', 'inActiveTabHeader');
    currentTab = tab;
    currentHeader = header;
    currentTab.setAttribute('style', 'display: block');
    currentHeader.setAttribute('class', 'activeTabHeader');
}

function getXmlHttp(){
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (E) {
            xmlhttp = false;
        }
    }
    if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
        xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}

function savePerson(id) {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', 'http://localhost:8080/Adaptation/update_person', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var form = document.forms.namedItem('edit_person_form' + id);
    var dataString =
        'id=' + encodeURIComponent(id) +
        '&firstName=' + encodeURIComponent(form.firstName.value) +
        '&lastName=' + encodeURIComponent(form.lastName.value) +
        '&middleName=' + encodeURIComponent(form.middleName.value) +
        '&birthday=' + encodeURIComponent(form.birthday.value) +
        '&post=' + encodeURIComponent(form.post.value);
    xmlhttp.send(dataString);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            loadPersons();
            closeTab('person', id);
            reloadTab('personsTab', '');
        }
    };
}

function addPerson() {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', 'http://localhost:8080/Adaptation/new_person', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var form = document.forms.namedItem('new_person_form');
    var dataString =
        '&firstName=' + encodeURIComponent(form.firstName.value) +
        '&lastName=' + encodeURIComponent(form.lastName.value) +
        '&middleName=' + encodeURIComponent(form.middleName.value) +
        '&birthday=' + encodeURIComponent(form.birthday.value) +
        '&post=' + encodeURIComponent(form.post.value);
    xmlhttp.send(dataString);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            loadPersons();
            closeTab('newPerson', "");
            reloadTab('personsTab', '');
        }
    };
}

function deletePerson(id) {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', 'http://localhost:8080/Adaptation/update_person', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var dataString =
        'id=' + encodeURIComponent(id) +
        '&action=Delete';
    xmlhttp.send(dataString);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            loadPersons();
            if(document.getElementById('person'+id)!=undefined){
                closeTab('person', id);
            }
            reloadTab('personsTab', '');
        }
    };
}

function newPerson() {
    addTab('newPerson', "");
}

function changePhoto(id) {
    var tab = document.getElementById('person' + id + '_tab');
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', 'http://localhost:8080/Adaptation/post_photo?id=' + id, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(null);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            tab.innerHTML = xmlhttp.response;
        }
    };
}

function savePhoto(id) {
    var tab = document.getElementById('person' + id + '_tab');
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', 'http://localhost:8080/Adaptation/post_photo', true);
    var form = document.forms.namedItem('set_photo_form');
    var data = new FormData(form);
    console.log(data);
    xmlhttp.send(data);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            tab.innerHTML = xmlhttp.response;
        }
    };
}

function reloadTab(type, id) {
    var tab = document.getElementById(type+id+'_tab');
    var xmlhttp = getXmlHttp();
    var link;
    if (type=='person'){
        link = 'edit_person?id=' + id;
    } else if (type=='document'){
        link = 'document?id=' + id;
    } else if(type=='newPerson') {
        link = 'new_person';
    } else {
        link = 'persons';
    }
    xmlhttp.open('GET', link, true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            tab.innerHTML = xmlhttp.response;
            if(type=='personsTab'){
                selectedRow=undefined;
                registerListeners();
            }
        }
    };
}

function registerListeners() {
    var table = document.getElementById("personsTable");
    if (table != null) {
        for (var i = 1; i < table.rows.length; i++) {
            var row = table.rows[i];
            row.onclick = function () {
                if (selectedRow!=undefined){
                    $(selectedRow).removeClass('selected');
                } else {
                    $('#edit_person_button').removeClass('hidden');
                    $('#delete_person_button').removeClass('hidden');
                }
                $(this).addClass('selected');
                selectedRow = this;
                selectedRowId = selectedRow.cells[0].innerHTML;
            };
        }
    }
}

window.onload = start();