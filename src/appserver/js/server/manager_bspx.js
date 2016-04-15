Ext.onReady(function(){
    var hpcsm = new Ext.grid.CheckboxSelectionModel();
    var hpcm = new Ext.grid.ColumnModel([
        hpcsm,
        /* {
            header : "管理IP",
            dataIndex : 'managerip',
            width:130,
            hidden:true
        },{
            header : "管理端口",
            dataIndex : 'managerport',
            width:100,
            hidden:true
        },{
            header : "管理地址",
            dataIndex : 'managerurl',
            width:160
        },*/ {
            header : "代理IP",
            dataIndex : 'proxyip',
            width:130/*,
            hidden:true*/
        },{
            header : "代理端口",
            dataIndex : 'proxyport',
            width:100/*,
            hidden:true*/
        },/*{
            header : "代理地址",
            dataIndex : 'proxyurl',
            width:160
        },*/{
            header : "代理类型",
            dataIndex : 'protocol',
            width:100
        },{
            header : "应用名称",
            dataIndex : 'applyname',
            width:130
        }, {
            header : "应用地址",
            dataIndex : 'applyurl',
            width:160
        }
        ,{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:100
        }
    ]);
    hpcm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a href='javascript:;' style='color:blue;' onclick='delmodel()'>删除</a>";
        return returnStr;

    }
//    var start = 0;
//    var pageSize = 15;
    var hpds = new Ext.data.GroupingStore({
        proxy : new Ext.data.HttpProxy({
//            url : '../../ResourceAction_findAllResources.action'
            url : '../../BsProxyConfigAction_findProxyConf.action'

        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'pclist',
            root : 'pcrow'
        }, [ /* {
            name : 'managerip',
            mapping : 'managerip',
            type : 'string'
        }, {
            name : 'managerport',
            mapping : 'managerport',
            type : 'string'
        }, {
            name : 'managerurl',
            mapping : 'managerurl',
            type : 'string'
        },*/ {
            name : 'proxyip',
            mapping : 'proxyip'/*,
            type : 'string'*/
        }, {
            name : 'proxyport',
            mapping : 'proxyport'/*,
            type : 'string'*/
        },/* {
            name : 'proxyurl',
            mapping : 'proxyurl',
            type : 'string'
        }*/, {
            name : 'protocol',
            mapping : 'protocol'/*,
            type : 'string'*/
        }, {
            name : 'applyname',
            mapping : 'applyname'/*,
            type : 'string'*/
        }, {
            name : 'applyurl',
            mapping : 'applyurl'/*,
            type : 'string'*/
        }//列的映射
        ])
    });
//    hpds.load({
//        params:{
//            start:start, limit:pageSize
//        }
//    });
    hpds.load();

    var hpgrid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'hpgrid',
        title : '',
        store : hpds,
        cm : hpcm,
        sm:hpcsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
        // 添加分页工具栏
//        bbar : new Ext.PagingToolbar({
//            pageSize : 10,
//            store : wapds,
//            displayInfo : true,
//            displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
//            emptyMsg : "无数据。"
//        }),
        // 添加内陷的工具条
        viewConfig:{
            autoFill:true
            //forceFit:true
        },
        tbar : [ //工具栏
            {
                id : 'New1',
                text : ' 新增  ',
                tooltip : '新建一个表单',
                iconCls : 'add',
                handler : function() {
                    createMyform();
                    Ext.getCmp("protocol2").setValue("http");
                    winopen(myform);
                }
            }
        ],
//        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });
//    var storeoutlink = new Ext.data.JsonStore({
//        fields : [ "interfaceName", "ip" ],
//        url : "../../InterfaceManagerAction_readInterface.action",
//        autoLoad : true,
//        root : "rows"
//    });
    var protocoldata = [ ['http','http'], ['https','https'] ];
    var protocolstore = new Ext.data.SimpleStore({fields:['value','name'],data:protocoldata});
    var myform;
    var win = null;
    var userJsonStore = new Ext.data.JsonStore({
        fields : [ "id", "username" ],
        url : '../../InterfaceManagerAction_readInterfaceComboBox.action',
        autoLoad : true,
        root : "rows"
    });
    function createMyform() {
        myform = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../BsProxyConfigAction_addProxyConf.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'protocol2',
                    fieldLabel:"代理类型",
                    width:250,
                    emptyText: '请选择',
                    store: protocolstore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true,
                    listeners:{
                        select:function(combo,record,index){
                            var prvdr=record.get('value');
                            if(prvdr=="https") {
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createHSform();
                                Ext.getCmp("protocol1").setValue("https");
                                winHSopen(myform);
                            }
                        }
                    }
                }),
                /*new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"管理IP",
                    emptyText: '请选择管理IP',
                    store: userJsonStore,
                    valueField:"id",
                    displayField:"username",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true ,
                    allowBlank: false
                }),{
                    fieldLabel : '管理端口' ,
                    name : 'managerport',
                    width:250,
                    regex:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },*/
            /*    {
                    fieldLabel : '代理IP' ,
                    name : 'proxyip',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },*/new Ext.form.ComboBox({
                    hiddenName:'proxyip',
//                    id:'id.proxyip',
                    fieldLabel:"代理IP",
                    emptyText:'请选代理IP',
                    store:userJsonStore,
                    width:250,
                    valueField:"id",
                    displayField:"username",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    allowBlank:false
                }), {
                    fieldLabel : '代理端口' ,
                    name : 'proxyport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank: false
                },{
                    fieldLabel : '应用名称' ,
                    name : 'applyname',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },{
                    fieldLabel : '应用地址' ,
                    name : 'applyurl',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank: false
                }
            ]
        });
    }


    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            title : '',
            width : 500,
