/**
 * Created by dyapparov on 08.07.2016.
 */


var currentTab, currentHeader;

function start() {
    loadHelloTab();
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
                newDiv.setAttribute("onclick", "getDocuments(" + person.id + ")");
                newDiv.innerHTML = "<p>" + person.lastName + " " + person.firstName + " " + person.middleName + "</p>";
                document.getElementById("persons").appendChild(newDiv);
            }
        }
    };
}

function getDocuments(id) {
    document.getElementById("documents").innerHTML = "";
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', 'http://localhost:8080/Adaptation/rest/ecm/employees/' + id, true);
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
                newDiv.setAttribute("onclick", "addDocTab(" + id + ")");
                newDiv.innerHTML = "<p>" + docName + "</p>";
                document.getElementById("documents").appendChild(newDiv);
            }
        }
    };
    addPersonTab(id);
}

function loadHelloTab() {
    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute("class", "tabHeader");
    newTabHeader.innerHTML = "Slaves";
    $('#tabHeaders').append(newTabHeader);
    var newTab = document.createElement('div');
    newTab.setAttribute("class", "tab");
    currentHeader = newTabHeader;
    currentTab = newTab;
    $(newTab).load("persons");
    newTab.setAttribute('style', "display: block");
    $('#tabContainer').append(newTab);
    newTabHeader.onclick = function () {
        updateTab(newTabHeader, newTab);
    };
}

function addDocTab(id) {
    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute("class", "tabHeader");
    newTabHeader.innerHTML = "Document id " + id;
    $('#tabHeaders').append(newTabHeader);
    var newTab = document.createElement('div');
    newTab.setAttribute("class", "tab");
    currentHeader.setAttribute('style', 'background-color: lightgrey');
    currentTab.setAttribute('style', 'display: none');
    currentHeader = newTabHeader;
    currentTab = newTab;
    $(newTab).load("document?id=" + id);
    newTab.setAttribute('style', "display: block");
    $('#tabContainer').append(newTab);
    newTabHeader.onclick = function () {
        updateTab(newTabHeader, newTab);
    };
}

function addPersonTab(id) {
    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute("class", "tabHeader");
    newTabHeader.innerHTML = "Employee " + id;
    $('#tabHeaders').append(newTabHeader);
    var newTab = document.createElement('div');
    newTab.setAttribute("class", "tab");
    currentHeader.setAttribute('style', 'background-color: lightgrey');
    currentTab.setAttribute('style', 'display: none');
    currentHeader = newTabHeader;
    currentTab = newTab;
    $(newTab).load("edit_person?id=" + id);
    newTab.setAttribute('style', "display: block");
    $('#tabContainer').append(newTab);
    newTabHeader.onclick = function () {
        updateTab(newTabHeader, newTab);
    };
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

function updateTab(header, tab){
    currentTab.setAttribute('style', 'display: none');
    currentHeader.setAttribute('style', 'background-color: lightgrey');
    currentTab = tab;
    currentHeader = header;
    currentTab.setAttribute('style', 'display: block');
    currentHeader.setAttribute('style', 'background-color: white');
}

window.onload = start();