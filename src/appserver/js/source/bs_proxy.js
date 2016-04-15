Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var record = new Ext.data.Record.create([
        {name:'bs_proxy_ip', mapping:'bs_proxy_ip'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../BsProxy_find.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"totalCount",
        root:"root"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load();
    store.on('load',function(){
        var bs_proxy_ip = store.getAt(0).get('bs_proxy_ip');
//        var vpn_port = store.getAt(0).get('vpn_port');
//        var bs_ip = store.getAt(0).get('bs_ip');
//        var bs_port = store.getAt(0).get('bs_port');
        Ext.getCmp('bs_proxy.ip').setValue(bs_proxy_ip);
//        Ext.getCmp('proxy_address.port').setValue(vpn_port);
//        Ext.getCmp('bs.ip').setValue(bs_ip);
//        Ext.getCmp('bs.port').setValue(bs_port);

    });

    var formPanel = new Ext.form.FormPanel({
        plain:true,
        width:500,
        labelAlign:'right',
        labelWidth:200,
        defaultType:'textfield',
        defaults:{
            width:250,
            allowBlank:false,
            blankText:'该项不能为空!'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel:'管理与代理通信地址',
                name:'bs_proxy_ip',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"bs_proxy.ip",
                allowBlank:false,
                blankText:"管理与代理通信地址"
            })/*,
             new Ext.form.TextField({
             fieldLabel:'VPN认证服务器端口',
             name:'vpn_port',
             id:"proxy_address.port",
             regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
             regexText:'请输入正确的端口',
             allowBlank:false,
             value:"80",
             hidden:true,
             blankText:"VPN认证服务器端口"
             })*//*,
             new Ext.form.TextField({
             fieldLabel : 'bs管理服务器ip',
             name : 'bs_ip',
             regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
             regexText:'请输入正确的ip地址',
             id:"bs.ip",
             allowBlank : false,
             blankText : "不能为空，请正确填写"
             }),
             new Ext.form.TextField({
             fieldLabel : 'bs管理服务器端口',
             id:"bs.port",
             regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
             regexText:'请输入正确的端口',
             name : 'bs_port',
             allowBlank : false,
             blankText : "不能为空，请正确填写"
             })*/
        ],
        buttons:[
            '->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../BsProxy_save.action",
                            method:'POST',
                            waitTitle:'系统提示',
                            waitMsg:'正在连接...',
                            success:function () {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false
                                });
                            },
                            failure:function () {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            }
        ]
    });

    var panel = new Ext.Panel({
        plain:true,
        width:600,
        border:false,
        items:[{
            id:'panel.info',
            xtype:'fieldset',
            title:'代理内部地址',
            width:530,
            items:[formPanel]
        }]
    });
    new Ext.Viewport({
        layout :'fit',
        renderTo:Ext.getBody(),
        autoScroll:true,
        items:[{
            frame:true,
            autoScroll:true,
            items:[panel]
        }]
    });

});