//            height :440,
            items: [myform],
            id : 'winhttp',
            bbar : [
                '->',
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
//                                    if (msg == "mmcw") {
//                                        Ext.Msg.alert('提示',msg);
//                                    } else if (msg == "ssadd") {
//                                        Ext.Msg.alert('提示', '应用名称重复');
//                                    } else if (msg == "portcf") {
//                                        Ext.Msg.alert('提示', '端口重复');
//                                    } else {
                                    Ext.MessageBox.alert('提示', msg);
//                                    hpgrid.render();
                                    hpds.reload();
                                    win.close();
//                                    }
                                },failure:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.MessageBox.alert('提示', msg);
                                }
                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    function createHSform() {
        myform = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame : true ,
            fileUpload:true,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../BsProxyConfigAction_addProxyConf.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'protocol1',
                    fieldLabel:"代理类型",
                    emptyText: '请选择',
                    store: protocolstore,
                    width:250,
                    valueField:"value",
                    displayField:"name",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true,
                    listeners:{
                        select:function(combo,record,index){
                            var prvdr=record.get('value');
                            if(prvdr=="http") {
                                Ext.getCmp("winhttps").close();
                                createMyform();
                                Ext.getCmp("protocol2").setValue("http");
                                winopen(myform);
                            }
                        }
                    }
                }),{
                    id:'outFile.info',
                    fieldLabel:"请选择上传证书",
                    name:'uploadFile',
                    xtype:'textfield',
                    width:250,
                    inputType: 'file',
                    allowBlank: false,
                    regexText: '请选择要上传的证书',
                    listeners:{
                        render:function(){
                            Ext.get('outFile.info').on("change",function(){
                                var file = this.getValue();
                                var fs = file.split('.');
                                if(fs[fs.length-1].toLowerCase()!='p12'&&fs[fs.length-1].toLowerCase()!='pfx'){
                                    Ext.MessageBox.show({
                                        title:'信息',
                                        width:200,
                                        msg:'上传文件格式不对,请重新选择!',
                                        buttons:{'ok':'确定'},
                                        icon:Ext.MessageBox.ERROR,
                                        closable:false,
                                        fn:function(e){
                                            if(e=='ok'){
                                                Ext.getCmp('outFile.info').setValue('');
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }

                }, {
                    fieldLabel : '证书密码' ,
                    name : 'ecpassword',
                    width:250,
                    regex:/^\S{4,20}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },
               /* new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"管理IP",
                    emptyText: '请选择管理IP',
                    store: userJsonStore,
                    valueField:"id",
                    displayField:"username",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true ,
                    allowBlank: false
                }),{
                    fieldLabel : '管理端口' ,
                    name : 'managerport',
                    width:250,
                    regex:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },*/
              /*  {
                    fieldLabel : '代理IP' ,
                    name : 'proxyip',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },*/new Ext.form.ComboBox({
                    hiddenName:'proxyip',
//                    id:'id.proxyip',
                    fieldLabel:"代理IP",
                    emptyText:'请选代理IP',
                    store:userJsonStore,
                    width:250,
                    valueField:"id",
                    displayField:"username",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    allowBlank:false
                }), {
                    fieldLabel : '代理端口' ,
                    name : 'proxyport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank: false
                },{
                    fieldLabel : '应用名称' ,
                    name : 'applyname',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },{
                    fieldLabel : '应用地址' ,
                    name : 'applyurl',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank: false,
                    regexText: '请输入应用地址'
                }
            ]
        });
    }


    function winHSopen(form) {
        var myform = form;
        win = new Ext.Window({
            id:'winhttps',
            title : '',
            width : 500,
//            height :440,
            items: [myform],
            bbar : [
                '->',
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
//                                    if (msg == "mmcw") {
//                                        Ext.Msg.alert('提示',msg);
//                                    } else if (msg == "ssadd") {
//                                        Ext.Msg.alert('提示', '应用名称重复');
//                                    } else if (msg == "portcf") {
//                                        Ext.Msg.alert('提示', '端口重复');
//                                    } else {
                                    Ext.MessageBox.alert('提示', msg);
//                                    hpgrid.render();
                                    hpds.reload();
                                    win.close();
//                                    }
                                },failure:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.MessageBox.alert('提示', msg);
                                }
                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    Model.delproxy= function delproxy() {
        var proxyip = hpgrid.getSelectionModel().getSelections()[0].get("proxyip");
        var proxyport = hpgrid.getSelectionModel().getSelections()[0].get("proxyport");
        var protocol = hpgrid.getSelectionModel().getSelections()[0].get("protocol");
        var applyname = hpgrid.getSelectionModel().getSelections()[0].get("applyname");
        var applyurl = hpgrid.getSelectionModel().getSelections()[0].get("applyurl");

        Ext.MessageBox.confirm('提示', '是否确定删除这代理', callBack);
        function callBack(qrid) {
            if("yes"==qrid){
                Ext.Ajax.request({
                    url:'../../BsProxyConfigAction_delProxyConf.action',
                    success:function(response,result){
                        Ext.Msg.alert('提示',"删除成功");
                        hpgrid.render();
                        hpds.reload();
                    }, failure:function (response, result) {
                        Ext.Msg.alert('提示', "删除失败");
                    },
                    params:{proxyip:proxyip,proxyport:proxyport,protocol:protocol,applyname:applyname,applyurl:applyurl}
                });
            }
        }
    }
});

var Model = new Object;
function delmodel(){
    Model.delModel();
}