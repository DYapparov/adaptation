
define([
    "dijit/registry",
    "dojo/_base/declare",
    "dojo/_base/fx",
    "dojo/_base/lang",
    "dojo/dom-style",
    "dojo/mouse",
    "dojo/on",
    "dijit/form/ValidationTextBox",
    "dijit/form/DateTextBox",
    "dijit/form/Button",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/text!./templates/PersonWidget.html"
    ], function (registry, declare, baseFx, lang, domStyle, mouse, on, ValidationTextBox, DateTextBox, Button, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        
        person: null,
        lastName: null,
        firstName: null,
        middleName: null,
        birthday: null,
        post: null,
        photoURL: null,
        id: null,

        templateString: template,

        baseClass: "personWidget",

        mouseAnim: null,

        baseBackgroundColor: "#fff",
        mouseBackgroundColor: "#def",
        postCreate: function () {
            var domNode = this.domNode;
            var closeButtonNode = this.closeButtonNode;
            var editButtonNode = this.editButtonNode;
            // Run any parent postCreate processes - can be done at any point
            this.inherited(arguments);

            domStyle.set(domNode, "backgroundColor", this.baseBackgroundColor);
            this.own(
                on(domNode, mouse.enter, lang.hitch(this, "_changeBackground", this.mouseBackgroundColor)),
                on(domNode, mouse.leave, lang.hitch(this, "_changeBackground", this.baseBackgroundColor)),
                on(closeButtonNode, "click", lang.hitch(this, "_closeTab")),
                on(editButtonNode, "click", lang.hitch(this, "_editTab"))
            );

            this.postNode.disabled = true;
        },
        _closeTab: function () {
            var tabContainer = registry.byId("tabContainer");
            var tab = registry.byId('personTab' + this.id);
            tabContainer.removeChild(tab);
            tab.destroyRecursive();
        },
        _editTab: function () {
            this.editButtonNode.set('innerHTML',"Сохранить");
            /*this.lastNameNode.removeAttribute('disabled');
            this.firstNameNode.removeAttribute('disabled');
            this.middleNameNode.removeAttribute('disabled');
            this.birthdayNode.removeAttribute('disabled');
            this.postNode.removeAttribute('disabled');*/
            this.postNode.disabled = false;
        },
        _changeBackground: function(newColor) {
            if (this.mouseAnim) {
                this.mouseAnim.stop();
            }

            this.mouseAnim = baseFx.animateProperty({
                node: this.domNode,
                properties: {
                    backgroundColor: newColor
                },
                onEnd: lang.hitch(this, function() {
                    this.mouseAnim = null;
                })
            }).play();
        },
        _setPhotoURLAttr: function (value) {
            this._set("photoURL", value);
            this.avatarNode.src = value;
        },
        constructor: function(args){
            declare.safeMixin(this);
            this._set("person", args);
        }
    });
});