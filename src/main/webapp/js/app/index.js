/**
 * Created by dyapparov on 08.07.2016.
 */


var currentTab, currentHeader;
var selectedRow, selectedRowId;
//var serverIP="http://172.16.124.194:8080";
var serverIP="http://localhost:8080";

function start() {
    loadPersons();
    loadDocuments();
    addTab('personsTab', '');
    if (document.getElementById('tabHeadersScroll').addEventListener) {
        // IE9, Chrome, Safari, Opera
        document.getElementById('tabHeadersScroll').addEventListener("mousewheel", scrollHorizontally, false);
        // Firefox
        document.getElementById('tabHeadersScroll').addEventListener("DOMMouseScroll", scrollHorizontally, false);
    } else {
        // IE 6/7/8
        document.getElementById('tabHeadersScroll').attachEvent("onmousewheel", scrollHorizontally);
    }
    registerScrollListeners();
    document.body.onresize = function () {
        adjustScroll();
    }

}

function loadPersons() {
    var personsMenu = document.getElementById("personsMenu");
    while(personsMenu.hasChildNodes()){
        personsMenu.firstChild.remove();
    }
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', serverIP + '/Adaptation/rest/ecm/employees', true);
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
    xmlhttp.open('GET', serverIP + '/Adaptation/rest/documents/all/', true);
    xmlhttp.send();
    var docs;
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            docs = xmlhttp.responseXML.getElementsByTagName("docs");
            for (var i = 0; i<docs.length; i++){

                var id = docs[i].childNodes[2].firstChild.nodeValue;
                var regNum = docs[i].childNodes[4].firstChild.nodeValue;
                var docName = docs[i].getAttribute('xsi:type');
                if(docName == 'incoming'){
                    docName='Входящее';
                } else if(docName == 'outgoing'){
                    docName='Исходящее';
                } else if(docName == 'task'){
                    docName='Поручение';
                }

                var newDiv = document.createElement("div");
                newDiv.setAttribute("class", "listItem");
                newDiv.setAttribute("onclick", "addTab('document', " + id + ")");
                newDiv.innerHTML = "<p>" + docName + " №" + regNum + "</p>";
                document.getElementById("documents").appendChild(newDiv);
            }
        }
    };
}

function doGet(url, data, callback) {
    var xmlHttp = getXmlHttp();
    xmlHttp.set();
    xmlHttp.set();
    xmlHttp.onreadystatechange = function(state, response){
        if (state == 200) {
            callback(response);
        }
    }
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
        header = 'Сотрудник ' + id;
    } else if (type=='document'){
        link = 'document?id=' + id;
        header = 'Документ №' + id;
    } else if(type=='newPerson') {
        link = 'new_person';
        header = 'Создать';
    } else {
        //link = 'faces/facelet.xhtml';
        link = 'persons';
        header = 'Сотрудники';
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
    document.getElementById('tabContainer').appendChild(newTab);

    var newTabHeader = document.createElement('div');
    newTabHeader.setAttribute('id', type+id);
    newTabHeader.innerHTML = header;
    newTabHeader.onclick = function (event) {
        var target = event.srcElement ? event.srcElement : event.target;
        if(target==this){
            updateActiveTab(newTabHeader, newTab);
        }
    };
    document.getElementById('tabHeaders').appendChild(newTabHeader);

    var closeButton = document.createElement('div');
    closeButton.setAttribute('class', 'closeButton');
    closeButton.innerHTML = 'X';
    closeButton.onclick = function () {
        closeTab(type, id);
    };
    newTabHeader.appendChild(closeButton);

    updateActiveTab(newTabHeader, newTab);
}

function closeTab(type, id) {
    var closingHeader = document.getElementById(type + id);
    closingHeader.remove();
    var closingTab = document.getElementById(type + id + '_tab');
    closingTab.remove();
    if(currentHeader==closingHeader){
        var tabs = document.getElementById('tabContainer').childNodes;
        if(tabs.length>0){
            var tabheaders = document.getElementById('tabHeaders').childNodes;
            currentHeader = tabheaders[tabheaders.length-1];
            currentTab = tabs[tabs.length-1];
            currentTab.setAttribute('style', 'display: block');
            currentHeader.removeAttribute('class');
            currentHeader.setAttribute('class', 'activeTabHeader');
        } else {
            return;
        }
    }
    adjustScroll();
}

function updateActiveTab(header, tab){
    if(currentHeader!=undefined&&currentTab!=undefined) {
        currentHeader.removeAttribute('class');
        currentHeader.setAttribute('class', 'inActiveTabHeader');
        currentTab.setAttribute('style', 'display: none');
    }
    currentHeader = header;
    currentTab = tab;
    currentHeader.setAttribute('class', 'activeTabHeader');
    currentTab.setAttribute('style', 'display: block');

    adjustScroll();
}

