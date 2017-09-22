/*
 * 展示成功的消息。默认7秒钟
 */
;(function($){
    'use strict';

    var ALL_VERSION = '0.0.1';

    var DEFAULT_TIME = 10000;//默认显示消息的持久时间

    var all = {
        //消息队列
        showMsg : function (message, options) {
            new $.zui.Messager(message,options).show();
        },
        showSuccess : function (message, options) {
            this.show(message,{time:DEFAULT_TIME,type:'success',icon:'smile'});
        },
        showInfo : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'info',icon:'info-sign'});
        },
        showDanger : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'danger',icon:'frown'});
        },
        showPrimary : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'primary',icon:'globe'});
        },
        showWarning : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'warning',icon:'bolt'});
        },
        showImportant : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'important',icon:'frown'});
        },
        showSpecial : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,type:'special',icon:'dollar'});
        },
        show : function (message, options) {
            this.showMsg(message,{time:DEFAULT_TIME,icon:'comment'});
        }
    };

    //绑定all到window对象里，才能在外面使用
    window.all = all;
})(jQuery);