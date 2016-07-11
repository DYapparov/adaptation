/**
 * Created by dyapparov on 08.07.2016.
 */


var currentTab, currentHeader;

function start() {
    loadPersons();
    loadDocuments(); //new
    addTab("Slaves", "persons");
}

function loadPersons() {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', 'http://localhost:8080/Adaptation/rest/ecm/employees', true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            persons = JSON.parse(xmlhttp.responseText);
            for (var i = 0; i < persons.length; i++) {
                var person = persons[i];
                var newDiv = document.createElement("div");
                newDiv.setAttribute("class", "listItem");
                //newDiv.setAttribute("onclick", "getDocuments(" + person.id + ")");
                newDiv.setAttribute("onclick", "addTab('Person " + person.id + "', 'edit_person?id=" + person.id + "')");
                newDiv.innerHTML = "<p>" + person.lastName + " " + person.firstName + " " + person.middleName + "</p>";
                document.getElementById("persons").appendChild(newDiv);
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
                newDiv.setAttribute("onclick", "addTab('Document " + id + "', 'document?id=" + id + "')");
                //newDiv.setAttribute("onclick", "addDocTab(" + id + ")");
                newDiv.innerHTML = "<p>" + docName + "</p>";
                document.getElementById("documents").appendChild(newDiv);
            }
        }
    };
}

function addTab(header, source) {
    var newTab = document.createElement('div');
    newTab.setAttribute("class", "tab");
    $(newTab).load(source);
    newTab.setAttribute('style', "display: block");
    $('#tabContainer').append(newTab);

    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute("class", "activeTabHeader");
    newTabHeader.innerHTML = header;
    $('#tabHeaders').append(newTabHeader);
    newTabHeader.onclick = function () {
        updateTab(newTabHeader, newTab);
    };

    var closeButton = document.createElement('div');
    closeButton.setAttribute('class', 'closeButton');
    closeButton.innerHTML = 'X';
    closeButton.onclick = function () {
        newTabHeader.remove();
        newTab.remove();
        console.log(currentHeader, currentTab);
        //updateTab(currentHeader, currentTab);
        currentTab.setAttribute('style', 'display: block');
        currentHeader.setAttribute('class', 'activeTabHeader');
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

function updateTab(header, tab){
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

window.onload = start();