function adjustScroll() {
    var headersContainer = document.getElementById('tabHeadersContainer');
    var headersScroll = document.getElementById('tabHeadersScroll');
    var visiblePos_m = headersScroll.scrollLeft;
    var visiblePos_M = headersScroll.scrollLeft + headersContainer.offsetWidth;
    var currentElemPos_m = currentHeader.offsetLeft;
    var currentElemPos_M = currentHeader.offsetLeft+currentHeader.offsetWidth;

    if(currentElemPos_M > visiblePos_M){
        headersScroll.scrollLeft += currentElemPos_M - visiblePos_M;
        document.getElementById('scrollButtons').setAttribute('style', 'display:block');
    } else if (currentElemPos_m < visiblePos_m) {
        headersScroll.scrollLeft -= visiblePos_m - currentElemPos_m;
    }

    if(headersScroll.scrollWidth>headersContainer.offsetWidth){
        document.getElementById('scrollButtons').setAttribute('style', 'display:block');
    } else{
        document.getElementById('scrollButtons').setAttribute('style', 'display:none');
    }
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
    xmlhttp.open('POST', serverIP + '/Adaptation/update_person', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var form = document.forms.namedItem('edit_person_form' + id);

    var valid=check(form.firstName);
    valid&=check(form.lastName);
    valid&=check(form.middleName);
    if(!valid) {
        return;
    }

    var dataString =
        'id=' + encodeURIComponent(id) +
        '&firstName=' + encodeURIComponent(form.firstName.value) +
        '&lastName=' + encodeURIComponent(form.lastName.value) +
        '&middleName=' + encodeURIComponent(form.middleName.value) +
        '&birthday=' + encodeURIComponent(form.birthday.value) +
        '&post=' + encodeURIComponent(form.post.value);
    xmlhttp.send(dataString);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4){
            if(xmlhttp.status===200) {
                loadPersons();
                document.getElementById('person' + id + '_tab').innerHTML = xmlhttp.response;
                //closeTab('person', id);
                reloadTab('personsTab', '');
            }
            else if (xmlhttp.status===520) {
                alert(xmlhttp.getResponseHeader('error'));
            }
        }
    };
}

function check(field) {
    var pattern = new RegExp(field.pattern);
    var result = pattern.test(field.value);
    if(!result){
        field.setAttribute('style', 'border: solid 2px red');
    } else {
        field.setAttribute('style', 'border: ');
    }
    return result;
}

function addPerson() {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', serverIP + '/Adaptation/new_person', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var form = document.forms.namedItem('new_person_form');

    var valid=check(form.firstName);
    valid&=check(form.lastName);
    valid&=check(form.middleName);
    if(!valid) {
        return;
    }

    var dataString =
        '&firstName=' + encodeURIComponent(form.firstName.value) +
        '&lastName=' + encodeURIComponent(form.lastName.value) +
        '&middleName=' + encodeURIComponent(form.middleName.value) +
        '&birthday=' + encodeURIComponent(form.birthday.value) +
        '&post=' + encodeURIComponent(form.post.value);



    xmlhttp.send(dataString);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4){
            if(xmlhttp.status===200) {
                loadPersons();
                closeTab('newPerson', '');
                reloadTab('personsTab', '');
            }
            else if (xmlhttp.status===520) {
                alert(xmlhttp.getResponseHeader('error'));
            }
        }
    };
}

function deletePerson(id) {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('POST', serverIP + '/Adaptation/update_person', true);
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
    xmlhttp.open('GET', serverIP + '/Adaptation/post_photo?id=' + id, true);
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
    xmlhttp.open('POST', serverIP + '/Adaptation/post_photo', true);
    var form = document.forms.namedItem('set_photo_form');
    var data = new FormData(form);
    xmlhttp.send(data);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status===200) {
            tab.innerHTML = xmlhttp.response;
        }
    };
}

function reloadTab(type, id) {
    var tab = document.getElementById(type+id+'_tab');
    if(tab==undefined) {
        return;
    }
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
                    selectedRow.classList.remove('selected');
                } else {
                    document.getElementById('edit_person_button').classList.remove('hidden');
                    document.getElementById('delete_person_button').classList.remove('hidden');
                }
                this.classList.add('selected');
                selectedRow = this;
                selectedRowId = selectedRow.cells[0].innerHTML;
            };
        }
    }
}

function enableEditMode(button, id) {
    button.innerHTML = 'Сохранить';
    button.onclick = function(){
        savePerson(id);
    };
    var form = document.forms.namedItem('edit_person_form' + id);
    for (var i = 0; i<form.elements.length; i++){
        form.elements[i].removeAttribute('disabled');
    }
}

function scrollHorizontally(e) {
    e = window.event || e;
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
    document.getElementById('tabHeadersScroll').scrollLeft -= (delta*40);
    e.preventDefault();
}

function registerScrollListeners() {
    document.getElementById('scrollLeftBut').onclick = function () {
        document.getElementById('tabHeadersScroll').scrollLeft -= 40;
    };
    document.getElementById('scrollRightBut').onclick = function () {
        document.getElementById('tabHeadersScroll').scrollLeft += 40;
    };
}

window.onload = start();