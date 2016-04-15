/**
 * 角色管理
 */
Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'name',			mapping:'name'},
        {name:'description',			mapping:'description'},
        {name:'createdTime',			mapping:'createdTime'},
        {name:'modifiedTime',		mapping:'modifiedTime'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRoleAction_findByPages.action"
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy : proxy,
        reader : reader
    });

    var boxM = new Ext.grid.CheckboxSelectionModel();   //复选框
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"角色名",			dataIndex:"name",		      align:'center',sortable:true,menuDisabled:true},
        {header:"描述信息",		dataIndex:"description",	  align:'center',menuDisabled:true},
        {header:'创建时间',		dataIndex:'createdTime',	  align:'center',sortable:true,menuDisabled:true},
        {header:'修改时间',		dataIndex:'modifiedTime', align:'center',sortable:true,menuDisabled:true,renderer:show_null},
        {header:'操作标记',		dataIndex:'flag',			  align:'center',sortable:true,menuDisabled:true,renderer:show_flag,	width:100}

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            '角色名称', new Ext.form.TextField({
                name : 'role_name',
                id:'hzih.tbar.role_name'
            }),
            {
                id:'tbar.tbar.role_name.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var role_name = Ext.getCmp('hzih.tbar.role_name').getValue();
                    store.setBaseParam('role_name', role_name);
                    store.load({
                        params : {
                            start : start,
                            limit : pageSize
                        }
                    });
                }
            }]
    });

    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        width:setWidth(),
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colM,
        sm:boxM,
        store:store,
        stripeRows:true,
        autoExpandColumn:2,
        disableSelection:true,
        bodyStyle:'width:100%',
        enableDragDrop:true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        viewConfig:{
            forceFit:true,
            enableRowBody:true,
            getRowClass:function(record,rowIndex,p,store){
                return 'x-grid3-row-collapsed';
            }
        },
        tbar:[
           {
                id:'btnAdd.info',
                text:'新增',
                iconCls:'add',
                handler:function(){
                    add_role(grid_panel,store);     //连接到 新增 面板
                }
            }
        ],
        listeners:{
            render:function(){
                tbar.render(this.tbar);
            }
        },
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }),
        bbar:page_toolbar
    });
    var port = new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[grid_panel]
    });
    store.load({
        params:{
            start:start,limit:pageSize
        }
    });
});
function setHeight(){
    var h = document.body.clientHeight-8;
    return h;
}

function setWidth(){
    return document.body.clientWidth-8;
}

function show_null(value){
    if(value == 'null'){
        return '';
    } else {
        return value;
    }
}

/**
 * 操作标记
 * @param value
 */
function show_flag(value, p, r){
    return String.format(
        '<a id="updateRole.info" href="javascript:void(0);" onclick="updateRole();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="deleteRole.info" href="javascript:void(0);" onclick="deleteRole();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'+
            '<a id="role_users.info" href="javascript:void(0);" onclick="role_users();return false;" style="color: green;">用户成员</a>&nbsp;&nbsp;&nbsp;'+
            '<a id="role_resources.info" href="javascript:void(0);" onclick="role_resources();return false;" style="color: green;">资源列表</a>&nbsp;&nbsp;&nbsp;'
    );
}

/**
 * 添加角色
 * @param grid_panel
 * @param store
 */
