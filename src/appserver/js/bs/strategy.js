Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var record = new Ext.data.Record.create([
        {name: 'stop_url', mapping: 'stop_url'}
       /* {name: 'white', mapping: 'white'},
        {name: 'black', mapping: 'black'}*/ /*,
         {name:'allow_url',mapping:'allow_url'}*/
    ]);

    var proxy = new Ext.data.HttpProxy({
        url: "../../StrategyAction_findConfig.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty: "totalCount",
        root: "root"
    }, record);

    var store = new Ext.data.GroupingStore({
        id: "store.info",
        proxy: proxy,
        reader: reader
    });

    store.load();
    store.on('load', function () {
        var stop_url = store.getAt(0).get('stop_url');
        //var white = store.getAt(0).get('white');
        //var black = store.getAt(0).get('black');
        //var threeYards = store.getAt(0).get('threeYards');
//        var allow_url = store.getAt(0).get('allow_url');
        Ext.getCmp('strategy.stop_url').setValue(stop_url);
        //Ext.getCmp('strategy.black').setValue(black);
        //Ext.getCmp('strategy.threeYards').setValue(threeYards);
//        Ext.getCmp('android.allow_url').setValue(allow_url);
    });

    var formPanel = new Ext.form.FormPanel({
        plain: true,
        width: 500,
        labelAlign: 'right',
        labelWidth: 200,
        defaultType: 'textfield',
        /*  defaults:{
         width:250,
         allowBlank:false,
         blankText:'该项不能为空!'
         },*/
        items: [

            /*{
             xtype: 'fieldset',
             title: '三码合一校验',
             width: 500,
             items: [new Ext.form.Checkbox({
             inputValue: 1,
             fieldLabel: '启用三码合一校验',
             id: "strategy.threeYards",
             regexText: '启用三码合一校验',
             name: 'threeYards',
             blankText: "启用三码合一校验"
             })]
             },*/
           /* {
                xtype: 'fieldset',
                title: '服务策略',
                width: 500,
                items: [*/

            new Ext.form.RadioGroup({
                id: "strategy.stop_url",
                //              fieldLabel : "协议",    //RadioGroup.fieldLabel 标签与 Radio.boxLabel 标签区别
                //              hideLabel : true,   //隐藏RadioGroup标签
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelStyle: 'padding-left:4px;'
                },
                columns: 1,
                collapsible: true,
                collapsed: true,
                items: [
                    new Ext.form.Radio({                          //三个必须项
                        checked: true, //设置当前为选中状态,仅且一个为选中.
                        boxLabel: '启用黑名单策略', //Radio标签
                        name: "stop_url", //用于form提交时传送的参数名
                        inputValue: 1, //提交时传送的参数值
                        listeners: {
                            check: function (checkbox, checked) {        //选中时,调用的事件
                                if (checked) {

                                }
                            }
                        }
                    }),
                    new Ext.form.Radio({            //以上相同
                        boxLabel: '启用白名单策略',
                        name: "stop_url",
                        inputValue: 0,
                        listeners: {
                            check: function (checkbox, checked) {
                                if (checked) {

                                }
                            }
                        }
                    })
                ]
            })
                   /* new Ext.form.Checkbox({
                        inputValue: 1,
                        fieldLabel: '启用白名单策略',
                        id: "strategy.white",
                        regexText: '启用白名单策略',
                        name: 'white',
                        blankText: "启用白名单策略"
                    }), new Ext.form.Checkbox({
                        inputValue: 1,
                        fieldLabel: '启用黑名单策略',
                        id: "strategy.black",
                        regexText: '启用黑名单策略',
                        name: 'black',
                        blankText: "启用黑名单策略"
                    })*/
            /*    ]
            }*/
            /*{
             xtype: 'fieldset',
             title: '服务策略',
             width: 500,
             items: [
             new Ext.form.RadioGroup({
             id: "strategy.stop_url",
             //              fieldLabel : "协议",    //RadioGroup.fieldLabel 标签与 Radio.boxLabel 标签区别
             //              hideLabel : true,   //隐藏RadioGroup标签
             layout: 'anchor',
             defaults: {
             anchor: '100%',
             labelStyle: 'padding-left:4px;'
             },
             columns: 1,
             collapsible: true,
             collapsed: true,
             items: [
             new Ext.form.Radio({                          //三个必须项
             checked: true, //设置当前为选中状态,仅且一个为选中.
             boxLabel: '启用黑名单策略', //Radio标签
             name: "stop_url", //用于form提交时传送的参数名
             inputValue: 1, //提交时传送的参数值
             listeners: {
             check: function (checkbox, checked) {        //选中时,调用的事件
             if (checked) {

             }
             }
             }
             }),
             new Ext.form.Radio({            //以上相同
             boxLabel: '启用白名单策略',
             name: "stop_url",
             inputValue: 0,
             listeners: {
             check: function (checkbox, checked) {
             if (checked) {

             }
             }
             }
             })*//*,
             {
             xtype: 'checkbox',
             name: 'allow_admin_accessAll',
             boxLabel: '允许管理访问所有VPN客户端ip地址',
             hideLabel: true,
             checked: false
             }*//*
             ]
             })]
             }*/


        ],
        buttons: [
            '->',
            {
                id: 'insert_win.info',
                text: '保存配置',
                handler: function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url: "../../StrategyAction_strategy.action",
                            method: 'POST',
                            waitTitle: '系统提示',
                            waitMsg: '正在连接...',
                            success: function () {
                                Ext.MessageBox.show({
                                    title: '信息',
                                    width: 250,
                                    msg: '保存成功,点击返回页面!',
                                    buttons: Ext.MessageBox.OK,
                                    buttons: {'ok': '确定'},
                                    icon: Ext.MessageBox.INFO,
                                    closable: false
                                });
                            },
                            failure: function () {
                                Ext.MessageBox.show({
                                    title: '信息',
                                    width: 250,
                                    msg: '保存失败，请与管理员联系!',
                                    buttons: Ext.MessageBox.OK,
                                    buttons: {'ok': '确定'},
                                    icon: Ext.MessageBox.ERROR,
                                    closable: false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title: '信息',
                            width: 200,
                            msg: '请填写完成再提交!',
                            buttons: Ext.MessageBox.OK,
                            buttons: {'ok': '确定'},
                            icon: Ext.MessageBox.ERROR,
                            closable: false
                        });
                    }
                }
            }
        ]
    });

    var panel = new Ext.Panel({
        plain: true,
        width: 600,
        border: false,
        items: [{
            id: 'panel.info',
            xtype: 'fieldset',
            title: '策略配置',
            width: 530,
            items: [formPanel]
        }]
    });
    new Ext.Viewport({
        layout: 'fit',
        renderTo: Ext.getBody(),
        autoScroll: true,
        items: [{
            frame: true,
            autoScroll: true,
            items: [panel]
        }]
    });
});


