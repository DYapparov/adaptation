
require([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin"
], function (declare, lang, _WidgetBase, _TemlatedMixin, _WidgetsInTemplatedMixin) {
    return declare('app.Navigator', [_WidgetBase, _TemlatedMixin, _WidgetsInTemplatedMixin], {
        template: replaceme,
        constructor: function (args) {
            lang.mixin(this, args);
        },
        postCreate: function () {
            this.inherited(arguments);

        }

    })
});