function add_role(grid_panel,store){
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
            new Ext.form.TextField({
                fieldLabel : '角色名称',
                name : 'caRole.name',
                id:  'caRole.name',
                allowBlank : false,
                emptyText:"角色名称",
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '角色描述',
                name : 'caRole.description',
                id:  'caRole.description',
                allowBlank : false,
                emptyText:"角色描述",
                blankText : "不能为空，请正确填写"
            })
        ]
    });
    var win = new Ext.Window({
        title:"新增角色",
        width:500,
        layout:'fit',
        height:180,
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
                            url :'../../CaRoleAction_add.action',
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
}

/**
 * 删除角色
 * @param value
 */
function deleteRole() {
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var store = grid.getStore();
    Ext.MessageBox.show({
        title:'信息',
        msg:'<font color="green">确定要删除所选记录？</font>',
        width:260,
        buttons:Ext.Msg.YESNO,
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.INFO,
        closable:false,
        fn:function(e){
            if(e == 'ok'){
                var myMask = new Ext.LoadMask(Ext.getBody(),{
                    msg : '正在删除,请稍后...',
                    removeMask : true
                });
                myMask.show();
                Ext.Ajax.request({
                    url : '../../CaRoleAction_delete.action',             // 删除 连接 到后台
                    params :{id:recode.get("id")},
                    method:'POST',
                    success : function(r,o){
                        myMask.hide();
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.MessageBox.show({
                            title:'信息',
                            width:250,
                            msg:msg,
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.INFO,
                            closable:false,
                            fn:function(e){
                                if(e=='ok'){
                                    grid.render();
                                    store.reload();
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

/**
 * 更新角色
 */
function updateRole(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var store = grid.getStore();
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
            new Ext.form.TextField({
                fieldLabel : '角色名称',
                name : 'caRole.name',
                id:  'caRole.name',
                value:recode.get('name'),
                allowBlank : false,
                emptyText:"角色名称",
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '角色描述',
                name : 'caRole.description',
                value:recode.get('description'),
                id:  'caRole.description',
                allowBlank : false,
                emptyText:"角色描述",
                blankText : "不能为空，请正确填写"
            })
        ]
    });
    var win = new Ext.Window({
        title:"更新角色",
        width:500,
        layout:'fit',
        height:180,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'add_win.info',
                text:'更新',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../CaRoleAction_modify.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            params :{id:recode.get("id")},
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'更新成功,点击返回页面!',
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
                                    msg:'更新失败，请与管理员联系!',
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
}

/**
 * 用户列表
 */
function role_users(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var record = new Ext.data.Record.create([
        {name:'id', mapping:'id'} ,
        {name:'cn', mapping:'cn'} ,
        {name:'hzihpassword', mapping:'hzihpassword'} ,
        {name:'hzihid', mapping:'hzihid'} ,
        {name:'hzihphone', mapping:'hzihphone'} ,
        {name:'hzihaddress', mapping:'hzihaddress'},
        {name:'hzihemail', mapping:'hzihemail'},
        {name:'hzihjobnumber', mapping:'hzihjobnumber'},
        {name:'phonenetid', mapping:'phonenetid'},
        {name:'hzihcaserialNumber', mapping:'hzihcaserialNumber'},
        {name:'terminalid', mapping:'terminalid'} ,
        {name:'terminal_pwd', mapping:'terminal_pwd'},
        {name:'terminal_pwd_audit', mapping:'terminal_pwd_audit'},
        {name:'hzihdn', mapping:'hzihdn'},
        {name:'hzihprovince', mapping:'hzihprovince'},
        {name:'hzihcity', mapping:'hzihcity'},
        {name:'hzihorganization', mapping:'hzihorganization'},
        {name:'hzihinstitutions', mapping:'hzihinstitutions'},
        {name:'hzihcastatus', mapping:'hzihcastatus'},
        {name:'hzihparentca', mapping:'hzihparentca'},
        {name:'hzihcavalidity', mapping:'hzihcavalidity'},
        {name:'status', mapping:'status'},
        {name:'hzihcertificatetype', mapping:'hzihcertificatetype'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRoleUserAction_findByPages.action?roleId="+recode.get("id")
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var storeWin = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    var start = 0;
    var pageSize = 10;
    var SelectArray = new Array();//记录集:所有选中的行号
    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true
//        listeners:{
//            rowselect:function(obj,rowIndex,record){
//                SelectArray[record.data.id] = record.data.id;//往记录集中添加选中的行号,我这里直接保存了一个值
//            },
//            rowdeselect:function(obj,rowIndex,record){
//                delete SelectArray[record.data.id];
//            }
//        }
    });   //复选框
    var colM = new Ext.grid.ColumnModel([
        boxM,
        {header:"标识",   	dataIndex:"id", align:'center',hidden:true,menuDisabled:true},
        {header:"用户名称",dataIndex:"cn",	 align:'center',menuDisabled:true},
        {header:'操作标记',dataIndex:'show', align:'center',sortable:true,menuDisabled:true,renderer:show,	width:100}

    ]);

    /**
     * 操作标记
     * @param value
     */
    function show(value, p, r){
        return String.format(
            '<a id="delete_role_user.info" href="javascript:void(0);" onclick="delete_role_user();return false;" style="color: green;">移除用户</a>&nbsp;&nbsp;&nbsp;'
        );
    }

    var tb = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'add_user.info',
                xtype:'button',
                text:'添加用户成员',
                iconCls:'add',
                handler:function () {
                    addRaUsers(permission, storeWin);
                }
            }]
    });

    var permission= new Ext.grid.GridPanel({
        id:'grid.role_users.info',
        plain:true,
        autoScroll:true,
        height:330,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        cm:colM,
        sm:boxM,
        store:storeWin,
        stripeRows:true,
        autoExpandColumn:2,
        disableSelection:true,
        tbar : tb,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        bbar:new Ext.PagingToolbar({
            pageSize : pageSize,
            store:storeWin,
            displayInfo:true,
            displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
            emptyMsg:"没有记录",
            beforePageText:"当前页",
            afterPageText:"共{0}页"
        })
    });
    var win = new Ext.Window({
        title:"用户",
        width:630,
        layout:'fit',
        height:430,
        modal:true,
        items:[{
            frame:true,
            items:[{
                layout:'fit',
                items:[{
                    xtype:'fieldset',
                    title:'用户列表',
                    height:330,
                    items:[permission]
                }]
            }]
        }]
    }).show();
    storeWin.load({params:{start:start,limit:pageSize}});
}

/**
 * 资源列表
 */
function role_resources(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'url',		mapping:'url'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRolePermissionAction_findByPages.action?roleId="+recode.get("id")
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var storeWin = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    var start = 0;
    var pageSize = 10;
    var SelectArray = new Array();//记录集:所有选中的行号
    var boxM = new Ext.grid.CheckboxSelectionModel({  singleSelect:true
//        listeners:{
//            rowselect:function(obj,rowIndex,record){
//                SelectArray[record.data.id] = record.data.id;
//                //往记录集中添加选中的行号,我这里直接保存了一个值
//            },
//            rowdeselect:function(obj,rowIndex,record){
//                delete SelectArray[record.data.id];
//            }
//        }
    });   //复选框
    var colM = new Ext.grid.ColumnModel([
        boxM,
        {header:"标识",   	dataIndex:"id", hidden:true,align:'center',menuDisabled:true},
        {header:"资源地址",dataIndex:"url",	 align:'center',menuDisabled:true},
        {header:'操作标记',dataIndex:'show', align:'center',sortable:true,menuDisabled:true,renderer:show,	width:100}

    ]);

    /**
     * 操作标记
     * @param value
     */
    function show(value, p, r){
        return String.format(
                '<a id="delete_role_permission.info" href="javascript:void(0);" onclick="delete_role_permission();return false;" style="color: green;">移除资源</a>&nbsp;&nbsp;&nbsp;'
        );
    }

    var tb = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'add_resource.info',
                xtype:'button',
                text:'添加资源条目',
                iconCls:'add',
                handler:function () {
                    addResource(permission, storeWin);
                }
            }]
    });

    var permission= new Ext.grid.GridPanel({
        id:'grid.role_resources.info',
        plain:true,
        autoScroll:true,
        height:330,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        cm:colM,
        sm:boxM,
        store:storeWin,
        stripeRows:true,
        autoExpandColumn:2,
        tbar : tb,
        disableSelection:true,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        bbar:new Ext.PagingToolbar({
            pageSize : pageSize,
            store:storeWin,
            displayInfo:true,
            displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
            emptyMsg:"没有记录",
            beforePageText:"当前页",
            afterPageText:"共{0}页"
        })
    });
    var win = new Ext.Window({
        title:"资源",
        width:630,
        layout:'fit',
        height:430,
        modal:true,
        items:[{
            frame:true,
            items:[{
                layout:'fit',
                items:[{
                    xtype:'fieldset',
                    title:'资源列表',
                    height:330,
                    items:[permission]
                }]
            }]
        }]
    }).show();
    storeWin.load({params:{start:start,limit:pageSize}});
}

/**
 * 删除角色中的用户
 */
function delete_role_user(){
    var role_grid = Ext.getCmp('grid.info');
    var role_recode = role_grid.getSelectionModel().getSelected();
    var roleId = role_recode.get("id");
    var user_grid = Ext.getCmp('grid.role_users.info');
    var user_recode = user_grid.getSelectionModel().getSelected();
    var userId = user_recode.get("id");
    Ext.MessageBox.show({
        title:'信息',
        msg:'<font color="green">确定要移除用户？</font>',
        width:260,
        buttons:Ext.Msg.YESNO,
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.INFO,
        closable:false,
        fn:function(e){
            if(e == 'ok'){
                var myMask = new Ext.LoadMask(Ext.getBody(),{
                    msg : '正在移除,请稍后...',
                    removeMask : true
                });
                myMask.show();
                Ext.Ajax.request({
                    url : '../../CaRoleUserAction_removeRoleIdUser.action',             // 删除 连接 到后台
                    params :{roleId:roleId,userId:userId},
                    method:'POST',
                    success : function(r,o){
                        myMask.hide();
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.MessageBox.show({
                            title:'信息',
                            width:250,
                            msg:msg,
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.INFO,
                            closable:false,
                            fn:function(e){
                                if(e=='ok'){
                                    user_grid.render();
                                    user_grid.getStore().reload();
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

/**
 * 删除角色中的资源
 */
function delete_role_permission(){
    var role_grid = Ext.getCmp('grid.info');
    var role_recode = role_grid.getSelectionModel().getSelected();
    var roleId = role_recode.get("id");
    var resource_grid = Ext.getCmp('grid.role_resources.info');
    var  resource_recode = resource_grid.getSelectionModel().getSelected();
    var pId =  resource_recode.get("id");
    Ext.MessageBox.show({
        title:'信息',
        msg:'<font color="green">确定要移除用户？</font>',
        width:260,
        buttons:Ext.Msg.YESNO,
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.INFO,
        closable:false,
        fn:function(e){
            if(e == 'ok'){
                var myMask = new Ext.LoadMask(Ext.getBody(),{
                    msg : '正在移除,请稍后...',
                    removeMask : true
                });
                myMask.show();
                Ext.Ajax.request({
                    url : '../../CaRolePermissionAction_removeRoleIdPermisson.action',             // 删除 连接 到后台
                    params :{roleId:roleId,pId:pId},
                    method:'POST',
                    success : function(r,o){
                        myMask.hide();
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.MessageBox.show({
                            title:'信息',
                            width:250,
                            msg:msg,
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.INFO,
                            closable:false,
                            fn:function(e){
                                if(e=='ok'){
                                    resource_grid.render();
                                    resource_grid.getStore().reload();
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

/**
 * 角色添加资源
 * @param grid_panel
 * @param store
 */
function addResource(grid_panel, store){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'url',		mapping:'url'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRolePermissionAction_findByOtherRoleId.action?roleId="+recode.get("id")
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var storeWin = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    var start = 0;
    var pageSize = 10;
//    var SelectArray = new Array();//记录集:所有选中的行号
    var boxM = new Ext.grid.CheckboxSelectionModel({
//        listeners:{
//            rowselect:function(obj,rowIndex,record){
//                SelectArray[record.data.id] = record.data.id;//往记录集中添加选中的行号,我这里直接保存了一个值
//            },
//            rowdeselect:function(obj,rowIndex,record){
//                delete SelectArray[record.data.id];
//            }
//        }
    });   //复选框
    var colM = new Ext.grid.ColumnModel([
        boxM,
        {header:"标识",   	dataIndex:"id", hidden:true,align:'center',menuDisabled:true},
        {header:"资源地址",dataIndex:"url",	 align:'center',menuDisabled:true}
    ]);

    var permission= new Ext.grid.GridPanel({
        id:'grid.addResource.info',
        plain:true,
        autoScroll:true,
        height:230,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        cm:colM,
        sm:boxM,
        store:storeWin,
        stripeRows:true,
        autoExpandColumn:2,
        disableSelection:true,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        bbar:new Ext.PagingToolbar({
            pageSize : pageSize,
            store:storeWin,
            displayInfo:true,
            displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
            emptyMsg:"没有记录",
            beforePageText:"当前页",
            afterPageText:"共{0}页"
        })
    });
    var win = new Ext.Window({
        title:"资源",
        width:530,
        layout:'fit',
        height:330,
        modal:true,
        items:[{
            frame:true,
            items:[{
                layout:'fit',
                items:[{
                    xtype:'fieldset',
                    title:'资源列表',
                    height:230,
                    items:[permission]
                }]
            }]
        }] ,
        bbar:[
            '->',
            {
                id:'insert_addResource.info',
                text:'添加',
                handler:function(){
                    var allSelect =  Ext.getCmp("grid.addResource.info").getSelectionModel().getSelections();
                    var pIds = new Array();
                    for(var i = 0; i < allSelect.length; i++){
                        pIds[i] = allSelect[i].get('id');
                    }
                    Ext.Ajax.request({
                        url : '../../CaRolePermissionAction_addPermissionsToRoleId.action',             // 删除 连接 到后台
                        params :{pIds:pIds,roleId:recode.get("id")},
                        method:'POST',
                        success : function(r,o){
                            var respText = Ext.util.JSON.decode(r.responseText);
                            var msg = respText.msg;
                            Ext.MessageBox.show({
                                title:'信息',
                                width:250,
                                msg:msg,
                                buttons:Ext.MessageBox.OK,
                                buttons:{'ok':'确定'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e=='ok'){
                                        grid.render();
                                        store.reload();
                                    }
                                }
                            });
                        }
                    });
                }
            },{
                text:'关闭',
                handler:function(){
                    win.close();
                }
            }
        ]
    }).show();
    storeWin.load({params:{start:start,limit:pageSize}});
}

/**
 * 角色添加用户
 * @param grid_panel
 * @param store
 */
function addRaUsers(grid_panel, store){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var record = new Ext.data.Record.create([
        {name:'id', mapping:'id'} ,
        {name:'cn', mapping:'cn'} ,
        {name:'hzihpassword', mapping:'hzihpassword'} ,
        {name:'hzihid', mapping:'hzihid'} ,
        {name:'hzihphone', mapping:'hzihphone'} ,
        {name:'hzihaddress', mapping:'hzihaddress'},
        {name:'hzihemail', mapping:'hzihemail'},
        {name:'hzihjobnumber', mapping:'hzihjobnumber'},
        {name:'phonenetid', mapping:'phonenetid'},
        {name:'hzihcaserialNumber', mapping:'hzihcaserialNumber'},
        {name:'terminalid', mapping:'terminalid'} ,
        {name:'terminal_pwd', mapping:'terminal_pwd'},
        {name:'terminal_pwd_audit', mapping:'terminal_pwd_audit'},
        {name:'hzihdn', mapping:'hzihdn'},
        {name:'hzihprovince', mapping:'hzihprovince'},
        {name:'hzihcity', mapping:'hzihcity'},
        {name:'hzihorganization', mapping:'hzihorganization'},
        {name:'hzihinstitutions', mapping:'hzihinstitutions'},
        {name:'hzihcastatus', mapping:'hzihcastatus'},
        {name:'hzihparentca', mapping:'hzihparentca'},
        {name:'hzihcavalidity', mapping:'hzihcavalidity'},
        {name:'status', mapping:'status'},
        {name:'hzihcertificatetype', mapping:'hzihcertificatetype'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRoleUserAction_findByOtherRoleId.action?roleId="+recode.get("id")
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var storeWin = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    var start = 0;
    var pageSize = 10;
//    var SelectArray = new Array();//记录集:所有选中的行号
    var boxM = new Ext.grid.CheckboxSelectionModel({
//        listeners:{
//            rowselect:function(obj,rowIndex,record){
//                SelectArray[record.data.id] = record.data.id;//往记录集中添加选中的行号,我这里直接保存了一个值
//            },
//            rowdeselect:function(obj,rowIndex,record){
//                delete SelectArray[record.data.id];
//            }
//        }
    });   //复选框
    var colM = new Ext.grid.ColumnModel([
        boxM,
        {header:"标识",   	dataIndex:"id", align:'center',hidden:true,menuDisabled:true},
        {header:"用户名称",dataIndex:"cn",	 align:'center',menuDisabled:true}
    ]);

    var permission= new Ext.grid.GridPanel({
        id:'grid.addRaUsers.info',
        plain:true,
        autoScroll:true,
        height:230,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        cm:colM,
        sm:boxM,
        store:storeWin,
        stripeRows:true,
        autoExpandColumn:2,
        disableSelection:true,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        bbar:new Ext.PagingToolbar({
            pageSize : pageSize,
            store:storeWin,
            displayInfo:true,
            displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
            emptyMsg:"没有记录",
            beforePageText:"当前页",
            afterPageText:"共{0}页"
        })
    });
    var win = new Ext.Window({
        title:"用户",
        width:530,
        layout:'fit',
        height:330,
        modal:true,
        items:[{
            frame:true,
            items:[{
                layout:'fit',
                items:[{
                    xtype:'fieldset',
                    title:'用户列表',
                    height:230,
                    items:[permission]
                }]
            }]
        }],
        bbar:[
            '->',
            {
                id:'insert_addRaUsers.info',
                text:'添加',
                handler:function(){
                    var allSelect =  Ext.getCmp("grid.addRaUsers.info").getSelectionModel().getSelections();
                    var pIds = new Array();
                    for(var i = 0; i < allSelect.length; i++){
                        pIds[i] = allSelect[i].get('id');
                    }
                    Ext.Ajax.request({
                        url : '../../CaRoleUserAction_addUserToRoleId.action',             // 删除 连接 到后台
                        params :{uIds:pIds,roleId:recode.get("id")},
                        method:'POST',
                        success : function(r,o){
                            var respText = Ext.util.JSON.decode(r.responseText);
                            var msg = respText.msg;
                            Ext.MessageBox.show({
                                title:'信息',
                                width:250,
                                msg:msg,
                                buttons:Ext.MessageBox.OK,
                                buttons:{'ok':'确定'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e=='ok'){
                                        grid.render();
                                        store.reload();
                                    }
                                }
                            });
                        }
                    });
                }
            },{
                text:'关闭',
                handler:function(){
                    win.close();
                }
            }
        ]
    }).show();
    storeWin.load({params:{start:start,limit:pageSize}});
}



