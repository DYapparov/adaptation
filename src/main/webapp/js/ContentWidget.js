
define([
    "dojo/Stateful",
    "dojox/mvc/at",
    "dojox/mvc/Output",
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
    "dijit/_WidgetsInTemplateMixin"
], function (Stateful, at, Output, registry, declare, baseFx, lang, domStyle, mouse, on, ValidationTextBox, DateTextBox, Button, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

        model: null,
        templateString: null,

        baseClass: "tabContent",

        mouseAnim: null,
        baseBackgroundColor: "#fff",
        mouseBackgroundColor: "#def",

        constructor: function(model, template){
            declare.safeMixin(this);
            this.model = new Stateful(model);
            this.templateString = template;
        },
        postCreate: function () {
            var domNode = this.domNode;
            // Run any parent postCreate processes - can be done at any point
            this.inherited(arguments);
            domStyle.set(domNode, "backgroundColor", this.baseBackgroundColor);
            this.own(
                on(domNode, mouse.enter, lang.hitch(this, "_changeBackground", this.mouseBackgroundColor)),
                on(domNode, mouse.leave, lang.hitch(this, "_changeBackground", this.baseBackgroundColor))
            );
        },
        _setPhotoURLAttr: function (value) {
            this._set("photoURL", value);
            this.avatarNode.src = value;
        },
        validate: function () {
            var result = undefined;
            var validated = true;
            if(this.lastNameNode.validate()&&this.firstNameNode.validate()&&this.middleNameNode.validate()){
                result = this.model;
            }
            return result;
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
        }
    });
});