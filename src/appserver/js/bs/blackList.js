Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'url', mapping:'url'} ,
         {name:'id', mapping:'id'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../BlackListAction_findByPages.action" ,
        timeout: 10*1000
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load({
        params:{
            start:start, limit:pageSize
        }
    });

//    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选
//    var boxM = new Ext.grid.RadioboxSelectionModel();

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
//        boxM,
        rowNumber,
        {header:"黑名单地址", dataIndex:"url", align:'center',sortable:true, menuDisabled:true,sort:true} ,
//        {header:"授权状态", dataIndex:"status", align:'center',sortable:true, menuDisabled:true,renderer:show_accessAddressStatus},
        {header:'操作标记', dataIndex:'flag', align:'center',sortable:true, menuDisabled:true, renderer:show_flag, width:300}
    ]);

    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    function setHeight() {
        var h = document.body.clientHeight - 8;
        return h;
    } ;

    var tb = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'add_resource.info',
                xtype:'button',
                text:'新增黑名单地址',
                iconCls:'add',
                handler:function () {
                    addBlackAddress(grid_panel, store);
                }
            }]
    });

    /*    var typeData = [
     [0,'白名单地址'],[1,'黑名单地址']
     ];
     var typeDataStore = new Ext.data.SimpleStore({
     fields:['id','name'],
     data:typeData
     });*/

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            '黑名单URL地址', new Ext.form.TextField({
                name : 'url',
                id:'hzih.tbar.accessAddress.url'
            }),
            /*    '授权状态',
             new Ext.form.ComboBox({
             typeAhead: true,
             triggerAction: 'all',
             forceSelection: true,
             mode: 'local',
             hiddenName:"status",
             id:'hzih.tbar.accessAddress.status',
             store: typeDataStore,
             valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
             displayField: 'name'//下拉框显示内容
             }) ,*/
            {
                id:'tbar.tbar.accessAddress.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var url = Ext.getCmp('hzih.tbar.accessAddress.url').getValue();
//                    var status = Ext.getCmp('hzih.tbar.accessAddress.status').getValue();
                    store.setBaseParam('url', url);
//                    store.setBaseParam('status', status);
                    store.load({
                        params : {
                            start : start,
                            limit : pageSize
                        }
                    });
//                    Ext.getCmp('hzih.tbar.accessAddress.url').reset();
//                    Ext.getCmp('hzih.tbar.accessAddress.status').reset();
                }
            }]
    });

    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
//        sm:boxM,
        store:store,
        tbar : tb,
        listeners:{
            render:function(){
                tbar.render(this.tbar);
            }
        },
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r){
//    if(r.get("status")=="0"){
        return String.format(
            '<a id="deleteBlackAddress.info" href="javascript:void(0);" onclick="deleteBlackAddress();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'  +
                '<a id="updateBlackAddress.info" href="javascript:void(0);" onclick="updateBlackAddress();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;' /* +
             '<a id="joinBlackList.info" href="javascript:void(0);" onclick="joinBlackList();return false;">加入黑名单</a>&nbsp;&nbsp;&nbsp;'*/
        );
//    }else if(r.get("status")=="1"){
//        return String.format(
//            '<a id="deleteBlackAddress.info" href="javascript:void(0);" onclick="deleteBlackAddress();return false;">删除</a>&nbsp;&nbsp;&nbsp;'  +
//                '<a id="updateBlackAddress.info" href="javascript:void(0);" onclick="updateBlackAddress();return false;">更新</a>&nbsp;&nbsp;&nbsp;'/*  +
//             '<a id="joinWhiteList.info" href="javascript:void(0);" onclick="joinWhiteList();return false;">加入白名单</a>&nbsp;&nbsp;&nbsp;'*/
//        );
//    }
};

//function show_accessAddressStatus(value, p, r){
//    if(r.get("status")=="0"){
//        return String.format('<font color="red" >仅当前地址不允许访问</font>');
//    }else if(r.get("status")=="1"){
//        return String.format('<font color="#4169e1" >服务下所有地址皆不允许访问</font> ');
//    }
//}

