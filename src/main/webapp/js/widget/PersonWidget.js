
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
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/text!./templates/PersonWidget.html"
    ], function (Stateful, at, Output, registry, declare, baseFx, lang, domStyle, mouse, on, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        
        model: null,

        templateString: template,

        baseClass: "personWidget",

        mouseAnim: null,

        baseBackgroundColor: "#fff",
        mouseBackgroundColor: "#def",
        constructor: function(args){
            declare.safeMixin(this);
            this.model = new Stateful(args);
        },
        postCreate: function () {
            this.inherited(arguments);

            var domNode = this.domNode;

            domStyle.set(domNode, "backgroundColor", this.baseBackgroundColor);
            this.own(
                on(domNode, mouse.enter, lang.hitch(this, "_changeBackground", this.mouseBackgroundColor)),
                on(domNode, mouse.leave, lang.hitch(this, "_changeBackground", this.baseBackgroundColor))
            );
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
        }
    });
});