function deleteBlackAddress() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认删除吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../BlackListAction_delete.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        Ext.Msg.alert("提示", "删除成功!");
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "删除失败!");
                    }
                });
            }
        });
    }
}  ;

function updateBlackAddress() {
//      var allData = [
//          [1,'服务下所有地址皆不允许访问'],
//          [0,'仅当前地址不允许访问']
//     ];
//     var allDataStore = new Ext.data.SimpleStore({
//     fields:['id','name'],
//     data:allData
//     });
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel : '提示',
                value:'请输入资源URL地址(允许通配一个*)'
            }),
            new Ext.form.TextField({
                fieldLabel : '访问地址',
                name : 'blackList.url',
                value: recode.get('url'),
                id:  'accessAddress.update.adAccessAddress.url',
                allowBlank : false,
                emptyText:"请输访问的URL地址",
                blankText : "不能为空，请正确填写" ,
                listeners:{
                    blur:function(){
                        var url = Ext.getCmp("accessAddress.update.adAccessAddress.url").getValue();
                        var count = countInstances(url,"*");
                        if(count>1){
                            Ext.MessageBox.show({
                                title:'信息',
                                width:250,
                                msg:'当前只允许通配一个*号,请重新输入！',
                                buttons:Ext.MessageBox.OK,
                                buttons:{'ok':'确定'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e=='ok'){
                                        Ext.getCmp("accessAddress.update.adAccessAddress.url").setValue('');
                                    }
                                }
                            });
                        } else{
                            Ext.Ajax.request({
                                url : '../../BlackListAction_checkUrl.action',
                                timeout: 20*60*1000,
                                method : 'post',
                                params : {
                                    url : url
                                },
                                success : function(r,o) {
                                    var respText = Ext.util.JSON.decode(r.responseText);
                                    var msg = respText.msg;
                                    if (msg == 'false') {
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'目标地址已存在,请更换！',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    Ext.getCmp("accessAddress.update.adAccessAddress.url").setValue('');
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            })/*,
             new Ext.form.ComboBox({
             typeAhead: true,
             emptyText:"选择授权范围:",
             triggerAction: 'all',
             forceSelection: true,
             mode: 'local',
             value:recode.get("status"),
             hiddenName:"blackList.status",
             id:'hzih.update.blackList.status',
             store: allDataStore,
             valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
             displayField: 'name'//下拉框显示内容
             })*/
        ]
    });
    var win = new Ext.Window({
        title:"更新地址",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'update_win.info',
                text:'更新',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../BlackListAction_modify.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            params:{
                                oldUrl:recode.get("url") ,id:recode.get("id")
                            },
                            waitMsg :'正在连接...',
                            success : function(form,action) {
                                var msg = action.result.msg;
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:msg,
                                    //                                                    animEl:'insert.win.info',
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        grid_panel.getStore().reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function(form,action) {
                                var msg = action.result.msg;
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:msg,
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
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();


} ;

/*function joinWhiteList(){
 var grid_panel = Ext.getCmp("grid.info");
 var recode = grid_panel.getSelectionModel().getSelected();
 if(!recode){
 Ext.Msg.alert("提示", "请选择一条记录!");
 }else{
 Ext.Msg.confirm("提示", "确认恢复到白名单吗？", function(sid) {
 if (sid == "yes") {
 Ext.Ajax.request({
 url : "../../AccessAddressAction_joinWhiteList.action",
 timeout: 20*60*1000,
 method : "POST",
 params:{url:recode.get("url")},
 success : function(form, action) {
 Ext.Msg.alert("提示", "恢复到白名单成功!");
 grid_panel.getStore().reload();
 },
 failure : function(result) {
 Ext.Msg.alert("提示", "恢复到白名单失败!");
 }
 });
 }
 });
 }


 };

 function joinBlackList(){
 var grid_panel = Ext.getCmp("grid.info");
 var recode = grid_panel.getSelectionModel().getSelected();
 if(!recode){
 Ext.Msg.alert("提示", "请选择一条记录!");
 }else{
 Ext.Msg.confirm("提示", "确认加入黑名单吗？", function(sid) {
 if (sid == "yes") {
 Ext.Ajax.request({
 url : "../../AccessAddressAction_joinBlackList.action",
 timeout: 20*60*1000,
 method : "POST",
 params:{url:recode.get("url")},
 success : function(form, action) {
 Ext.Msg.alert("提示", "加入黑名单成功!");
 grid_panel.getStore().reload();
 },
 failure : function(result) {
 Ext.Msg.alert("提示", "加入黑名单失败!");
 }
 });
 }
 });
 }
 };*/

function addBlackAddress(grid_panel, store){
  /*   var httpType = [
     ['http','http'],['https','https']
     ];
     var httpTypeDataStore = new Ext.data.SimpleStore({
     fields:['id','name'],
     data:httpType
     });*/
//    var allData = [
//        [1,'服务下所有地址皆不允许访问'],
//        [0,'仅当前地址不允许访问']
//    ];
//    var allDataStore = new Ext.data.SimpleStore({
//        fields:['id','name'],
//        data:allData
//    });
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel : '提示',
                value:'请输入资源URL地址(允许通配一个*)'
            }),
           /*  new Ext.form.ComboBox({
                 fieldLabel:'协议类型:',
                 id:'httpType',
                 typeAhead:true,
                 triggerAction:'all',
                 forceSelection:true,
                 mode:'local',
                 editable:false,
                 allowBlank:false,
                 blankText:'请选择',
                 store:httpTypeDataStore,
                 valueField:'id', //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
                 displayField:'name'//下拉框显示内容
             }),*/
            new Ext.form.TextField({
                fieldLabel : '黑名单URL地址',
                name : 'blackList.url',
                id:  'accessAddress.add.adAccessAddress.url',
                allowBlank : false,
              //  regex:/^(http(s)?:\/\/)?((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}):?([1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$))(w+)$/,
                emptyText:"请输入要添加的黑名单URL地址",
                blankText : "不能为空，请正确填写",
                listeners:{
                    blur:function(){
                        var url = Ext.getCmp("accessAddress.add.adAccessAddress.url").getValue();
                        var count = countInstances(url,"*");
                        if(count>1){
                            Ext.MessageBox.show({
                                title:'信息',
                                width:250,
                                msg:'当前只允许通配一个*号,请重新输入！',
                                buttons:Ext.MessageBox.OK,
                                buttons:{'ok':'确定'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e=='ok'){
                                        Ext.getCmp("accessAddress.add.adAccessAddress.url").setValue('');
                                    }
                                }
                            });
                        } else{
                            Ext.Ajax.request({
                                url : '../../BlackListAction_checkUrl.action',
                                timeout: 20*60*1000,
                                method : 'post',
                                params : {
                                    url : url
                                },
                                success : function(r,o) {
                                    var respText = Ext.util.JSON.decode(r.responseText);
                                    var msg = respText.msg;
                                    if (msg == 'false') {
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'目标地址已存在,请更换！',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    Ext.getCmp("accessAddress.add.adAccessAddress.url").setValue('');
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            })/*,
            new Ext.form.ComboBox({
                typeAhead: true,
                emptyText:"选择授权范围",
                triggerAction: 'all',
                forceSelection: true,
                mode: 'local',
                hiddenName:"blackList.status",
                id:'hzih.update.blackList.status',
                store: allDataStore,
                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
                displayField: 'name'//下拉框显示内容
            })*//*,
             new Ext.form.ComboBox({
             typeAhead: true,
             emptyText:"授权状态",
             triggerAction: 'all',
             forceSelection: true,
             mode: 'local',
             hiddenName:"accessAddress.status",
             id:'hzih.add.accessAddress.status',
             store: typeAddDataStore,
             valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
             displayField: 'name'//下拉框显示内容
             })*/
        ]
    });
    var win = new Ext.Window({
        title:"新增黑名单地址",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'add_win.info',
                text:'新增',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../BlackListAction_add.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'新增成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        store.reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
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
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};

function countInstances(mainStr, subStr)
{
    var count = 0;
    var offset = 0;
    do
    {
        offset = mainStr.indexOf(subStr, offset);
        if(offset != -1)
        {
            count++;
            offset += subStr.length;
        }
    }while(offset != -1)
    return count;
}